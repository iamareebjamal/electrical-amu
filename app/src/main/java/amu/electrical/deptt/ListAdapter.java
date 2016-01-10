package amu.electrical.deptt;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

class FacultyMember {
    protected String name, designation, responsibility, mobile, intext;

    public FacultyMember() {
    }

    public FacultyMember(String name, String designation, String responsibility, String mobile, String intext) {
        this.name = name;
        this.designation = designation;
        this.responsibility = responsibility;
        this.mobile = mobile;
        this.intext = intext;
    }
}

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PlaceHolder> {

    RotateAnimation ranim;
    private List<FacultyMember> members;
    private Context context;
    private int lastPosition = -1;

    public ListAdapter(Context ctx, List<FacultyMember> list) {
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

    @Override
    public void onBindViewHolder(final ListAdapter.PlaceHolder ah, int i) {
        FacultyMember a = members.get(i);

        ah.name.setText(a.name);
        ah.designation.setText(a.designation);

        if (!a.responsibility.equals("")) {
            ah.resposibility.setVisibility(View.VISIBLE);
            ah.resposibility.setText("Responsibility : " + a.responsibility);
        } else
            ah.resposibility.setVisibility(View.GONE);

        ah.mobile.setText(a.mobile);

        if (!a.intext.equals("")) {
            ah.intext.setText(a.intext);
            ah.intext.setVisibility(View.VISIBLE);
            (ah.card.findViewById(R.id.inttext)).setVisibility(View.VISIBLE);
        } else {
            ah.intext.setVisibility(View.GONE);
            (ah.card.findViewById(R.id.inttext)).setVisibility(View.GONE);
        }
        final RelativeLayout hidden = (RelativeLayout) ah.card.findViewById(R.id.hidden);
        ah.fab.setImageResource(hidden.isShown() ? R.drawable.ic_arrow_up_white_48dp : R.drawable.ic_arrow_down_white_48dp);
        ah.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                // TODO: Implement this method

                hidden.setVisibility(hidden.isShown() ? View.GONE : View.VISIBLE);
                ah.fab.startAnimation(ranim);
                ah.fab.setImageResource(hidden.isShown() ? R.drawable.ic_arrow_down_white_48dp : R.drawable.ic_arrow_up_white_48dp);

            }
        });
        setAnimation(ah.card, i);
    }

    public void resetAnimation() {
        lastPosition = -1;
    }

    private void setAnimation(CardView viewToAnimate, int position) {
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
        protected CardView card;

        public PlaceHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.title);
            designation = (TextView) v.findViewById(R.id.designation);
            resposibility = (TextView) v.findViewById(R.id.responsibility);
            mobile = (TextView) v.findViewById(R.id.mobNo);
            intext = (TextView) v.findViewById(R.id.intExt);
            fab = (FloatingActionButton) v.findViewById(R.id.more);
            card = (CardView) v;
        }
    }
}
