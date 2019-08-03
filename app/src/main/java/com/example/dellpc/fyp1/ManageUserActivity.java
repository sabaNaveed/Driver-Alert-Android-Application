package com.example.dellpc.fyp1;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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

public class ManageUserActivity extends AppCompatActivity
{
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private DatabaseHelper databaseHelper;

    private InputValidation inputValidation;
    private UserBO selectedUser;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText txtName, txtNumber,em;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);
        inputValidation = new InputValidation(ManageUserActivity.this);
        databaseHelper = new DatabaseHelper( this );

        txtName = findViewById( R.id.textInputEditTextName );
        txtNumber = findViewById( R.id.textInputEditTextPassword );
        em = findViewById( R.id.textInputEditTextEmail );

        btnUpdate = findViewById(R.id.btnUpdate );
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateUser();

            }
        });

        //btnDelete = findViewById( R.id.btnDelete );


        populateFields( getIntent().getLongExtra(Constants.KEY_USER_ID, -1));
    }

    private void populateFields( long userId )
    {
        if( userId != -1 )
        {
            UserBO selectedUser = getUserbyId(userId);
            if( selectedUser != null )
            {em.setText( selectedUser.getEmail() );
                txtName.setText( selectedUser.getName() );
                txtNumber.setText( selectedUser.getPassword() );

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
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_USER_NAME,
                DatabaseHelper.COLUMN_USER_EMAIL,
                DatabaseHelper.COLUMN_USER_PASSWORD};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,
                columns,
                DatabaseHelper.COLUMN_USER_ID + " = ?",
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
            selectedUser.setEmail( cursor.getString(2) );
            selectedUser.setPassword( cursor.getString(3));
        }

        cursor.close();
        db.close();

        return selectedUser;
    }



    private void updateUser()
    {
        String name = txtName.getText().toString();
        String pw = txtNumber.getText().toString();
        String email = em.getText().toString();
        boolean t=postDataToSQLite();
        if(t){
            if(TextUtils.isEmpty(name) )
            {
                txtName.setError( "Please enter name" );
            }
            else if(TextUtils.isEmpty(email) )
            {
                txtNumber.setError( "Please enter" );
            }
            else if(TextUtils.isEmpty(pw) )
            {
                txtNumber.setError( "Please enter" );
            }
            else
            {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put( DatabaseHelper.COLUMN_USER_NAME, name.trim() );
                values.put( DatabaseHelper.COLUMN_USER_EMAIL, email.trim() );
                values.put( DatabaseHelper.COLUMN_USER_PASSWORD, pw.trim() );

                int updatedRows = db.update(
                        DatabaseHelper.TABLE_USER,
                        values,
                        DatabaseHelper.COLUMN_USER_ID + " = ?",
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

        }}
    private boolean postDataToSQLite() {


        if (!inputValidation.isInputEditTextFilled(txtName, textInputLayoutName, getString(R.string.error_message_name))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(em, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(em, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(txtNumber, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return false;
        }
        if (!inputValidation.isInputEditTextMatches(txtNumber, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return false;
        }


        emptyInputEditText();





        return true;
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        txtName.setText(null);
        em.setText(null);
        txtNumber.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}

