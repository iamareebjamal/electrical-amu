package amu.electrical.deptt.utils;

import amu.electrical.deptt.R;
import amu.electrical.deptt.messages.Message;
import amu.electrical.deptt.messages.MessageManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.PlaceHolder> {

    /*
    Test for ripple background
     */
    private TypedValue mTypedValue = new TypedValue();
    private int mBackground;


    private static final int MESSAGE = 0, DATE = 1;
    RotateAnimation ranim;
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
        //Ripple Background Workaround
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public MessageAdapter.PlaceHolder onCreateViewHolder(ViewGroup viewGroup, int p2) {
        // TODO: Implement this method
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_detail, viewGroup, false);
        itemView.setBackgroundResource(mBackground);
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
        Date date = new Date(message.getTimestamp());
        mh.time.setText(new SimpleDateFormat("dd MMM hh:mm").format(date));
        message.color = Colors.getRandomColor();
        ((GradientDrawable) mh.icon.getBackground()).setColor(context.getResources().getColor(message.color));
        mh.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MessageManager(context).saveMessage(new Message("Hello", "Simple Notification", System.currentTimeMillis()));
            }
        });
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
