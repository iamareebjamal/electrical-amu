package amu.electrical.deptt;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import amu.electrical.deptt.fragment.FacultyFragment;
import amu.electrical.deptt.fragment.HomeFragment;
import amu.electrical.deptt.fragment.MessageFragment;

public class MainActivity extends AppCompatActivity {

    static {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private DrawerLayout mDrawerLayout;
    private HomeFragment hf;
    private MessageFragment mf;
    private FacultyFragment ff;
    private int NAV_SLIDE_DELAY = 250;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.getHeaderView(0).findViewById(R.id.imageView3).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (BuildConfig.DEBUG) {

                    final EditText editText = new EditText(context);
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setView(editText);
                    alert.setPositiveButton("YEAH", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (editText.getText().toString().equals("iamareebjamal")) {
                                FirebaseMessaging.getInstance().subscribeToTopic("debug");
                                Toast.makeText(context, "Subscribed to debug mode", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "WRONG!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    alert.show();
                }

                return true;
            }
        });

        setUpNavView(navView);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (hf == null)
            hf = new HomeFragment();

        MenuItem home = navView.getMenu().getItem(0);
        home.setChecked(true);
        ft.replace(R.id.rootframe, hf);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
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
                int id = mItem.getItemId();
                final FragmentTransaction ft = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                switch (id) {
                    case R.id.nav_home:

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mItem.setChecked(true);
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
                        break;
                }

                return false;
            }


        });
    }
}
