package com.example.dellpc.fyp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Main3Activity extends AppCompatActivity{
    private DatabaseHelper databaseHelper; Timer timer;

    int i=1;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);





        Button btn=(Button)findViewById(R.id.but);
        databaseHelper = new DatabaseHelper( this );
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
intent.putExtra( Constants.KEY_USER_ID, i );
                startActivity(intent);


            }
        });
    }
    public void btnEmergencyContact( )
    {
        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(Main3Activity.this);
        View mview =getLayoutInflater().inflate(R.layout.emergencycontactdialog ,null);

        final  EditText contactname = (EditText) mview.findViewById(R.id.Econtactname);
        final  EditText contactnumber=(EditText) mview.findViewById(R.id.Econtactnumber);

        Button ECbtnadd=(Button) mview.findViewById(R.id.addcontact);
        Button ECbtncancel=(Button) mview.findViewById(R.id.button3);
        mbuilder.setView(mview);
        final AlertDialog alertdialog= mbuilder.create();
        alertdialog.setCanceledOnTouchOutside(false);
        alertdialog.show();


        ECbtncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertdialog.dismiss();
            }
        });

        ECbtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Adding contact in db
                String name = contactname.getText().toString();
                String number = contactnumber.getText().toString();

                if(TextUtils.isEmpty(name) )
                {
                    contactname.setError( "Please enter name" );
                }

                else   if(name.length() > 15)
                { contactname.setError("Name is too long");}

                else if(TextUtils.isEmpty(number) )
                {
                    contactnumber.setError( "Please enter number" );}

                else  if(!Pattern.matches("^[0-9]*$", number))
                {contactnumber.setError("This field only accepts numbers"); }

                else   if(number.length() != 11)
                { contactnumber.setError("Enter 11 digit number");}

                else
                {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put( DBContract.USER.COL_FULL_NAME, name.trim() );
                    values.put( DBContract.USER.COL_NUMBER, number.trim() );

                    int numRows = (int) DatabaseUtils.queryNumEntries(db, "Users");

                    if(numRows<2)
                    {
                        long id = db.insert(DBContract.USER.TABLE_NAME, null, values);


                        if (id > 0)
                        {
                            Toast.makeText(Main3Activity.this, "Contact Added ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{Toast.makeText(Main3Activity.this, "Only 2 contacts can be added " , Toast.LENGTH_SHORT).show();}
                    db.close();

                    contactname.setText( "" );
                    contactnumber.setText( "" );
                    alertdialog.dismiss();
                }


            }
        });

    }


    //OPTION MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.main_menu, menu);
        return  true;
    }


    //item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.item1 :

                return true;

            case R.id.subitem1a :
                //Toast.makeText(Main3Activity.this,"Add Emergency Contact",Toast.LENGTH_SHORT).show();
                btnEmergencyContact();

                return true;
            case R.id.subitem1b :
                //Toast.makeText(MainActivity.this,"View Contacts",Toast.LENGTH_SHORT).show();
                startActivity( new Intent(Main3Activity.this, ShowUsersActivity.class));

                return true;



            case R.id.item4 :
                //Toast.makeText(MainActivity.this,"item2",Toast.LENGTH_SHORT).show();
                startActivity( new Intent(Main3Activity.this, InstructionsActivity.class));
                return true;
            case R.id.item3 :
                Intent i = new Intent( this, AlarmActivity.class );
                //i.putExtra( Constants.KEY_USER_ID, userBo.getId() );

                startActivityForResult( i, Constants.REQUEST_CODE_GET );
                //startActivity( new Intent(MainActivity.this, AlarmActivity.class));
                return true;
            case R.id.item2 :
                startActivity( new Intent(Main3Activity.this, ShowUserActivity.class));
                return true;
            case R.id.item :
                startActivity( new Intent(Main3Activity.this, Object.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( resultCode == Constants.RESULT_CODE_a )
        {
            //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            i=1;
        }
        else if( resultCode == Constants.RESULT_CODE_b )
        {
            //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            i=2;
        }
        else
            i=3;



    }



}

