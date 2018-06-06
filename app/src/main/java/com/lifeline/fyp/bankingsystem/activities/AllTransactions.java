package com.lifeline.fyp.bankingsystem.activities;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.bankingsystem.R;
import com.lifeline.fyp.bankingsystem.adapter.AllDetails;
import com.lifeline.fyp.bankingsystem.adapter.TransactionRecord;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllTransactions extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout dlayout; // drawerlayout
    private ActionBarDrawerToggle drawerToggle; //toggle button
    private NavigationView mNavigationview;
TextView norecord;
    Integer id;
    Database database;
    String[] seperator;
    String[] seperator1;
    SimpleDateFormat df;
    String details;
    android.support.v7.widget.AppCompatButton create, btn;
    FloatingActionButton button;
    Date dateobject;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    String ownername;
    com.rengwuxian.materialedittext.MaterialEditText amount, description, date;
    SharedPreferences sharedPreferences;
    String selectedstatus;
    RecyclerView recyclerView;
    private RecyclerView.Adapter recycleradapter;
    ArrayList<Transaction> allrecords;
    Integer lastamount;
    Integer currentamount;
    Integer netamount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_all_transactions );
        sharedPreferences = getSharedPreferences( "login", MODE_PRIVATE );

norecord = (TextView) findViewById( R.id.norecord );
        dlayout = (DrawerLayout) findViewById( R.id.drawer );
        drawerToggle = new ActionBarDrawerToggle( this, dlayout, R.string.open, R.string.close );
        dlayout.addDrawerListener( drawerToggle );
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        NavigationView navigationMenu = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview = (NavigationView) findViewById( R.id.navmenu );
        mNavigationview.setNavigationItemSelectedListener( this );

        dateobject = Calendar.getInstance().getTime();
        df = new SimpleDateFormat( "dd-MMM-yyyy" );
        button = (FloatingActionButton) findViewById( R.id.button );


        recyclerView = (RecyclerView) findViewById( R.id.recylcerview22 );


        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        } );

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            id = extras.getInt( "id" ); // information contains boolean+gender+age+lifestylecategory.

        }
        database = new Database( this );

        seperator = database.specificMember( id ).split( ";" );
        ownername = sharedPreferences.getString( "username", null );
        Log.d( "ALLTRANSACTION ", String.valueOf( database.alltransaction( seperator[0], ownername ).size() ) );

        try {

            alltransactions();
            norecord.setVisibility( View.INVISIBLE);
            recyclerView.setVisibility( View.VISIBLE );
        } catch (Exception e) {

norecord.setVisibility( View.VISIBLE );
recyclerView.setVisibility( View.INVISIBLE );
        }
    }


    public void onBackPressed() {

        finish();
        startActivity( new Intent( getApplicationContext(), Home.class ) );
    }

    private void createOrder() {
        builder = new AlertDialog.Builder( this );

        dialog = new Dialog( this );
        LayoutInflater layoutInflater = getLayoutInflater();

        View customView = layoutInflater.inflate( R.layout.editdetail, null );


        amount = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.amount );
        description = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.descritpion );
        date = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.date );
        btn = (android.support.v7.widget.AppCompatButton) customView.findViewById( R.id.btn );
        final RadioGroup rg = (RadioGroup) customView.findViewById( R.id.statusofamount );
        final RadioButton borrow = (RadioButton) customView.findViewById( R.id.borrow );
        final RadioButton lend = (RadioButton) customView.findViewById( R.id.lend );
        date.setText( df.format( dateobject ) );
        date.setEnabled( false );

        borrow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedstatus = borrow.getText().toString();
                Log.d( "dsfsdfsf", selectedstatus );
            }
        } );

        lend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedstatus = lend.getText().toString();
                Log.d( "dsfsdfsf", selectedstatus );
            }
        } );

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//
//                if (radioGroup.getCheckedRadioButtonId() == -1)
//                {
//                    Toast.makeText(getApplicationContext()," Select statusof the transaction.", Toast.LENGTH_SHORT).show();
//
//                }

                if (TextUtils.isEmpty( description.getText().toString() )
                        && (TextUtils.isEmpty( amount.getText().toString() ))
                        && (rg.getCheckedRadioButtonId() == -1)

                        ) {
                    Toast.makeText( getApplicationContext(), "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();

                } else if (rg.getCheckedRadioButtonId() == -1) {
                    Toast.makeText( getApplicationContext(), "Select status of the  transaction to continue.", Toast.LENGTH_SHORT ).show();

                } else if (TextUtils.isEmpty( description.getText().toString() )) {

                    Toast.makeText( getApplicationContext(), "Set description for the transaction.", Toast.LENGTH_LONG ).show();
                    return;

                } else if (TextUtils.isEmpty( amount.getText().toString() )) {

                    Toast.makeText( getApplicationContext(), "Enter the amount of the current transaction.", Toast.LENGTH_LONG ).show();
                    return;

                } else {

                    seperator1 = database.fetchingBalanceDetail( seperator[0] ).split( ";" );

                    if (seperator1[1].equals( "borrowed" ) && selectedstatus.equals( "borrowed" )) {
                      Log.d( "FIRSTONE","11" );
                        lastamount = Integer.parseInt( seperator1[0] );
                        currentamount = Integer.parseInt( amount.getText().toString().trim() );
                        netamount = lastamount + currentamount;
                        database.updateMemberInformation( id, selectedstatus, netamount + "" );
                    }

                   else if (seperator1[1].equals( "lend" ) && selectedstatus.equals( "lend" )) {
                        lastamount = Integer.parseInt( seperator1[0] );
                        currentamount = Integer.parseInt( amount.getText().toString().trim() );
                        netamount = lastamount + currentamount;
                        database.updateMemberInformation( id, selectedstatus, netamount + "" );
                    }

                   else if (seperator1[1].equals( "borrowed" ) && selectedstatus.equals( "lend" )) {
                        lastamount = Integer.parseInt( seperator1[0] );
                        currentamount = Integer.parseInt( amount.getText().toString().trim() );
                        netamount = currentamount - lastamount;


                        if(currentamount> lastamount){
                            database.updateMemberInformation( id, "lend", Math.abs( netamount ) + "" );

                        }
                        else{
                            database.updateMemberInformation( id, "borrowed", Math.abs( netamount ) + "" );

                        }


                    }

                    else if (seperator1[1].equals( "lend" ) && selectedstatus.equals( "borrowed" )) {
                        lastamount = Integer.parseInt( seperator1[0] );
                        currentamount = Integer.parseInt( amount.getText().toString().trim() );
                        netamount = currentamount - lastamount;


                        Log.d( "dfdfdsfsd", String.valueOf( lastamount ) );
                        Log.d( "dfdfdsfsd", String.valueOf( currentamount ) );
                        Log.d( "dfdfdsfsd", String.valueOf( netamount ) );

                        if(currentamount> lastamount){
                            database.updateMemberInformation( id, "lend", Math.abs( netamount ) + "" );

                        }
                        else{
                            database.updateMemberInformation( id, "borrowed", Math.abs( netamount ) + "" );

                        }


                    }
                    else{
                        database.updateMemberInformation( id, selectedstatus, amount.getText().toString().trim() );

                    }
                    database.createTransaction( new Transaction(
                            ownername, seperator[0],
                            selectedstatus, amount.getText().toString().trim(),
                            description.getText().toString().trim(), date.getText().toString().trim()
                    ) );


                    alltransactions();
                    dialog.dismiss();

                }

            }
        } );

        builder.setCancelable( true );
        builder.setView( customView );
        dialog = builder.create();
        dialog.show();


    }

    private void alltransactions() {
        allrecords = database.alltransaction( seperator[0], ownername );
        recycleradapter = new TransactionRecord( allrecords, AllTransactions.this );
        LinearLayoutManager dpms = new LinearLayoutManager( getApplicationContext(), LinearLayoutManager.VERTICAL, false );
        recyclerView.setLayoutManager( dpms );
        recyclerView.setAdapter( recycleradapter );

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

            case R.id.userdetail: {
                Intent intent = new Intent( this, DetailedActivity.class );
                intent.putExtra( "id", id );
                startActivity( intent );
                break;
            }
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
