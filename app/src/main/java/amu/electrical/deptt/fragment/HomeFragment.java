package amu.electrical.deptt.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;

import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.R;
import amu.electrical.deptt.utils.Colors;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
        if(ab != null)
            ab.setTitle(getString(R.string.home));
        Colors.tintFab(fab, getActivity());
        RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_90);
        ranim.setFillEnabled(true);
        ranim.setFillAfter(true);
        fab.startAnimation(ranim);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openNavDrawer();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO: Implement this method
                ((MainActivity) getActivity()).closeNavDrawer();
            }

        }, 0);
    }
}
