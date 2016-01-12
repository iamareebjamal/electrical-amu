package amu.electrical.deptt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * Created by divy on 12/1/16.
 */
public class AboutDepartment extends AppCompatActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_about);


      final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_about);
      collapsingToolbar.setTitle("About");

      ImageView iv=(ImageView)findViewById(R.id.image_about);
      Glide.with(this).load(R.drawable.temp).centerCrop().into(iv);


  }
}
