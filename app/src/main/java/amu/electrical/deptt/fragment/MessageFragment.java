package amu.electrical.deptt.fragment;

import amu.electrical.deptt.MainActivity;
import amu.electrical.deptt.R;
import amu.electrical.deptt.messages.Message;
import amu.electrical.deptt.messages.MessageDump;
import amu.electrical.deptt.messages.MessageManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MessageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: Implement this method
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Messagess");
        final MessageManager messageManager = new MessageManager(getContext());
        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                messageManager.saveMessage(new Message(new Intent().putExtra("lol", false) , "Title B", "Message B", System.currentTimeMillis()));
            }


        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MessageDump messageDump = messageManager.getMessageDump();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                for (Message m : messageDump.getMessages()){
                    Log.d(MessageDump.TAG, m.getTitle() + " " + m.getMessage() + " " + sdf.format(new Date(m.getTimestamp())));
                    if(m.getIntent()!=null)
                        Log.d(MessageDump.TAG, "\tIntent Extras : " + m.getIntent().getExtras().toString());

                }
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_delete);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageManager.clear();
            }
        });

        return v;
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
