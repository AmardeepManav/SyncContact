package com.contdemo.contdemo;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private RecyclerView contactRecyclerView;
    private List<ContactItem> contactItemList;
    private ContactAdapter adapter;
    private List<String> addedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactRecyclerView = findViewById(R.id.contact_recycler);
        getAllContact();

        //adapter = new ContactAdapter(contactItemList, listener, getApplicationContext());
        adapter = new ContactAdapter(contactItemList, getApplicationContext(), new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ContactItem item) {
                Toast.makeText(getApplicationContext(),item.getContactNumber(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(ContactItem item) {
                Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        contactRecyclerView.setLayoutManager(mLayoutManager);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactRecyclerView.setAdapter(adapter);


    }

    private void getAllContact() {
        contactItemList = new ArrayList<>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactItem item = new ContactItem(name, phoneNumber);
            contactItemList.add(item);
        }
        phones.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_phone_number_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.add_all_number);
        if (true) {
            menuItem.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_all_number) {
           Toast.makeText(getApplicationContext(), "Check", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
