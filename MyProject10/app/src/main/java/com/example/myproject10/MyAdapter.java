package com.example.myproject10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public List<String> myData;
    private LayoutInflater mInflater;
    private ItemClickListener iClickListener;

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    MyAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.myData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String text = myData.get(position);
        holder.mTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.tv_audioName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (iClickListener != null)
                iClickListener.onItemClick(view, getAdapterPosition());

        }

    }


    public void setListener(ItemClickListener iClick){
        iClickListener = iClick;
    }


}