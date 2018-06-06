package com.lifeline.fyp.bankingsystem.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.bankingsystem.adapter.AllDetails;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    FloatingActionButton createuser;
    Database database;
    TextView norecord;
    RelativeLayout homelayout,formlayout;
    com.rengwuxian.materialedittext.MaterialEditText creatingusername, creatingcnic, creatingphonenumber;
    android.support.v7.widget.AppCompatButton createaccount;
    RecyclerView recyclerView;
    private RecyclerView.Adapter recycleradapter;
    ArrayList<Member> allrecords;
    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private NavigationView mNavigationview;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);


        recyclerView = (RecyclerView) findViewById( R.id. recyclerview);
        createuser = (FloatingActionButton) findViewById( R.id.createuser );
        database = new Database( this );
        createuser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userform();
            }
        } );
        norecord = (TextView) findViewById( R.id.norecord );

        homelayout = (RelativeLayout) findViewById( R.id.homelayout );
        formlayout = (RelativeLayout) findViewById( R.id.formlayout );

        creatingusername = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingusername );
        creatingcnic = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingcnic );
        creatingphonenumber = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingphonenumber );

        createaccount = (android.support.v7.widget.AppCompatButton) findViewById( R.id.createaccount );



        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview.setNavigationItemSelectedListener( this );

        View header = mNavigationview.getHeaderView( 0 );
        username = (TextView) header.findViewById( R.id.activeusername );
        username.setText( sharedPreferences.getString("username", null) );



        try{
            recyclerView.setVisibility( View.VISIBLE );
            norecord.setVisibility( View.INVISIBLE );
            userAllRecord();


        }catch (Exception e){
            norecord.setVisibility( View.VISIBLE );
            recyclerView.setVisibility( View.INVISIBLE );
        }



        createaccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatingUser();
            }
        } );
    }


    private void userform(){

homelayout.setVisibility( View.INVISIBLE );
formlayout.setVisibility( View.VISIBLE );


    }

    private void creatingUser(){

        database.creatingMember( new Member(
                sharedPreferences.getString("username", null),
                creatingusername.getText().toString().trim(),
                creatingphonenumber.getText().toString().trim(),
                creatingcnic.getText().toString().trim(),"No Balance",
                "null","null","null"
        ));

        try{
            formlayout.setVisibility( View.INVISIBLE );
            homelayout.setVisibility( View.VISIBLE );
            creatingusername.getText().clear();
            creatingphonenumber.getText().clear();
            creatingcnic.getText().clear();
            userAllRecord();
        }catch (Exception e){
            creatingusername.getText().clear();
            creatingphonenumber.getText().clear();
            creatingcnic.getText().clear();
            formlayout.setVisibility( View.INVISIBLE );
            homelayout.setVisibility( View.VISIBLE );

        }

    }

    private void userAllRecord(){
        allrecords =  database.fetchingAllData(sharedPreferences.getString("username", null));
        recycleradapter = new AllDetails(allrecords,Home.this);
        LinearLayoutManager dpm = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(dpm);
        recyclerView.setAdapter(recycleradapter);

    }

    public void onBackPressed() {
     if(homelayout.getVisibility() ==0){
         final AlertDialog.Builder builder = new AlertDialog.Builder( Home.this );
         builder.setMessage( "Do you want to exit?" );
         builder.setCancelable( true );
         builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 finish();
             }
         } );
         builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.cancel();
             }
         } );

         AlertDialog alertDialog = builder.create();
         alertDialog.show();
     }
     else{
         homelayout.setVisibility( View.VISIBLE );
         formlayout.setVisibility( View.INVISIBLE );
     }
    }

    public boolean onOptionsItemSelected(MenuItem menu) {

        if (drawerToggle.onOptionsItemSelected( menu )) {
            return true;
        }
        return super.onOptionsItemSelected( menu );
    }

    // intializing the menu items of the navigation bar.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exitapp: {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.clear();
                e.commit();
                finish();
                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                break;
            }


        }
        return false;
    }

}
