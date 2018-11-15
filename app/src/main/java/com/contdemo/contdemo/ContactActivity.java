package com.contdemo.contdemo;

import android.content.Intent;
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
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_LIST = "extra_number_list";

    private RecyclerView contactRecyclerView;
    private List<ContactItem> contactItemList;
    private ContactAdapter adapter;
    private List<String> addedNumber;
    private int countNumber;
    private MenuItem okItem;
    private MenuItem countItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactRecyclerView = findViewById(R.id.contact_recycler);
        getAllContact();

        addedNumber = new ArrayList<>();
        adapter = new ContactAdapter(contactItemList, getApplicationContext(), new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int item, View itemView) {
                if (addedNumber.contains(contactItemList.get(item).getContactNumber())) {
                    countNumber = countNumber - 1;
                    showOkMenuItem();
                    countItem.setTitle(String.valueOf(countNumber));
                    addedNumber.remove(contactItemList.get(item).getContactNumber());
                } else if (!addedNumber.contains(contactItemList.get(item).getContactNumber())) {
                    countNumber = countNumber + 1;
                    showOkMenuItem();
                    countItem.setTitle(String.valueOf(countNumber));
                    addedNumber.add(contactItemList.get(item).getContactNumber());
                }
            }
            @Override
            public void onItemLongClick(int item, View itemView) {
                if (!addedNumber.contains(contactItemList.get(item).getContactNumber())) {
                    countNumber = countNumber + 1;
                    showOkMenuItem();
                    countItem.setTitle(String.valueOf(countNumber));
                    addedNumber.add(contactItemList.get(item).getContactNumber());
                    itemView.findViewById(R.id.contact_add_tick).setVisibility(View.VISIBLE);
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        contactRecyclerView.setLayoutManager(mLayoutManager);
        contactRecyclerView.setItemAnimator(new DefaultItemAnimator());
        contactRecyclerView.setAdapter(adapter);
    }

    private void getAllContact() {
        Cursor phones = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        contactItemList = new ArrayList<>();
        if (phones.getCount() > 0) {
            while (phones.moveToNext()) {
                String id = phones.getString(phones.getColumnIndex(ContactsContract.Contacts._ID));
                String name = phones.getString(phones.getColumnIndex(ContactsContract
                        .Contacts.DISPLAY_NAME));
                if (Integer.parseInt(phones.getString(phones.getColumnIndex(ContactsContract
                        .Contacts.HAS_PHONE_NUMBER))) == 1) {
                    System.out.println(name);
                    // get the phone number
                    Cursor pCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone
                            .CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone
                            .CONTACT_ID + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println(phone);
                        if(!list.contains(phone)) {
                            ContactItem item = new ContactItem(name, phone);
                            contactItemList.add(item);
                        }
                        list.add(phone);
                    }
                    pCur.close();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_phone_number_menu, menu);
        countItem = menu.findItem(R.id.count_contact);
        okItem = menu.findItem(R.id.add_all_number);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_all_number) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(EXTRA_NUMBER_LIST, (ArrayList<String>) addedNumber);
            setResult(MainActivity.CONTACT_REQUEST_CODE, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOkMenuItem() {
        if (countNumber >0) {
            okItem.setVisible(true);
        } else {
            okItem.setVisible(false);
        }
    }
}
