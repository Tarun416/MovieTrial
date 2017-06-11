package com.example.rahul.movietrial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridImageActivity extends AppCompatActivity {


    @BindView(R.id.newTrailerText)
    TextView newTrailerText;
    @BindView(R.id.gridrecyclerView)
    RecyclerView gridRecyclerView;
    @BindView(R.id.inTheatreRecyclerView)
    RecyclerView inTheatreRecyclerView;

    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_image_container);

        ButterKnife.bind(this);

        setNewTrailerRecyclerView();
        setInTheatreRecyclerView();
    }

    private void setInTheatreRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GridImageActivity.this, 2, 0, false);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(GridImageActivity.this);
        gridRecyclerView.setAdapter(gridAdapter);
    }

    private void setNewTrailerRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GridImageActivity.this, 2, 0, false);
        inTheatreRecyclerView.setLayoutManager(gridLayoutManager);
        gridAdapter = new GridAdapter(GridImageActivity.this);
        inTheatreRecyclerView.setAdapter(gridAdapter);
    }
}
