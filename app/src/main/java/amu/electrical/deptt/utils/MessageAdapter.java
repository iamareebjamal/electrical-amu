package amu.electrical.deptt.utils;

import amu.electrical.deptt.DetailActivity;
import amu.electrical.deptt.R;
import amu.electrical.deptt.messages.Message;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.PlaceHolder> {


    RotateAnimation ranim;
    private static final int MESSAGE = 0, DATE = 1;
    private List<Object> members;
    private Context context;
    private int lastPosition = -1;

    public MessageAdapter(Context ctx, List<Object> list) {
        //lastPosition=-1;
        members = list;
        context = ctx;
        ranim = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotate);
        ranim.setFillEnabled(true);
        ranim.setFillAfter(true);
    }

    @Override
    public MessageAdapter.PlaceHolder onCreateViewHolder(ViewGroup viewGroup, int p2) {
        // TODO: Implement this method
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_detail, viewGroup, false);
        return new PlaceHolder(itemView);
    }



    @Override
    public int getItemViewType(int position) {
        if (members.get(position) instanceof Message) {
            return MESSAGE;
        } else if (members.get(position) instanceof Date) {
            return DATE;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.PlaceHolder mh, int i) {
        Message message = (Message) members.get(i);
        mh.title.setText(message.getTitle());
        mh.message.setText(message.getMessage());
        int hr = 1 + (int) (12*Math.random());
        int min = (int) (60*Math.random());
        mh.time.setText(String.valueOf(hr)+":"+String.valueOf(min)+" PM");
        ((GradientDrawable)mh.icon.getBackground()).setColor(context.getResources().getColor(R.color.green_main));
        setAnimation(mh.root, i);
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
    public void onViewDetachedFromWindow(MessageAdapter.PlaceHolder holder) {
        // TODO: Implement this method
        holder.root.clearAnimation();
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
        protected TextView title;
        protected TextView message;
        protected TextView time;
        protected ImageView icon;
        protected RelativeLayout root;

        public PlaceHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            message = (TextView) v.findViewById(R.id.message);
            time = (TextView) v.findViewById(R.id.time);
            icon = (ImageView) v.findViewById(R.id.icon);
            root = (RelativeLayout) v;
        }
    }


}
