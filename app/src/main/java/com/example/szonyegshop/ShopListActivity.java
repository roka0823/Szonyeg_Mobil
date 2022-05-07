package com.example.szonyegshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ShopListActivity extends AppCompatActivity {
    private static final String LOG_TAG = ShopListActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private FirebaseUser user;

    private FrameLayout redCircle;
    private TextView countTextView;
    private int cartItems = 0;
    private  int gridNumber = 1;
    private int queryLimit = 10;

    private RecyclerView myRecycleView;
    private ArrayList<Szonyeg> mItemList;
    private SzonyegAdapter mAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private SharedPreferences preferences;

    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        }else{
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

       /* preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        if(preferences != null){
            cartItems = preferences.getInt("cartItems", 0);
            gridNumber = preferences.getInt("gridNum", 1);
        }

        */

        myRecycleView = findViewById(R.id.recyclerView);
        myRecycleView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();

        mAdapter = new SzonyegAdapter(this, mItemList);
        myRecycleView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
        queryData();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(null, filter);
    }

    BroadcastReceiver powerReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action == null){
                return;
            }
            switch (action) {
                case Intent.ACTION_POWER_CONNECTED:
                    queryLimit = 10;
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    queryLimit = 5;
                    break;
            }
            queryData();
        }
    };

    private void queryData() {
        mItemList.clear();
        mItems.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Szonyeg item = document.toObject(Szonyeg.class);
                mItemList.add(item);
            }

            if (mItemList.size() == 0) {
                initializeData();
                queryData();
            }

            mAdapter.notifyDataSetChanged();
        });
    }

    private void initializeData(){
        String[] itemList = getResources().getStringArray(R.array.szonyeg_names);
        String[] itemInfo = getResources().getStringArray(R.array.szonyeg_desc);
        String[] itemPrice = getResources().getStringArray(R.array.szonyeg_price);
        TypedArray itemsImageResource = getResources().obtainTypedArray(R.array.szonyeg_images);
        TypedArray itemsRate = getResources().obtainTypedArray(R.array.szonyeg_rates);

        for (int i = 0; i < itemList.length; i++) {
            mItems.add(new Szonyeg(
                    itemList[i],
                    itemInfo[i],
                    itemPrice[i],
                    itemsRate.getFloat(i, 0),
                    itemsImageResource.getResourceId(i, 0)));
        }

        itemsImageResource.recycle();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.shop_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Log.d(LOG_TAG, "Setting clicked!");
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.cart:
                Log.d(LOG_TAG, "Cart clicked!");
                startCart();
                return true;
            case R.id.logoutButton:
                Log.d(LOG_TAG, "Logout clicked!");
                unregisterReceiver(powerReciever);
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.view_selector:
                if (viewRow) {
                    changeSpanCount(item, R.drawable.ic_view2, 1);
                } else {
                    changeSpanCount(item, R.drawable.ic_view, 2);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void to_bovebben(View view) {
        Log.d(LOG_TAG, "Rányomtálxd");
        startBovebben();
    }

    private void startBovebben() {
        Log.d(LOG_TAG, "Na? :D");
        Intent intent = new Intent(this, BovebbenActivity.class);
        startActivity(intent);
    }

    private void startCart() {
        Log.d(LOG_TAG, "startCart()");
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    public void to_cart(MenuItem item) {
        Log.d(LOG_TAG, "to_cart");
        startCart();
    }

    private void changeSpanCount(MenuItem item, int drawableId, int spanCount) {
        viewRow = !viewRow;
        item.setIcon(drawableId);
        GridLayoutManager layoutManager = (GridLayoutManager) myRecycleView.getLayoutManager();
        layoutManager.setSpanCount(spanCount);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        redCircle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        countTextView = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onOptionsItemSelected(alertMenuItem);
            }
        });
        return super.onPrepareOptionsMenu(menu);
    }

    public void updateAlertIcon() {
            cartItems = (cartItems + 1);
            if (0 < cartItems) {
                countTextView.setText(String.valueOf(cartItems));
            } else {
                countTextView.setText("");
            }

            redCircle.setVisibility((cartItems > 0) ? VISIBLE : GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerReciever);
    }
}
