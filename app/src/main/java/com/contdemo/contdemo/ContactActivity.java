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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_LIST = "extra_number_list";

    private RecyclerView contactRecyclerView;
    private List<ContactItem> contactItemList;
    private ContactAdapter adapter;
    private List<String> addedNumber;
    private int countNumber;
    private Menu menu;
    private MenuItem okItem;
    private MenuItem countItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contactRecyclerView = findViewById(R.id.contact_recycler);
        getAllContact();

        addedNumber = new ArrayList<>();

        //adapter = new ContactAdapter(contactItemList, listener, getApplicationContext());
        adapter = new ContactAdapter(contactItemList, getApplicationContext(), new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ContactItem item) {

                if (addedNumber.contains(item.getContactNumber())) {
                    countNumber = countNumber - 1;
                    showOkMenuItem();
                    countItem.setTitle(String.valueOf(countNumber));
                    addedNumber.remove(item.getContactNumber());
                    //Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
                }
//                int count = addedNumber.size();
//                for (int i = 0; i < count; i++){
//                    if (item.getContactNumber() == addedNumber.get(i)) {
//                        if (countNumber >=1 ) {
//                            countNumber = countNumber - 1;
//                            showOkMenuItem();
//                            countItem.setTitle(String.valueOf(countNumber));
//                            addedNumber.remove(item.getContactNumber());
//                            //Toast.makeText(getApplicationContext(),item.getContactNumber(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
            }

            @Override
            public void onItemLongClick(ContactItem item) {

//                int count = addedNumber.size();
//                for (int i = 0; i< count; i++){
//                    if (item.getContactNumber() != addedNumber.get(i)) {
//                        countNumber = countNumber + 1;
//                        showOkMenuItem();
//                        countItem.setTitle(String.valueOf(countNumber));
//                        addedNumber.add(item.getContactNumber());
//                        Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//                if (count == 0) {
//                    countNumber = countNumber + 1;
//                    showOkMenuItem();
//                    countItem.setTitle(String.valueOf(countNumber));
//                    addedNumber.add(item.getContactNumber());
//                    Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
//                }

                if (!addedNumber.contains(item.getContactNumber())) {
                    countNumber = countNumber + 1;
                    showOkMenuItem();
                    countItem.setTitle(String.valueOf(countNumber));
                    addedNumber.add(item.getContactNumber());
                    //Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
                }



//                if (item.getContactNumber().contains(addedNumber.get(addedNumber.size())))
//                countNumber = countNumber + 1;
//                showOkMenuItem();
//                countItem.setTitle(String.valueOf(countNumber));
//                addedNumber.add(item.getContactNumber());
//                Toast.makeText(getApplicationContext(),item.getContactName(), Toast.LENGTH_SHORT).show();
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
        this.menu = menu;

        countItem = menu.findItem(R.id.count_contact);
        okItem = menu.findItem(R.id.add_all_number);

//        MenuItem okTick = menu.findItem(R.id.add_all_number);
//
//        if (countNumber > 0) {
//            okTick.setVisible(true);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_all_number) {
            addedNumber.size();
           //Toast.makeText(getApplicationContext(), "" +addedNumber.size(), Toast.LENGTH_SHORT).show();

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
