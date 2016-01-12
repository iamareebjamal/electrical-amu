package amu.electrical.deptt;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;

public class HomeFragment extends Fragment
{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_home, container, false);
		((MainActivity)getActivity()).getSupportActionBar().setTitle("Home");
        return v;
    }
	
	@Override
	public void onResume() {
		super.onResume();
		((MainActivity)getActivity()).closeNavDrawer();
	}
}
