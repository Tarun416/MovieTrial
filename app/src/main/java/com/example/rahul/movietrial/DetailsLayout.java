package com.example.rahul.movietrial;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
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

    @BindView(R.id.rootCardView)
    RelativeLayout rootCardView;

    @BindView(R.id.title)
    TextView textViewTitle;

    @BindView(R.id.headerImage)
    ImageView imageViewPlaceDetails;

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
    }

    private void setData() {

    }

    public static Scene showScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater().inflate(R.layout.details_layout, container, false);
        //   detailsLayout.setData(data);
        TransitionSet set = new ShowDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }


    public static Scene hideScene(Activity activity, final ViewGroup container, final View sharedView, final String transitionName) {
        DetailsLayout detailsLayout = (DetailsLayout) container.findViewById(R.id.details_container);
        TransitionSet set = new HideDetailsTransitionSet(activity, transitionName, sharedView, detailsLayout);
        Scene scene = new Scene(container, (View) detailsLayout);
        TransitionManager.go(scene, set);
        return scene;
    }
}
