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

public class ShowUserActivity extends AppCompatActivity
{

    private ArrayList<UserBO> users;
    private ListView usersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("User");
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
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_USER_NAME,
                DatabaseHelper.COLUMN_USER_EMAIL,
                DatabaseHelper.COLUMN_USER_PASSWORD};

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_USER_NAME + " ASC"
        );

        users = new ArrayList<>();
        while(cursor.moveToNext() )
        {
            UserBO userBO = new UserBO();
            userBO.setId(cursor.getLong(0) );
            userBO.setName( cursor.getString(1) );
            userBO.setEmail( cursor.getString(2) );
            userBO.setPassword( cursor.getString(3));
            users.add( userBO );
            //String a=String.valueOf(userBO.getId());
            //Toast.makeText(this, a, Toast.LENGTH_SHORT).show();


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
        //Toast.makeText(this, userBO.getID(), Toast.LENGTH_SHORT).show();
        Intent i = new Intent( this, ManageUserActivity.class );
        i.putExtra( Constants.KEY_USER_ID, userBo.getId() );

        startActivityForResult( i, Constants.REQUEST_CODE_GET );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( resultCode == Constants.RESULT_CODE_EDIT )
        {
            //Toast.makeText(this, "updated.", Toast.LENGTH_SHORT).show();
        }
        else if( resultCode == Constants.RESULT_CODE_DELETE )
        {
            //Toast.makeText(this, "deleted.", Toast.LENGTH_SHORT).show();
        }

        fetchAllUsers();
    }
}
