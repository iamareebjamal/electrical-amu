package amu.electrical.deptt.fragment;

import android.support.v4.app.Fragment;
import android.os.Handler;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import amu.electrical.deptt.R;
import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.utils.NotificationUtils;
import android.content.Intent;


public class MessageFragment extends Fragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Messages");
		v.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					NotificationUtils nUtil = new NotificationUtils(getActivity());
					nUtil.setId((int)(100*Math.random()));
					nUtil.showNotification("lolwa", "Dekh Luuna", new Intent());
				}
				
			
		});
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
