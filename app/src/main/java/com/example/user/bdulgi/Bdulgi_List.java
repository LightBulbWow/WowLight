package com.example.user.bdulgi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by user on 2016-07-21.
 */
public class Bdulgi_List extends AppCompatActivity {
    //edit_add
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thelist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("Expense Management");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        ImageButton I = (ImageButton) findViewById(R.id.edit_add);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        getApplicationContext(),
                        Bdulgi_List_edit.class);
                startActivity(intent);
            }

        });


    }
}
