package com.example.szonyegshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class SzonyegAdapter extends RecyclerView.Adapter<SzonyegAdapter.ViewHolder> implements Filterable{

    private ArrayList<Szonyeg> mSzonyegItemsData = new ArrayList<>();
    private ArrayList<Szonyeg> mSzonyegItemsDataAll = new ArrayList<>();
    private Context mContext;
    private int lastPsition = -1;

    public SzonyegAdapter(Context context, ArrayList<Szonyeg> itemsData) {
        this.mSzonyegItemsData = itemsData;
        this.mSzonyegItemsDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(SzonyegAdapter.ViewHolder holder, int position) {
        Szonyeg currentItem = mSzonyegItemsData.get(position);
        
        holder.bindTo(currentItem);

        if(holder.getAdapterPosition() > lastPsition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPsition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mSzonyegItemsData.size();
    }

    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Szonyeg> filteredList = new ArrayList<>();
            FilterResults result = new FilterResults();

            if(charSequence == null || charSequence.length() == 0){
                result.count = mSzonyegItemsDataAll.size();
                result.values = mSzonyegItemsDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Szonyeg szonyeg : mSzonyegItemsDataAll){
                    if(szonyeg.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(szonyeg);
                    }
                }
                result.count = filteredList.size();
                result.values = filteredList;
            }

            return result;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mSzonyegItemsData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;


        public ViewHolder(View itemView) {
            super(itemView);

            mTitleText = itemView.findViewById(R.id.itemTitle);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mPriceText = itemView.findViewById(R.id.price);
            mItemImage  = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);

            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ShopListActivity)mContext).updateAlertIcon();
                }
            });
        }

        public void bindTo(Szonyeg currentItem) {
            mTitleText.setText(currentItem.getName());
            mInfoText.setText(currentItem.getInfo());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatedInfo());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);

        }
    }

}

