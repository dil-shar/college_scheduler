package com.shashank.platform.classroomappui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Announcements extends AppCompatActivity {
    DBHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_all);

         dbh = new DBHelper(this);
        ArrayList<Announcement> arrayList;
        arrayList = dbh.getAllAnnouncements();
        CustomAdapterForAnnouncements customAdapterForAnnouncements = new CustomAdapterForAnnouncements(arrayList,getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.linear_layout_for_announcement);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(customAdapterForAnnouncements);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
