package amu.electrical.deptt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);

        NavigationView nv=(NavigationView)findViewById(R.id.nav_view);
        if(nv != null){
            setupDrawerContent(nv);
        }

        Toolbar t=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(t);
        final ActionBar ab=getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()){
                            case R.id.faculty:
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                FacultyFragment df = new FacultyFragment();
                                ft.replace(R.id.rootframe, df);
                                ft.commit();break;
                            case R.id.about:
                                Context c=MainActivity.this;
                                Intent intent = new Intent(c,AboutDepartment.class);
                                c.startActivity(intent);break;
                        }
                        drawer.closeDrawers();
                        return true;
                    }
                });
    }
}
