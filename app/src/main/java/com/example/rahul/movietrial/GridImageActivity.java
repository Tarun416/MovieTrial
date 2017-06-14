package com.example.rahul.movietrial;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Scene;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.rahul.movietrial.adapters.GridAdapter;
import com.example.rahul.movietrial.adapters.GridTheatreAdapter;
import com.example.rahul.movietrial.utils.SpacesItemDecoration;
import com.example.rahul.movietrial.utils.StartSnapHelper;
import com.example.rahul.movietrial.utils.TransitionUtils;
import com.example.rahul.movietrial.utils.TranslateItemAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GridImageActivity extends AppCompatActivity implements GridAdapter.OnPlaceClickListener {

    @BindView(R.id.newTrailerText)
    TextView newTrailerText;

    @BindView(R.id.gridrecyclerView)
    RecyclerView gridRecyclerView;

    @BindView(R.id.inTheatreRecyclerView)
    RecyclerView inTheatreRecyclerView;

    @BindView(R.id.containersLayout)
    RelativeLayout containerLayout;

    @BindView(R.id.newInTheatresText)
    TextView newInTheatresText;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.innerContainerLayout)
    RelativeLayout innerContainerLayout;

    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridTheatreLayoutManager;
    private Scene detailsScene;
    private GridAdapter gridAdapter;
    private GridTheatreAdapter gridTheatreAdapter;
    private String currentTransitionName;
    private int spacingInPixels;
    private SnapHelper snapHelper1;
    private SnapHelper snapHelper2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_image_container);
        ButterKnife.bind(this);
        spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        snapHelper1=new StartSnapHelper();
        snapHelper2=new StartSnapHelper();
        setNewTrailerRecyclerView();
        setInTheatreRecyclerView();
    }


    private void setInTheatreRecyclerView() {
        gridTheatreLayoutManager = new GridLayoutManager(GridImageActivity.this, 2, 0, false);
        inTheatreRecyclerView.setLayoutManager(gridTheatreLayoutManager);
        inTheatreRecyclerView.setItemAnimator(new TranslateItemAnimator());
        inTheatreRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        snapHelper1.attachToRecyclerView(inTheatreRecyclerView);
        gridTheatreAdapter = new GridTheatreAdapter(GridImageActivity.this, this);
        inTheatreRecyclerView.setAdapter(gridTheatreAdapter);
    }

    private void setNewTrailerRecyclerView() {
        gridLayoutManager = new GridLayoutManager(GridImageActivity.this, 2, 0, false);
        gridRecyclerView.setLayoutManager(gridLayoutManager);
        gridRecyclerView.setItemAnimator(new TranslateItemAnimator());
        gridRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        snapHelper2.attachToRecyclerView(gridRecyclerView);
        gridAdapter = new GridAdapter(GridImageActivity.this, this);
        gridRecyclerView.setAdapter(gridAdapter);
    }

    private int offsetTop;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPlaceClicked(View sharedView, String transitionName, int position) {
        currentTransitionName = transitionName;
        detailsScene = DetailsLayout.showScene(GridImageActivity.this, containerLayout, sharedView, transitionName);
        offsetTop = scrollView.getScrollY();

    }

    @Override
    public void onBackPressed() {
        if (detailsScene != null) {
            onBackPressedWithScene();
        } else {
            finish();
        }
    }

    private void onBackPressedWithScene() {
        int childPosition = TransitionUtils.getItemPositionFromTransition(currentTransitionName);
        if (currentTransitionName.startsWith("InTheatre")) {
            DetailsLayout.hideScene(GridImageActivity.this, containerLayout, getSharedViewByPosition(childPosition, "theatre"), currentTransitionName);
        } else {
            DetailsLayout.hideScene(GridImageActivity.this, containerLayout, getSharedViewByPosition(childPosition, "notheatre"), currentTransitionName);
        }

        notifyLayoutAfterBackPress(childPosition, currentTransitionName);
        detailsScene = null;
    }


    private View getSharedViewByPosition(final int childPosition, String flavour) {

        if (flavour.equalsIgnoreCase("theatre")) {

            for (int i = 0; i < inTheatreRecyclerView.getChildCount(); i++) {
                if (childPosition == inTheatreRecyclerView.getChildAdapterPosition(inTheatreRecyclerView.getChildAt(i))) {
                    return inTheatreRecyclerView.getChildAt(i);
                }
            }
        } else {

            for (int i = 0; i < gridRecyclerView.getChildCount(); i++) {
                if (childPosition == gridRecyclerView.getChildAdapterPosition(gridRecyclerView.getChildAt(i))) {
                    return gridRecyclerView.getChildAt(i);
                }
            }
        }
        return null;
    }


    private void notifyLayoutAfterBackPress(final int childPosition, String transitionName) {
        containerLayout.removeAllViews();

        containerLayout.addView(scrollView);

        if (transitionName.startsWith("InTheatre")) {
            inTheatreRecyclerView.requestLayout();

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, offsetTop);
                }
            });
            gridTheatreAdapter.notifyItemChanged(childPosition);

        } else {
            gridRecyclerView.requestLayout();
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, offsetTop);
                }
            });
            gridAdapter.notifyItemChanged(childPosition);

        }
    }


}
