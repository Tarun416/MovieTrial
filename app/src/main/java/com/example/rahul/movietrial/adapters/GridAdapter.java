package com.example.rahul.movietrial.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.rahul.movietrial.R;
import com.example.rahul.movietrial.utils.TransitionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 09/06/17.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private Context context;
    private final OnPlaceClickListener listener;

    public GridAdapter(Context context,OnPlaceClickListener onPlaceClickListener) {
        this.listener=onPlaceClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        holder.gridImage.setImageResource(R.drawable.mark);
        holder.rootId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPlaceClicked(holder.rootId, TransitionUtils.getRecyclerViewTransitionName(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }


   public  interface OnPlaceClickListener {
        void onPlaceClicked(View sharedView, String transitionName, final int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.gridImage)
        ImageView gridImage;

        @BindView(R.id.rootId)
        RelativeLayout rootId;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
