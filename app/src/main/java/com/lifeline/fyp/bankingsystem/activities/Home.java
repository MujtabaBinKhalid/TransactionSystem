package com.lifeline.fyp.bankingsystem.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.bankingsystem.adapter.AllDetails;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.R;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView name;
    Button logout;
    FloatingActionButton createuser;
    Database database;
    RelativeLayout homelayout,formlayout;
    com.rengwuxian.materialedittext.MaterialEditText creatingusername, creatingcnic, creatingphonenumber;
    android.support.v7.widget.AppCompatButton createaccount;
    RecyclerView recyclerView;
    private RecyclerView.Adapter recycleradapter;
    ArrayList<Member> allrecords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home );
        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);


        recyclerView = (RecyclerView) findViewById( R.id. recyclerview);
        logout = (Button) findViewById( R.id.logout );
        name = (TextView) findViewById( R.id.name );
        createuser = (FloatingActionButton) findViewById( R.id.createuser );
        database = new Database( this );
        createuser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userform();
            }
        } );
        name.setText( sharedPreferences.getString("username", null) );

        homelayout = (RelativeLayout) findViewById( R.id.homelayout );
        formlayout = (RelativeLayout) findViewById( R.id.formlayout );

        creatingusername = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingusername );
        creatingcnic = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingcnic );
        creatingphonenumber = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingphonenumber );

        createaccount = (android.support.v7.widget.AppCompatButton) findViewById( R.id.createaccount );



        try{
            userAllRecord();

        }catch (Exception e){

        }


        logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor e=sharedPreferences.edit();
                e.clear();
                e.commit();
                finish();
                startActivity( new Intent( getApplicationContext(),MainActivity.class ) );

            }
        } );

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

    }
