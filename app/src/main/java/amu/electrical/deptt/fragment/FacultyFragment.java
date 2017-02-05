package amu.electrical.deptt.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.R;
import amu.electrical.deptt.model.FacultyAll;
import amu.electrical.deptt.utils.Colors;
import amu.electrical.deptt.utils.ListAdapter;


public class FacultyFragment extends Fragment {
    private ArrayList list;
    private ListAdapter mAdapter;
    private FacultyAll faculty;

    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_faculty, container, false);
        ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
        if(ab != null)
            ab.setTitle("Staff Members");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recycler);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<>();

        mAdapter = new ListAdapter(getActivity(), list);
        rv.setAdapter(mAdapter);

        final FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        Colors.tintFab(fab, getActivity());
        fab.hide();

        TextView inChargeText = (TextView) v.findViewById(R.id.inChargeText);
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(ContextCompat.getColor(getContext(), R.color.green_main));
        shape.setCornerRadius(inChargeText.getWidth() + 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            inChargeText.setBackground(shape);
        else
            inChargeText.setBackgroundDrawable(shape);

        TextView inChargeIntext = (TextView) v.findViewById(R.id.inChargeIntext);
        shape.setCornerRadius(inChargeIntext.getWidth() + 100);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            inChargeIntext.setBackground(shape);
        else
            inChargeIntext.setBackgroundDrawable(shape);

        setScrollBehavior(fab, rv);

        DatabaseReference facultyReference = FirebaseDatabase.getInstance().getReference("faculty-all");
        facultyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                faculty = dataSnapshot.getValue(FacultyAll.class);
                Log.d("Faculty", faculty.toString());
                list.clear();
                list.add("Works and Maintenance");
                list.addAll(faculty.getWorks());

                list.add("Supply Service Section");
                list.addAll(faculty.getSupply());
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        View.OnClickListener onInchargePhoneClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + getContext().getString(R.string.in_charge_phone)));
                try {
                    getContext().startActivity(callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getContext(), "No Dialer found", Toast.LENGTH_LONG).show();
                }
            }
        };

        v.findViewById(R.id.inChargeFab).setOnClickListener(onInchargePhoneClickListener);
        v.findViewById(R.id.inChargePhone).setOnClickListener(onInchargePhoneClickListener);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @TargetApi(14)
    private void setScrollBehavior(final FloatingActionButton fab, final RecyclerView rv) {

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public final void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (Build.VERSION.SDK_INT < 14) {

                    if (recyclerView.getLayoutManager().canScrollVertically())
                        fab.show();
                    else {
                        fab.hide();
                    }

                    return;
                }

                fab.hide();
                /*if (!recyclerView.canScrollVertically(1) && level < 3) {
                    fab.show();
                    //recyclerView.setPadding(0,0,0,fab.getMeasuredHeight());
                }*/
            }

        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View p1) {
                // TODO: Implement this method
                Snackbar.make(rv, "Load More...", Snackbar.LENGTH_SHORT).show();
                return false;
            }


        });
        /*fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                int index = list.size();
                loadMore(level++);
                rv.smoothScrollToPosition(index + 1);
                fab.hide();
            }


        });*/
    }

    /*

    private void loadMore(int level) {
        if(faculty==null)
            return;

        switch (level) {
            case 0:

                list.add("Office Staff");
                list.addAll(faculty.getOffice());
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                list.add("Technical Staff");
                list.addAll(faculty.getTechnical());
                mAdapter.notifyDataSetChanged();
                break;
            case 2:

                list.add("Daily Wager Staff");
                list.addAll(faculty.getDaily());
                mAdapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "No More Information", Toast.LENGTH_SHORT).show();
        }
    }*/

    private String[] loadStringArray(int id) {
        return getActivity().getResources().getStringArray(id);
    }


}
