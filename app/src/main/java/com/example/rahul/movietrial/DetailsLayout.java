package com.example.rahul.movietrial;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 12/06/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DetailsLayout extends CoordinatorLayout {

    private static int spacingInPixels;
    @BindView(R.id.rootCardView)
    RelativeLayout rootCardView;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.headerImage)
    ImageView imageViewPlaceDetails;
    @BindView(R.id.trailerRecyclerView)
    RecyclerView trailerRecyclerView;
   /* @BindView(R.id.cardView)
    CardView cardView;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.movieLimit)
    TextView movieLimit;
    @BindView(R.id.starCastText)
    TextView starCastText;
    @BindView(R.id.starCast)
    TextView starCast;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.trailerText)
    TextView trailerText;
    @BindView(R.id.details_container)
    DetailsLayout detailsContainer;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;

    private LinearLayoutManager linearLayoutManager;

    private TrailerAdapter trailerAdapter;
    private static Activity gridActivity;

    public DetailsLayout(final Context context) {
        this(context, null);
    }

    public DetailsLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        setRecyclerView();
        setToolbar();
    }

    private void setRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        trailerAdapter = new TrailerAdapter(getContext());
        trailerRecyclerView.setAdapter(trailerAdapter);

    }

    private void setData() {

    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        spacingInPixels = activity.getResources().getDimensionPixelSize(R.dimen.spacing);
        gridActivity=activity;
        DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
        //   detailsLayout.setData(data);
        TransitionSet set = new ShowDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }


    private void setToolbar() {
        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(getContext(),R.color.black));
        collapsingToolbar.setTitle("Movie Details");
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null) {
            ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);

            ActionBar actionBar = ((AppCompatActivity) getContext()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } else {
            // Don't inflate. Tablet is in landscape mode.
        }



        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                gridActivity.onBackPressed();

            }
        });
    }


    public static Scene hideScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        DetailsLayout detailsLayout = (DetailsLayout) container.findViewById(R.id.details_container);
        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }
}
