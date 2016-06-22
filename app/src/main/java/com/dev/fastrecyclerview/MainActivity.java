package com.dev.fastrecyclerview;

import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dev.fastrecyclerview.adapter.AdapterRecord;
import com.dev.fastrecyclerview.interfaces.ClickListener;
import com.dev.fastrecyclerview.models.RecordModel;
import com.pluscubed.recyclerfastscroll.RecyclerFastScroller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {
    private RecyclerView mRecyclerView;
    private AdapterRecord mAdapter;
    private List<RecordModel> mListRecords;
    private Toolbar toolbar;
    private RecyclerFastScroller mFastScroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListRecords = new ArrayList<>();
        // Fake data
        for (int i = 0; i < 100; i++) {
            RecordModel contact = new RecordModel();
            if(i%2==0){
                contact.setPhoneNumber("0976252503");
            }else{
                contact.setPhoneNumber("096821350"+i);
            }
            contact.setId(i);
            contact.setNote("Note for contact "+ i);
            contact.setDuration(10);
            contact.setDate(10000212);

            mListRecords.add(contact);
        }


        // Filter All Record
        for (int i=0;i<mListRecords.size();i++) {
            mListRecords.set(i,filterRecords(mListRecords.get(i)));
        }
        mAdapter = new AdapterRecord(mListRecords,this,this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
       mFastScroller = (RecyclerFastScroller) findViewById(R.id.fast_scroll);
        mFastScroller.attachRecyclerView(mRecyclerView);

    }
    public void getContact(){
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME
                + " COLLATE LOCALIZED ASC";
        Cursor cursor= getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, sortOrder);

        if(cursor!=null){
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                RecordModel contact = new RecordModel();

                Bitmap bitmap = openPhoto(Long.parseLong(id));
                contact.setAvatar(bitmap);
                if(name.equals(""))  contact.setPhoneNumber(number);
                contact.setPhoneNumber(name);

                mListRecords.add(contact);
            }
        }
        //  mAdapterContact.notifyDataSetChanged();
        cursor.close();
    }


    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
      //  Toast.makeText(MainActivity.this,mAdapter.getSelectedItemCount()+"",Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle(mAdapter.getSelectedItemCount()+"");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onItemClicked(int position) {
        toggleSelection(position);
    }

    @Override
    public boolean onItemLongClicked(int position) {
        toggleSelection(position);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(mAdapter.getSelectedItemCount()>0)
        resetSelected();
        else
            super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:{
              resetSelected();
                break;
            }
            case R.id.add:{
                RecordModel record = new RecordModel();
                record.setPhoneNumber("Add new");
                record.setNote("This is new row record");
                mAdapter.addRow(record);
                Toast.makeText(this,"Đã thêm một record mới",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.delete:{
               List<Integer> listItemSelected = mAdapter.getSelectedItems();
                if(listItemSelected!=null && listItemSelected.size()>0){
                    for (int i = listItemSelected.size()-1; i>=0; i--) {
                        mAdapter.deleteRow(listItemSelected.get(i));
                    }
                }

                resetSelected();
                break;
            }
            case R.id.share:{
                List<Integer> listItemSelected = mAdapter.getSelectedItems();
                Toast.makeText(this,"Chia sẻ record",Toast.LENGTH_SHORT).show();
                if(listItemSelected!=null && listItemSelected.size()>0){

                }

break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetSelected(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.app_name);
        mAdapter.clearSelection();
    }


    private RecordModel filterRecords(RecordModel record) {
        String contact = record.getPhoneNumber();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode( record.getPhoneNumber()));
        Cursor cs = getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME,ContactsContract.PhoneLookup._ID}, ContactsContract.PhoneLookup.NUMBER + "='" + record.getPhoneNumber() + "'", null, null);
        if (cs.getCount() > 0) {
            cs.moveToFirst();
            contact = cs.getString(cs.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            record.setPhoneNumber(contact);
            Bitmap bitmap ;
            Long id = cs.getLong(cs.getColumnIndex(ContactsContract.PhoneLookup._ID));
            bitmap = openPhoto(id);
            record.setAvatar(bitmap);
        }

        return record;
    }
    public Bitmap openPhoto(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        } finally {
            cursor.close();
        }
        return null;

    }
}
