package amu.electrical.deptt.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import amu.electrical.deptt.R;
import amu.electrical.deptt.MainActivity;
import android.os.Handler;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Home");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable(){

				@Override
				public void run()
				{
					// TODO: Implement this method
					((MainActivity) getActivity()).closeNavDrawer();
				}

			}, 0);
    }
}
