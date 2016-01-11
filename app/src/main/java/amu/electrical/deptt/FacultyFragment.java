package amu.electrical.deptt;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FacultyFragment extends Fragment {
    RecyclerView rv;
    ArrayList list;
    ListAdapter mAdapter;
    private int level = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_faculty, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Faculty");
        rv = (RecyclerView) v.findViewById(R.id.recycler1);
        setupRecyclerView(rv);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

        fab.hide();
        setScrollBehavior(fab);
        return v;
    }

    @TargetApi(14)
    private void setScrollBehavior(final FloatingActionButton fab) {

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
                if (!recyclerView.canScrollVertically(1) && level < 3) {
                    fab.show();
                    //recyclerView.setPadding(0,0,0,fab.getMeasuredHeight());
                }
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
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                // TODO: Implement this method
                int index = list.size();
                loadMore(level++);
                rv.smoothScrollToPosition(index + 1);
                fab.hide();
            }


        });
    }

    private void setupRecyclerView(RecyclerView rv) {
        // TODO: Implement this method

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        list = new ArrayList<Object>();

        String[] names = loadStringArray(R.array.works_names);
        String[] designations = loadStringArray(R.array.works_designation);
        String[] responsibilities = loadStringArray(R.array.works_responsibility);
        String[] mobiles = loadStringArray(R.array.works_mobile);
        String[] intexts = loadStringArray(R.array.works_intext);


        list.add("Works and Maintenance");
        for (int i = 0; i < names.length; i++) {
            try {
                list.add(new FacultyMember(names[i], designations[i], responsibilities[i], mobiles[i], intexts[i]));
            } catch (ArrayIndexOutOfBoundsException e) {
                list.add(new FacultyMember(names[i], designations[i], responsibilities[i], mobiles[i], ""));
            }
        }

        names = loadStringArray(R.array.supply_names);
        designations = loadStringArray(R.array.supply_designation);
        responsibilities = loadStringArray(R.array.supply_responsibility);
        mobiles = loadStringArray(R.array.supply_mobile);

        list.add("Supply Service Section");
        for (int i = 0; i < names.length; i++) {
            list.add(new FacultyMember(names[i], designations[i], responsibilities[i], mobiles[i], ""));
        }

        mAdapter = new ListAdapter(getActivity(), list);
        rv.setAdapter(mAdapter);
    }

    private void loadMore(int level) {

        String[] names, designations, mobiles;

        switch (level) {
            case 0:
                names = loadStringArray(R.array.office_names);
                designations = loadStringArray(R.array.office_designation);
                mobiles = loadStringArray(R.array.office_mobile);

                list.add("Office Staff");

                for (int i = 0; i < names.length; i++) {
                    list.add(new FacultyMember(names[i], designations[i], "", mobiles[i], ""));
                }
                mAdapter.notifyDataSetChanged();
                break;
            case 1:
                names = loadStringArray(R.array.technical_names);
                designations = loadStringArray(R.array.technical_designation);
                mobiles = loadStringArray(R.array.technical_mobile);

                list.add("Technical Staff");
                for (int i = 0; i < names.length; i++) {
                    list.add(new FacultyMember(names[i], designations[i], "", mobiles[i], ""));
                }
                mAdapter.notifyDataSetChanged();
                break;
            case 2:
                names = loadStringArray(R.array.daily_names);
                designations = loadStringArray(R.array.daily_designation);
                mobiles = loadStringArray(R.array.daily_mobile);

                list.add("Daily Wager Staff");
                for (int i = 0; i < names.length; i++) {
                    list.add(new FacultyMember(names[i], designations[i], "", mobiles[i], ""));
                }
                mAdapter.notifyDataSetChanged();
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "No More Information", Toast.LENGTH_SHORT).show();
        }
    }

    private String[] loadStringArray(int id) {
        return getActivity().getResources().getStringArray(id);
    }


}
