package com.example.marketplaceproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listingAdapter extends RecyclerView.Adapter<listingAdapter.MyViewHolder> {

    Context mcontext;
    Cursor mcursor;

    public listingAdapter(Context context, Cursor cursor){
        this.mcontext = context;
        this.mcursor = cursor;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageOutput;
        TextView priceOutput, titleOutput;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOutput = itemView.findViewById(R.id.listingImg);
            priceOutput = itemView.findViewById(R.id.listingPrice);
            titleOutput = itemView.findViewById(R.id.listingTitle);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.listings_card,parent,false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(!mcursor.moveToPosition(position)){
            return;
        }
        @SuppressLint("Range") String Title = mcursor.getString(mcursor.getColumnIndex(ListingContract.ListingEntry.TITLE_COL));
        @SuppressLint("Range") String Price = mcursor.getString(mcursor.getColumnIndex(ListingContract.ListingEntry.PRICE_COL));
        @SuppressLint("Range") byte[] imageBytes = mcursor.getBlob(mcursor.getColumnIndex(ListingContract.ListingEntry.IMAGE_COL));

        holder.titleOutput.setText(Title);
        holder.priceOutput.setText("$"+Price);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.imageOutput.setImageBitmap(bitmap);

    }


    public void swapCursor(Cursor newCursor) {
        if (mcursor != null) {
            mcursor.close();
        }

        mcursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }






}
