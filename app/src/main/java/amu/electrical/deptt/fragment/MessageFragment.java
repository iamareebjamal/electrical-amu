package amu.electrical.deptt.fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.R;
import amu.electrical.deptt.model.News;
import amu.electrical.deptt.utils.Colors;
import amu.electrical.deptt.viewholder.NewsHolder;


public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Notifications");
        RecyclerView rv = (RecyclerView) v.findViewById(R.id.recycler);

        View emptyLayout = v.findViewById(R.id.no_message);

        setupRecyclerView(rv, emptyLayout);

        return v;
    }

    public void setupRecyclerView(RecyclerView rv, final View placeholder) {
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        DatabaseReference newsReference = FirebaseDatabase.getInstance().getReference("news");
        FirebaseRecyclerAdapter newsAdapter = new FirebaseRecyclerAdapter<News, NewsHolder>(News.class, R.layout.message_detail, NewsHolder.class, newsReference) {
            @Override
            protected void populateViewHolder(NewsHolder viewHolder, News model, int position) {
                if (placeholder.getVisibility() == View.VISIBLE)
                    placeholder.setVisibility(View.GONE);

                viewHolder.title.setText(model.getTitle());
                viewHolder.message.setText(model.getBody());
                Date date = new Date(model.getTime());
                viewHolder.time.setText(new SimpleDateFormat("dd MMM h:mm a").format(date));
                ((GradientDrawable) viewHolder.icon.getBackground()).setColor(getActivity().getResources().getColor(Colors.getRandomColor()));

            }
        };

        rv.setAdapter(newsAdapter);
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
