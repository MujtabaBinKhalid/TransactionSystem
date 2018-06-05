 package com.lifeline.fyp.bankingsystem.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.R;
import com.lifeline.fyp.bankingsystem.models.User;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

 public class MainActivity extends AppCompatActivity {

    android.support.v7.widget.AppCompatButton login,
            actuallogin, signup, createaccount, checkusername, changepassword;
    RelativeLayout homepage, loginpage, signuppage, forgotpassword;
    com.rengwuxian.materialedittext.MaterialEditText creatingusername,
            creatingpassword, creatingphonenumber, usernametext, passwordtext, usernamechecking, reenterpassword;
    Database database;
    SharedPreferences sharedPreferences;
    String username, password, phonenumber;
    TextView forgotpasswordtext;
    Integer userid;
     CircleImageView userdp;
     protected static final int CAMERA_REQUEST = 0;
     protected static final int GALLERY_PICTURE = 1;
     private Intent pictureActionIntent = null;
     Bitmap bitmap;
     String selectedImagePath;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        userdp = (CircleImageView) findViewById( R.id.image );

        userdp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice();
            }
        } );
        sharedPreferences = getSharedPreferences( "login", MODE_PRIVATE );
        if (sharedPreferences.contains( "username" ) && sharedPreferences.contains( "password" )) {
            startActivity( new Intent( getApplicationContext(), Home.class ) );
        }

        //buttons
        login = (android.support.v7.widget.AppCompatButton) findViewById( R.id.login );
        actuallogin = (android.support.v7.widget.AppCompatButton) findViewById( R.id.actuallogin );
        signup = (android.support.v7.widget.AppCompatButton) findViewById( R.id.signup );
        createaccount = (android.support.v7.widget.AppCompatButton) findViewById( R.id.createaccount );
        checkusername = (android.support.v7.widget.AppCompatButton) findViewById( R.id.checkusername );
        changepassword = (android.support.v7.widget.AppCompatButton) findViewById( R.id.changepassword );

        // edittexts
        creatingusername = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingusername );
        creatingpassword = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingpassword );
        creatingphonenumber = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.creatingphonenumber );
        usernametext = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.username );
        passwordtext = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.password );
        usernamechecking = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.usernamechecking );
        reenterpassword = (com.rengwuxian.materialedittext.MaterialEditText) findViewById( R.id.reenterpassword );


        //textview
        forgotpasswordtext = (TextView) findViewById( R.id.forgotpasswordtext );

        //relativelayouts.
        homepage = (RelativeLayout) findViewById( R.id.homepage );
        loginpage = (RelativeLayout) findViewById( R.id.loginpage );
        signuppage = (RelativeLayout) findViewById( R.id.signuppage );
        forgotpassword = (RelativeLayout) findViewById( R.id.forgotpassword );


        // database object.
        database = new Database( this );

        forgotpasswordtext.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpasswordView();
            }
        } );
        checkusername.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkinguser();
            }
        } );

        changepassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changingPassword();
            }
        } );
        // making the loginpage appear.
        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPage();
            }
        } );

        // making the signup page appear.
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupPage();
            }
        } );

        // creating account.
        createaccount.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatingUserAccount();
            }
        } );

        // login user.
        actuallogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        } );

    }

    private void loginPage() {
        homepage.setVisibility( View.INVISIBLE );
        signuppage.setVisibility( View.INVISIBLE );
        loginpage.setVisibility( View.VISIBLE );
    }

    private void signupPage() {
        homepage.setVisibility( View.INVISIBLE );
        loginpage.setVisibility( View.INVISIBLE );
        signuppage.setVisibility( View.VISIBLE );
    }

    private void creatingUserAccount() {
        // strings.
        username = creatingusername.getText().toString().trim();
        password = creatingpassword.getText().toString().trim();
        phonenumber = creatingphonenumber.getText().toString().trim();


        if (TextUtils.isEmpty( username )
                && (TextUtils.isEmpty( password ))
                && (TextUtils.isEmpty( phonenumber )
        )) {
            Toast.makeText( getApplicationContext(), "Fill in the fields, to proceed.", Toast.LENGTH_LONG ).show();

        } else if (TextUtils.isEmpty( username )) {

            Toast.makeText( getApplicationContext(), "Set your username,to proceed.", Toast.LENGTH_LONG ).show();
            return;

        } else if (TextUtils.isEmpty( password )) {

            Toast.makeText( getApplicationContext(), "Set your password,to proceed.", Toast.LENGTH_LONG ).show();
            return;

        } else if (TextUtils.isEmpty( phonenumber )) {

            Toast.makeText( getApplicationContext(), "Set your phone number, to proceed.", Toast.LENGTH_LONG ).show();
            return;
        } else {

            boolean finding = database.unqiueUser( username.trim() );

            Log.d( "checkingDetails", username );
            Log.d( "checkingDetails", phonenumber );
            Log.d( "checkingDetails", password );
            Log.d( "checkingDetails", String.valueOf( finding ) );

            if (finding) {
                Toast.makeText( getApplicationContext(), "Username exists", Toast.LENGTH_SHORT ).show();
            } else {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString( "username", username );
                e.putString( "password", password );
                e.commit();

                database.creatingUser( new User( creatingusername.getText().toString().trim()
                        , creatingpassword.getText().toString().trim()
                        , creatingphonenumber.getText().toString().trim() ) );

                finish();
                startActivity( new Intent( MainActivity.this, Home.class ) );


            }


        }


    }

    private void loginuser() {
        username = usernametext.getText().toString().trim();
        password = passwordtext.getText().toString().trim();

        Log.e( "dfdfsfsdf", username );
        Log.e( "dfdfsfsdf", password );
        if (TextUtils.isEmpty( username )) {

            Toast.makeText( getApplicationContext(), "Fill in the username,to proceed.", Toast.LENGTH_LONG ).show();
            return;

        } else if (TextUtils.isEmpty( password )) {

            Toast.makeText( getApplicationContext(), "Enter your password,to proceed.", Toast.LENGTH_LONG ).show();
            return;

        } else {
            boolean validating = database.validateUser( username.trim(), password.trim() );
            if (validating) {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putString( "username", username );
                e.putString( "password", password );
                e.commit();
                finish();
                startActivity( new Intent( MainActivity.this, Home.class ) );
            } else {
                Toast.makeText( getApplicationContext(), "In correct Crededntials", Toast.LENGTH_SHORT ).show();
            }
        }

    }

    private void forgotpasswordView() {
        homepage.setVisibility( View.INVISIBLE );
        loginpage.setVisibility( View.INVISIBLE );
        signuppage.setVisibility( View.INVISIBLE );
        forgotpassword.setVisibility( View.VISIBLE );
    }

    private void checkinguser() {
        boolean testuser = database.unqiueUser( usernamechecking.getText().toString().trim() );
        Log.e( "DLDLDLDLDLDLD", String.valueOf( testuser ) );

        if (testuser) {
            usernamechecking.setVisibility( View.INVISIBLE );
            reenterpassword.setVisibility( View.VISIBLE );
            checkusername.setVisibility( View.INVISIBLE );
            changepassword.setVisibility( View.VISIBLE );
      userid = database.fetchingUser(usernamechecking.getText().toString().trim());
        } else {
            Toast.makeText( getApplicationContext(), "No such user exists", Toast.LENGTH_SHORT ).show();
        }


    }

    private void changingPassword() {

        Log.e( "Dfdsfsfsd",usernamechecking.getText().toString() );
        Log.e( "Dfdsfsfsd",reenterpassword.getText().toString() );
        database.updatepassword( reenterpassword.getText().toString(),userid);
        Toast.makeText( getApplicationContext(), "Password updated!", Toast.LENGTH_SHORT ).show();
        homepage.setVisibility( View.INVISIBLE );
        loginpage.setVisibility( View.VISIBLE );
        signuppage.setVisibility( View.INVISIBLE );
        forgotpassword.setVisibility( View.INVISIBLE );

    }


    public void onBackPressed() {


         if(loginpage.getVisibility() == 0 || signuppage.getVisibility()==0){
             homepage.setVisibility( View.VISIBLE );
             loginpage.setVisibility( View.INVISIBLE );
             signuppage.setVisibility( View.INVISIBLE );
             forgotpassword.setVisibility( View.INVISIBLE );
         }
         else if(forgotpassword.getVisibility() == 0){
             homepage.setVisibility( View.INVISIBLE );
             loginpage.setVisibility( View.VISIBLE );
             signuppage.setVisibility( View.INVISIBLE );
             forgotpassword.setVisibility( View.INVISIBLE );

         }
         else{
             finish();
//             final AlertDialog.Builder builder = new AlertDialog.Builder( MainActivity.this );
//             builder.setMessage( "Do you want to exit?" );
//             builder.setCancelable( true );
//             builder.setNegativeButton( "Yes", new DialogInterface.OnClickListener() {
//                 @Override
//                 public void onClick(DialogInterface dialogInterface, int i) {
//                     finish();
//                 }
//             } );
//             builder.setPositiveButton( "No", new DialogInterface.OnClickListener() {
//                 @Override
//                 public void onClick(DialogInterface dialogInterface, int i) {
//                     dialogInterface.cancel();
//                 }
//             } );
//
//             AlertDialog alertDialog = builder.create();
//             alertDialog.show();
         }
    }

    private void choice(){
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                GALLERY_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment
                                .getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(f));

                        startActivityForResult(intent,
                                CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         super.onActivityResult(requestCode, resultCode, data);

         bitmap = null;
         selectedImagePath = null;
         if(resultCode != RESULT_CANCELED){
             if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

                 File f = new File( Environment.getExternalStorageDirectory()
                         .toString());
                 for (File temp : f.listFiles()) {
                     if (temp.getName().equals("temp.jpg")) {
                         f = temp;
                         break;
                     }
                 }

                 if (!f.exists()) {

                     Toast.makeText(getBaseContext(),

                             "Error while capturing image", Toast.LENGTH_LONG)

                             .show();

                     return;

                 }

                 try {

                     bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                     bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                     int rotate = 0;
                     try {
                         ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                         int orientation = exif.getAttributeInt(
                                 ExifInterface.TAG_ORIENTATION,
                                 ExifInterface.ORIENTATION_NORMAL);

                         switch (orientation) {
                             case ExifInterface.ORIENTATION_ROTATE_270:
                                 rotate = 270;
                                 break;
                             case ExifInterface.ORIENTATION_ROTATE_180:
                                 rotate = 180;
                                 break;
                             case ExifInterface.ORIENTATION_ROTATE_90:
                                 rotate = 90;
                                 break;
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     Matrix matrix = new Matrix();
                     matrix.postRotate(rotate);
                     bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                             bitmap.getHeight(), matrix, true);



                     userdp.setImageBitmap(bitmap);
                     //storeImageTosdCard(bitmap);
                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }

             } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
                 if (data != null) {

                     Uri selectedImage = data.getData();
                     String[] filePath = { MediaStore.Images.Media.DATA };
                     Cursor c = getContentResolver().query(selectedImage, filePath,
                             null, null, null);
                     c.moveToFirst();
                     int columnIndex = c.getColumnIndex(filePath[0]);
                     selectedImagePath = c.getString(columnIndex);
                     c.close();

                     if (selectedImagePath != null) {
                         Log.d( "Sdfsdfsd","dfsfsd" );
                     }

                     bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                     // preview image
                     bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);



                     userdp.setImageBitmap(bitmap);

                 } else {
                     Toast.makeText(getApplicationContext(), "Cancelled",
                             Toast.LENGTH_SHORT).show();
                 }
             }

         }

     }
}



