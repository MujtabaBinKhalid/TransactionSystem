package com.lifeline.fyp.bankingsystem.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mujtaba_khalid on 5/23/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.models.Transaction;
import com.lifeline.fyp.bankingsystem.models.User;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // database version.
    private static final int DATABASE_VERSION = 7;

    // database name.
    private static final String DATABASE_NAME = "bankingsystem.db";

    // common cols.
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_USERNAME = "_username";
    private static final String COLUMN_MOBILENUMBER = "mobilenumber";

    // user table.
    private static final String TABLE_USER = "user";
    private static final String TABLE_NETTRANSACTION = "nettransaction";
    private static final String COLUMN_PASSWORD = "password";

    // member table.
    private static final String TABLE_MEMBER = "member";

    private static final String COLUMN_CNIC = "cnic";
    private static final String COLUMN_OWNERNAME = "owner";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

//all records

    public ArrayList<Member> usersdata;
    public ArrayList<Transaction> alltransaction;


    public Database(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_CNIC + " TEXT,"
            + COLUMN_MOBILENUMBER + " TEXT"
            + ")";

    private static final String CREATE_TABLE_NETTRANSACTION = "CREATE TABLE " + TABLE_NETTRANSACTION + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_STATUS + " TEXT,"
            + COLUMN_OWNERNAME + " TEXT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_AMOUNT + " TEXT"
            + ")";


    private static final String CREATE_TABLE_MEMBER = "CREATE TABLE " + TABLE_MEMBER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_OWNERNAME + " TEXT,"
            + COLUMN_STATUS + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_AMOUNT + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_CNIC + " TEXT,"
            + COLUMN_MOBILENUMBER + " TEXT"
            + ")";


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL( CREATE_TABLE_USER );
        db.execSQL( CREATE_TABLE_MEMBER );
        db.execSQL( CREATE_TABLE_NETTRANSACTION );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE  IF EXISTS " + TABLE_USER );
        db.execSQL( "DROP TABLE  IF EXISTS " + TABLE_MEMBER );
        db.execSQL( "DROP TABLE  IF EXISTS " + TABLE_NETTRANSACTION );

        onCreate( db );
    }


    // creating account
    public void creatingUser(User user) {

        ContentValues values = new ContentValues();
        values.put( COLUMN_USERNAME, user.get_username() );
        values.put( COLUMN_MOBILENUMBER, user.get_cellnumber() );
        values.put( COLUMN_PASSWORD, user.get_password() );

        SQLiteDatabase db = getWritableDatabase();
        long rowinserted = db.insert( TABLE_USER, null, values );
        db.close();
        Log.d( "ValueInserted", String.valueOf( rowinserted ) );
    }

    // for creating unique users.
    public boolean unqiueUser(String username) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_USERNAME + "='" + username + "'", null );
        if (cursor.getCount() > 0)
            return true;
        return false;
    }

    // login .
    public boolean validateUser(String username, String password) {
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_USERNAME + "='" + username + "'AND " + COLUMN_PASSWORD + "='" + password + "'", null );
        if (cursor.getCount() > 0)
            return true;
        return false;
    }



    // creating members and its all details

    public void creatingMember(Member member){

        ContentValues value = new ContentValues();

        value.put( COLUMN_USERNAME, member.get_username() );
        value.put( COLUMN_OWNERNAME, member.get_owner() );
        value.put( COLUMN_MOBILENUMBER, member.get_cellnumber() );
        value.put( COLUMN_CNIC, member.get_cnic() );
        value.put( COLUMN_STATUS, member.getStatus() );
        value.put( COLUMN_AMOUNT, member.getAmount() );
        value.put( COLUMN_DESCRIPTION, member.get_description() );
        value.put( COLUMN_DATE, member.getDate() );


        SQLiteDatabase db = getWritableDatabase();

        long rowinserted = db.insert( TABLE_MEMBER, null, value );
        db.close();
        Log.d( "ValueInserted", String.valueOf( rowinserted ) );


    }

    public void deleteUserAccount(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( "DELETE FROM " + TABLE_MEMBER + " WHERE " + COLUMN_ID + "=\"" + id + "\";" );
    }

    public ArrayList<Member> fetchingAllData(String username) {


        usersdata = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_MEMBER + " WHERE "
                        + COLUMN_OWNERNAME + "='" + username +"'" ,  null);
        cursor.moveToFirst();
        Log.e( "SSSASSA", String.valueOf( cursor.moveToFirst() ) );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                usersdata.add( new Member( Integer.parseInt( cursor.getString( cursor.getColumnIndex( COLUMN_ID ) ) ),
                        cursor.getString( cursor.getColumnIndex( COLUMN_USERNAME ) ),
                        cursor.getString( cursor.getColumnIndex( COLUMN_MOBILENUMBER ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_CNIC ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_STATUS ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_DESCRIPTION ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_AMOUNT ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_DATE ) )
                         ) );
                cursor.moveToNext();
            }
        }

        Log.e( "dfldfdfd", String.valueOf( usersdata.size() ) );
        Log.e( "dfldfdfd",username );

        db.close();
        return usersdata;
    }

    public void updateMemberInformation(Integer id,String status,String amount){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("status",status); // borrowed or lend
        cv.put("amount",amount);


        db.update(TABLE_MEMBER, cv, "_id="+id, null);

    }

    public  String specificMember(Integer id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_MEMBER + " WHERE "
                        + COLUMN_ID + "='" + id +"'" ,  null);
        cursor.moveToFirst();

  return cursor.getString( cursor.getColumnIndex( COLUMN_USERNAME ) )+";"+
   cursor.getString( cursor.getColumnIndex( COLUMN_MOBILENUMBER ) )+";"+
          cursor.getString( cursor.getColumnIndex( COLUMN_CNIC ) )+";"+
          cursor.getString( cursor.getColumnIndex( COLUMN_STATUS ) )+";"+
          cursor.getString( cursor.getColumnIndex( COLUMN_DESCRIPTION ) )+";"+
          cursor.getString( cursor.getColumnIndex( COLUMN_AMOUNT ) )+";"+
          cursor.getString( cursor.getColumnIndex( COLUMN_DATE ) )+";";
    }

    public void updatepassword(String password,Integer id){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("password",password);

        db.update(TABLE_USER, cv, "_id="+id, null);

    }

    public Integer fetchingUser(String name){
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_USERNAME + "='" + name +"'" ,  null);
        cursor.moveToFirst();

        return Integer.parseInt( cursor.getString( cursor.getColumnIndex( COLUMN_ID ) ) );
    }

    public String fetchingBalanceDetail(String name){
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_MEMBER + " WHERE "
                        + COLUMN_USERNAME + "='" + name +"'" ,  null);
        cursor.moveToFirst();

        return  cursor.getString( cursor.getColumnIndex( COLUMN_AMOUNT ) )+";"
                +cursor.getString( cursor.getColumnIndex( COLUMN_STATUS ) );
    }

    public String fetchingMobileNumber(String name){
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_USER + " WHERE "
                        + COLUMN_USERNAME + "='" + name +"'" ,  null);
        cursor.moveToFirst();

        return  cursor.getString( cursor.getColumnIndex( COLUMN_MOBILENUMBER ) );

    }

    public ArrayList<Transaction> alltransaction(String username, String ownername){
        alltransaction = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = getReadableDatabase().rawQuery(
                "SELECT * FROM " + TABLE_NETTRANSACTION + " WHERE "
                        + COLUMN_USERNAME + "='" + username + "'AND " + COLUMN_OWNERNAME + "='" + ownername + "'", null );
        cursor.moveToFirst();
        Log.e( "SSSASSA", String.valueOf( cursor.moveToFirst() ) );
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                alltransaction.add( new Transaction(
                        cursor.getString( cursor.getColumnIndex( COLUMN_USERNAME ) ),
                        cursor.getString( cursor.getColumnIndex( COLUMN_OWNERNAME ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_STATUS ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_DESCRIPTION ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_AMOUNT ) )
                        , cursor.getString( cursor.getColumnIndex( COLUMN_DATE ) )
                ));
                cursor.moveToNext();
            }
        }

        Log.e( "qqqqqwww", String.valueOf( alltransaction.size() ) );
        Log.e( "qqqqwwww",username );
        Log.e( "qqqwwwww",ownername );

        db.close();
        return alltransaction;

    }

    public void createTransaction(Transaction transaction){
        ContentValues values = new ContentValues();
        values.put( COLUMN_USERNAME, transaction.getUsername() );
        values.put( COLUMN_OWNERNAME, transaction.getOwnername() );
        values.put( COLUMN_AMOUNT, transaction.getAmount() );
        values.put( COLUMN_STATUS, transaction.getStatus() );
        values.put( COLUMN_DATE, transaction.getDate() );
        values.put( COLUMN_DESCRIPTION, transaction.getDescription() );


        SQLiteDatabase db = getWritableDatabase();
        long rowinserted = db.insert( TABLE_NETTRANSACTION, null, values );
        db.close();
        Log.d( "ValueInserted__", String.valueOf( rowinserted ) );
    }
}
