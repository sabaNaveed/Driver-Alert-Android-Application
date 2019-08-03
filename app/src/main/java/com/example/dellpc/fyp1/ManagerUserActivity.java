package com.example.dellpc.fyp1;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class ManagerUserActivity extends AppCompatActivity
{
    private DatabaseHelper databaseHelper;

    private UserBO selectedUser;

    private EditText txtName, txtNumber;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Contact");
        setSupportActionBar(toolbar);
        databaseHelper = new DatabaseHelper( this );

        txtName = findViewById( R.id.txtName );
        txtNumber = findViewById( R.id.txtNumber );

        btnUpdate = findViewById(R.id.btnUpdate );
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateUser();
            }
        });

        btnDelete = findViewById( R.id.btnDelete );
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteUser();
            }
        });

        populateFields( getIntent().getLongExtra(Constants.KEY_USER_ID, -1));
    }

    private void populateFields( long userId )
    {
        if( userId != -1 )
        {
            UserBO selectedUser = getUserbyId(userId);
            if( selectedUser != null )
            {
                txtName.setText( selectedUser.getName() );
                txtNumber.setText( selectedUser.getNumber() );
            }
            else
            {
                Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "User ID not available", Toast.LENGTH_SHORT).show();
        }
    }

    private UserBO getUserbyId( long userId )
    {
        selectedUser = null;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {
                DBContract.USER._ID,
                DBContract.USER.COL_FULL_NAME,
                DBContract.USER.COL_NUMBER};

        Cursor cursor = db.query(
                DBContract.USER.TABLE_NAME,
                columns,
                DBContract.USER._ID + " = ?",
                new String[]{ String.valueOf(userId)},
                null,
                null,
                null );

        if( cursor != null )
        {
            cursor.moveToFirst();

            selectedUser = new UserBO();
            selectedUser.setId(cursor.getLong(0) );
            selectedUser.setName( cursor.getString(1) );
            selectedUser.setNumber( cursor.getString(2) );
        }

        cursor.close();
        db.close();

        return selectedUser;
    }

    private void deleteUser()
    {
        if( selectedUser != null )
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            int deletedRows = db.delete(
                    DBContract.USER.TABLE_NAME,
                    DBContract.USER._ID + " = ?",
                    new String[]{ String.valueOf(selectedUser.getId())} );

            db.close();

            if( deletedRows > 0 )
            {
                setResult( Constants.RESULT_CODE_DELETE );
                finish();
            }
            else
            {
                Toast.makeText( this, "User not deleted!", Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void updateUser()
    {
        String name = txtName.getText().toString();
        String number = txtNumber.getText().toString();

        if(TextUtils.isEmpty(name) )
        {
            txtName.setError( "Please enter name" );
        }

        else   if(name.length() > 15)
        { txtName.setError("Name is too long");}
        else if(TextUtils.isEmpty(number) )
        {
            txtNumber.setError( "Please enter number" );
        }

        else  if(!Pattern.matches("^[0-9]*$", number))
        {txtNumber.setError("This field only accepts numbers"); }

        else   if(number.length() != 11)
        { txtNumber.setError("Enter 11 digit number");}

        else
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put( DBContract.USER.COL_FULL_NAME, name.trim() );
            values.put( DBContract.USER.COL_NUMBER, number.trim() );

            int updatedRows = db.update(
                    DBContract.USER.TABLE_NAME,
                    values,
                    DBContract.USER._ID + " = ?",
                    new String[]{ String.valueOf(selectedUser.getId())});

            db.close();

            if( updatedRows > 0 )
            {
                setResult( Constants.RESULT_CODE_EDIT );
                finish();
            }
            else
            {
                Toast.makeText( this, "User not updated!", Toast.LENGTH_LONG ).show();
            }
        }
    }
}
