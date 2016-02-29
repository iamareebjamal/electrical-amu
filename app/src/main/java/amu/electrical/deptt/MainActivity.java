package amu.electrical.deptt;

import amu.electrical.deptt.fragment.FacultyFragment;
import amu.electrical.deptt.fragment.HomeFragment;
import amu.electrical.deptt.fragment.MessageFragment;
import amu.electrical.deptt.messages.Message;
import amu.electrical.deptt.messages.MessageDump;
import amu.electrical.deptt.messages.MessageManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.parse.ParseAnalytics;

public class MainActivity extends AppCompatActivity {

    int prevItem;
    private DrawerLayout mDrawerLayout;
    private HomeFragment hf;
    private MessageFragment mf;
    BroadcastReceiver messageInsert = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MessageDump.TAG)) {
                refreshMessages();
                Log.d(MessageDump.TAG, "Broadcast Received");
            }
        }
    };
    private FacultyFragment ff;
    private int NAV_SLIDE_DELAY = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseAnalytics.trackAppOpened(getIntent());
        registerReceiver(messageInsert, new IntentFilter(MessageDump.TAG));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        if (navView != null)
            setUpNavView(navView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (hf == null)
            hf = new HomeFragment();

        MenuItem home = navView.getMenu().getItem(0);
        prevItem = home.getItemId();
        home.setChecked(true);
        ft.replace(R.id.rootframe, hf);
        ft.commit();
    }

    //Testing - Delete
    public void newMessage(View v) {
        new MessageManager(this).saveMessage(new Message("Hello", "Simple Notification", System.currentTimeMillis()));
        Intent messageInserted = new Intent(MessageDump.TAG);
        sendBroadcast(messageInserted);
        if (mf != null) {
            mf.inserted();
        }
    }

    private void refreshMessages() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mf != null) {
            mf = new MessageFragment();
            ft.replace(R.id.rootframe, mf);
            ft.commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void openNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void setUpNavView(final NavigationView navView) {
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(final MenuItem mItem) {
                // TODO: Implement this method
                int id = mItem.getItemId();
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                switch (id) {
                    case R.id.nav_home:

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mItem.setChecked(true);
                                prevItem = mItem.getItemId();
                                if (hf == null) {
                                    hf = new HomeFragment();
                                } else {
                                    closeNavDrawer();
                                }
                                ft.replace(R.id.rootframe, hf);
                                ft.commit();
                            }
                        }, 0);

                        break;
                    case R.id.nav_messages:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mItem.setChecked(true);
                                prevItem = mItem.getItemId();
                                if (mf == null) {
                                    mf = new MessageFragment();
                                } else {
                                    closeNavDrawer();
                                }
                                ft.replace(R.id.rootframe, mf);
                                ft.commit();
                            }
                        }, 0);

                        break;
                    case R.id.nav_faculty:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mItem.setChecked(true);
                                prevItem = mItem.getItemId();
                                if (ff == null) {
                                    ff = new FacultyFragment();
                                } else {
                                    closeNavDrawer();
                                }
                                ft.replace(R.id.rootframe, ff);
                                ft.commit();
                            }
                        }, NAV_SLIDE_DELAY);
                        closeNavDrawer();

                        break;
                    case R.id.nav_department:

                        final Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        mItem.setChecked(true);
                        closeNavDrawer();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(i);
                            }
                        }, NAV_SLIDE_DELAY);
                        //navView.getMenu().getItem(prevItem).setChecked(true);

                        break;
                    default:
                        closeNavDrawer();


                }

                return false;
            }


        });
    }
}
