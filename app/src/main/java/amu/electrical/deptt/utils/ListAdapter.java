package amu.electrical.deptt.utils;

import amu.electrical.deptt.R;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PlaceHolder> {

    public final int COLORS[] = {R.color.indigo, R.color.orange, R.color.blue_grey, R.color.purple, R.color.cyan};
    private final int FACULTY = 0, HEADER = 1;
    private RotateAnimation ranim;
    private int colorCount = 0;
    private List<Object> members;
    private Context context;
    private int lastPosition = -1;

    public ListAdapter(Context ctx, List<Object> list) {
        //lastPosition=-1;
        members = list;
        context = ctx;
        ranim = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotate);
        ranim.setFillEnabled(true);
        ranim.setFillAfter(true);
    }

    @Override
    public ListAdapter.PlaceHolder onCreateViewHolder(ViewGroup viewGroup, int p2) {
        // TODO: Implement this method
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faculty_card, viewGroup, false);
        return new PlaceHolder(itemView);
    }

    private void normalBindViewHolder(final ListAdapter.PlaceHolder ah, FacultyMember a) {

        showAllViews(ah);
        ah.name.setGravity(Gravity.LEFT);
        ah.name.setTextColor(context.getResources().getColor(R.color.text_light));

        ah.name.setText(a.name);
        ah.designation.setText(a.designation);

        if (!a.responsibility.equals("")) {
            ah.resposibility.setVisibility(View.VISIBLE);
            ah.resposibility.setText("Responsibility : " + a.responsibility);
        } else
            ah.resposibility.setVisibility(View.GONE);

        View.OnClickListener callClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + ah.mobile.getText()));
                try {
                    context.startActivity(callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Dialer found", Toast.LENGTH_LONG).show();
                }
            }
        };

        if (!ah.mobile.equals("")) {
            ah.call_fab.setVisibility(View.VISIBLE);
            ah.mobile.setText(a.mobile);
            ah.call_fab.setOnClickListener(callClick);
            ah.mobile.setOnClickListener(callClick);
        } else {
            ah.call_fab.setVisibility(View.GONE);
        }


        if (!a.intext.equals("")) {
            ah.intext.setText(a.intext);
            ah.intext.setVisibility(View.VISIBLE);
            (ah.card.findViewById(R.id.inttext)).setVisibility(View.VISIBLE);
        } else {
            ah.intext.setVisibility(View.GONE);
            (ah.card.findViewById(R.id.inttext)).setVisibility(View.GONE);
        }
        final RelativeLayout hidden = (RelativeLayout) ah.card.findViewById(R.id.hidden);
        ah.fab.setImageResource(hidden.isShown() ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_down);
        Colors.tintFab(ah.fab, context);
        ah.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                // TODO: Implement this method

                hidden.setVisibility(hidden.isShown() ? View.GONE : View.VISIBLE);
                ah.fab.startAnimation(ranim);
                ah.fab.setImageResource(hidden.isShown() ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
                Colors.tintFab(ah.fab, context);
            }
        });
    }

    private void headerBindViewHolder(ListAdapter.PlaceHolder p, String name) {
        hideAllViews(p);
        p.name.setGravity(Gravity.CENTER);
        p.name.setTextColor(context.getResources().getColor(R.color.text_dark));
        p.name.setText(name);
        p.card.setCardBackgroundColor(context.getResources().getColor(COLORS[colorCount++]));

        if (colorCount == COLORS.length)
            colorCount = 0;
    }

    private void hideAllViews(ListAdapter.PlaceHolder p) {
        p.designation.setVisibility(View.GONE);
        p.resposibility.setVisibility(View.GONE);
        p.fab.setVisibility(View.GONE);
    }

    private void showAllViews(ListAdapter.PlaceHolder p) {
        p.designation.setVisibility(View.VISIBLE);
        p.resposibility.setVisibility(View.VISIBLE);
        p.fab.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemViewType(int position) {
        if (members.get(position) instanceof FacultyMember) {
            return FACULTY;
        } else if (members.get(position) instanceof String) {
            return HEADER;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(ListAdapter.PlaceHolder ah, int i) {
        switch (ah.getItemViewType()) {
            case FACULTY:
                FacultyMember a = (FacultyMember) members.get(i);
                normalBindViewHolder(ah, a);
                break;
            case HEADER:
                String name = (String) members.get(i);
                headerBindViewHolder(ah, name);
                break;
            default:
                Toast.makeText(context, "Error : Invalid ViewType", Toast.LENGTH_SHORT).show();
        }
        setAnimation(ah.card, i);
    }

    public void resetAnimation() {
        lastPosition = -1;
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(200);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(ListAdapter.PlaceHolder holder) {
        // TODO: Implement this method
        holder.card.clearAnimation();
        //lastPosition=-1;
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        // TODO: Implement this method
        return members.size();
    }

    @Override
    public long getItemId(final int position) {
        return getItemId(position);
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView designation;
        protected TextView resposibility;
        protected TextView mobile;
        protected TextView intext;
        protected FloatingActionButton fab;
        protected FloatingActionButton call_fab;
        protected CardView card;

        public PlaceHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.title);
            designation = (TextView) v.findViewById(R.id.designation);
            resposibility = (TextView) v.findViewById(R.id.responsibility);
            mobile = (TextView) v.findViewById(R.id.mobNo);
            intext = (TextView) v.findViewById(R.id.intExt);
            fab = (FloatingActionButton) v.findViewById(R.id.more);
            call_fab = (FloatingActionButton) v.findViewById(R.id.call);
            card = (CardView) v;
        }
    }


}
