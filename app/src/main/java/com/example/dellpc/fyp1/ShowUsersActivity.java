package com.example.dellpc.fyp1;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowUsersActivity extends AppCompatActivity
{

    private ArrayList<UserBO> users;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);
        usersListView = findViewById( R.id.listUser );

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                manageUser( users.get(position) );
            }
        });

        fetchAllUsers();
    }

    private void fetchAllUsers()
    {
        DatabaseHelper dbHelper = new DatabaseHelper( this );
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DBContract.USER._ID,
                DBContract.USER.COL_FULL_NAME,
                DBContract.USER.COL_NUMBER};

        Cursor cursor = db.query(
                DBContract.USER.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                DBContract.USER.COL_FULL_NAME + " ASC"
        );

        users = new ArrayList<>();
        while(cursor.moveToNext() )
        {
            UserBO userBO = new UserBO();
            userBO.setId(cursor.getLong(0) );
            userBO.setName( cursor.getString(1) );
            userBO.setNumber( cursor.getString(2) );

            users.add( userBO );
        }

        cursor.close();

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                users );

        usersListView.setAdapter( adapter );

        db.close();
    }

    private void manageUser( UserBO userBo )
    {
        Log.d( "ID", userBo.getId() + "");

        Intent i = new Intent( this, ManagerUserActivity.class );
        i.putExtra( Constants.KEY_USER_ID, userBo.getId() );

        startActivityForResult( i, Constants.REQUEST_CODE_GET );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( resultCode == Constants.RESULT_CODE_EDIT )
        {
            Toast.makeText(this, "Contact updated.", Toast.LENGTH_SHORT).show();
        }
        else if( resultCode == Constants.RESULT_CODE_DELETE )
        {
            Toast.makeText(this, "Contact deleted.", Toast.LENGTH_SHORT).show();
        }

        fetchAllUsers();
    }
}

