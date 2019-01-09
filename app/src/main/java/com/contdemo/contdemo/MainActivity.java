package com.contdemo.contdemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACT_PERMISSION_CODE = 201;
    public static final int CONTACT_REQUEST_CODE = 321;
    private Button getContactBtn;
    private TextView mainContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkIfAlreadyhavePermission()) {
            requestStoragePermission();
        }
        getContactBtn = findViewById(R.id.get_contact_btn);
        mainContact = findViewById(R.id.main_contact);
        getContactBtn.setOnClickListener(onClickListener);
        showDilaog();
    }

    private void contactPermission () {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                CONTACT_PERMISSION_CODE);
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "granted", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "not granted", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},
                CONTACT_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == CONTACT_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.get_contact_btn) {
                //startActivity(new Intent(getApplicationContext(), ContactActivity.class));

                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivityForResult(intent, CONTACT_REQUEST_CODE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "something wrong...", Toast.LENGTH_SHORT).show();
        } else if (requestCode == CONTACT_REQUEST_CODE) {
            List<String> list = new ArrayList<>();
            StringBuffer buffer = new StringBuffer();

            list = data.getStringArrayListExtra(ContactActivity.EXTRA_NUMBER_LIST);
            Toast.makeText(getApplicationContext(), "OK..."+ list.size(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i< list.size(); i++) {
                buffer.append(list.get(i));
            }
           // if()
            mainContact.setText(buffer.toString());
        }
    }

    private void showDilaog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
        dialog.setTitle("");
        dialog.setMessage("please add the cntact Permission");
        dialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
        .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
         dialog.create();
    }
}
