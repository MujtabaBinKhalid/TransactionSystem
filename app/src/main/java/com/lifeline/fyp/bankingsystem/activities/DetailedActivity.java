package com.lifeline.fyp.bankingsystem.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.bankingsystem.R;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    Integer id;
    Database database;
    TextView personname, personcnic,
            personnumber, personstatus,personamount,
            persondetail,persondate,amounttext,destext,datetext;
    private RadioGroup radioGroup;
    String[] seperator;
    SimpleDateFormat df;
    android.support.v7.widget.AppCompatButton create,create2,btn;
    Date dateobject;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    String ownername;
    com.rengwuxian.materialedittext.MaterialEditText amount,description,date;
    SharedPreferences sharedPreferences;
    String selectedstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detailed );

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);

         dateobject = Calendar.getInstance().getTime();

         df = new SimpleDateFormat("dd-MMM-yyyy");
        personname = (TextView) findViewById( R.id.personname );
        personnumber = (TextView) findViewById( R.id.personnumber );
        personcnic = (TextView) findViewById( R.id.personcnic );
        personstatus = (TextView) findViewById( R.id.personstatus );
        personamount = (TextView) findViewById( R.id.personamount );
        persondetail = (TextView) findViewById( R.id.persondetail );
        persondate = (TextView) findViewById( R.id.persondate );

        datetext = (TextView) findViewById( R.id.datetext );
        destext = (TextView) findViewById( R.id.destext );
        amounttext = (TextView) findViewById( R.id.amounttext );
        create = (android.support.v7.widget.AppCompatButton) findViewById( R.id.create );
        create2 = (android.support.v7.widget.AppCompatButton) findViewById( R.id.create2 );



        create.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        } );

        create2.setOnClickListener( new View.OnClickListener() {
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
        personname.setText( seperator[0] );
        personnumber.setText( seperator[1] );
        personcnic.setText( seperator[2] );
        personstatus.setText( seperator[3] );
        ownername = sharedPreferences.getString("username", null);
        Log.d( "ALLTRANSACTION ", String.valueOf( database.alltransaction( seperator[0], ownername).size() ) );


        if(seperator[4].equals( "null" ) || seperator[5].equals( "null" )){
            Log.d( "sdfsdfsd","sdfsdfsd" );
         create.setVisibility( View.INVISIBLE );
         create2.setVisibility( View.INVISIBLE );
         persondetail.setVisibility( View.INVISIBLE );
         persondate.setVisibility( View.INVISIBLE );
         personamount.setVisibility( View.INVISIBLE );
         destext.setVisibility( View.INVISIBLE );
         amounttext.setVisibility( View.INVISIBLE );
         datetext.setVisibility( View.INVISIBLE );

        }
        else{
            Log.d( "sdfsdfsd","sdfsdfsd22" );
            persondetail.setText( seperator[4] );
            personamount.setText( seperator[5] );
            persondate.setText( seperator[6] );
            create2.setVisibility( View.INVISIBLE );
            create.setVisibility( View.INVISIBLE );
        }
    }

    public void onBackPressed() {

        finish();
        startActivity( new Intent( getApplicationContext(), Home.class ) );
    }

    private void createOrder(){
        builder = new AlertDialog.Builder(this);

        dialog = new Dialog(this );
        LayoutInflater layoutInflater = getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.editdetail, null);


        amount = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.amount );
        description = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.descritpion );
        date = (com.rengwuxian.materialedittext.MaterialEditText) customView.findViewById( R.id.date);
        btn = (android.support.v7.widget.AppCompatButton)customView. findViewById( R.id.btn );
       final RadioGroup rg = (RadioGroup) customView.findViewById( R.id.statusofamount );
       final RadioButton borrow = (RadioButton) customView.findViewById( R.id.borrow );
       final RadioButton lend= (RadioButton) customView.findViewById( R.id.lend );
        date.setText( df.format( dateobject ) );
        date.setEnabled( false );

        borrow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             selectedstatus = borrow.getText().toString();
             Log.d( "dsfsdfsf",selectedstatus );
            }
        } );

        lend.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedstatus = lend.getText().toString();
                Log.d( "dsfsdfsf",selectedstatus );
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
                        && (rg.getCheckedRadioButtonId() == -1 )

                ) {
                    Toast.makeText( getApplicationContext(), "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();

                }
               else if (rg.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(getApplicationContext(),"Select status of the  transaction to continue.", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty( description.getText().toString() )) {

                    Toast.makeText( getApplicationContext(), "Set description for the transaction.", Toast.LENGTH_LONG ).show();
                    return;

                } else if (TextUtils.isEmpty( amount.getText().toString() )) {

                    Toast.makeText( getApplicationContext(), "Enter the amount of the current transaction.", Toast.LENGTH_LONG ).show();
                    return;

                }
else{


                                      database.createTransaction( new Transaction(
                          ownername,seperator[0],selectedstatus,amount.getText().toString().trim(),
                          description.getText().toString().trim(),date.getText().toString().trim()
                  ) );
                    dialog.dismiss();
                }

            }
        } );

        builder.setCancelable( true );
        builder.setView(customView);
        dialog = builder.create();
        dialog.show();



    }

}
