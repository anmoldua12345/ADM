package com.example.joginderpal.ngo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar1;
    private TabLayout tabLayout1;
    private ViewPager viewPager1;
    String[] items;
    List<String> li1;
    MediaPlayer mp;
    int currentpos=0;
    SeekBar sb;
    int position=0;
    ArrayList<File> mySongs;
    Thread updateseeker;
    ImageButton f,r,play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar1 = (Toolbar) findViewById(R.id.toolbarone);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager1 = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager1);

        tabLayout1 = (TabLayout) findViewById(R.id.tabs);
        tabLayout1.setupWithViewPager(viewPager1);


        f= (ImageButton) findViewById(R.id.f);
        r= (ImageButton) findViewById(R.id.r);
        sb= (SeekBar) findViewById(R.id.seekbar);
        play= (ImageButton) findViewById(R.id.play);

        f.setOnClickListener(this);
        r.setOnClickListener(this);
        play.setOnClickListener(this);

        li1=new ArrayList<String>();
         mySongs=findroot(Environment.getExternalStorageDirectory());
        items=new String[mySongs.size()];
        for (int i=0;i<mySongs.size();i++){
            //   toast(mySongs.get(i).getName().toString());
            items[i]=(mySongs.get(i).getName().toString().replace(".mp3","").replace(".wav",""));
            li1.add(items[i]);
        }


        updateseeker=new Thread(){
            @Override
            public void run() {
                int totalDuration=mp.getDuration();
                sb.setMax(totalDuration);
                while(currentpos<totalDuration){
                    try {
                        sleep(500);
                        currentpos=mp.getCurrentPosition();
                        sb.setProgress(currentpos);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // super.run();
            }
        };

        Uri u=Uri.parse(mySongs.get(position).toString());

        mp=MediaPlayer.create(getApplicationContext(),u);
       // mp.start();
      //  updateseeker.start();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

    }




    public ArrayList<File> findroot (File root){
        ArrayList<File> al=new ArrayList<File>();
        File[] files=root.listFiles();
        for (File singlefile : files){
            if (singlefile.isDirectory() && !singlefile.isHidden()){
                al.addAll(findroot(singlefile));
            }
            else{
                if (singlefile.getName().endsWith(".mp3")|| singlefile.getName().endsWith(".wav")){
                    al.add(singlefile);
                }
            }
        }
        return al;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragmentone(), "Hot Punjabi");
        adapter.addFragment(new Fragmenttwo(), "Hindi Songs");
        adapter.addFragment(new Fragmentthree(), "Tunes Mirchi");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.SdCard) {

            Intent intent=new Intent(MainActivity.this,Sdcard.class);
            mp.stop();
            startActivity(intent);


        } else if (id == R.id.favourites) {

            Intent i=new Intent(MainActivity.this,Favourites.class);


        }  else if (id == R.id.nav_share) {

            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
           // whatsappIntent.setPackage("");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
            try {
               startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
               // ToastHelper.MakeShortText("Whatsapp have not been installed.");
            }



        } else if (id == R.id.nav_send) {

        }
        else if (id==R.id.playlist){
            Intent i=new Intent(MainActivity.this,youtube.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.play:
                if (mp.isPlaying()){
                    mp.pause();

                }
                else{
                    if(currentpos==0) {
                         updateseeker.start();
                    }
                    mp.start();

                }
                break;

            case R.id.f:
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                Uri u=Uri.parse(mySongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u);
                mp.start();
                break;
            case R.id.r:
                mp.stop();
                mp.release();
                position=(position-1<0)?mySongs.size()-1:position-1;
                Uri u1=Uri.parse(mySongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),u1);
                mp.start();
                break;

        }
    }


}
