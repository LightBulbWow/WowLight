package com.example.user.bdulgi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bdulgi extends AppCompatActivity {
    //date_name
    public EditText editText;
    public EditText password;
    private Context CONTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdulgi);


        editText = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);


        Button b = (Button) findViewById(R.id.Login_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (!TextUtils.isEmpty(editText.getText())
                        && !TextUtils.isEmpty(password.getText())) {
                    Intent intent = new Intent(getApplicationContext(), Bdulgi_List.class);
                    startActivity(intent);
                }

                if (TextUtils.isEmpty(editText.getText())) {
                    Toast.makeText(Bdulgi.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getText())) {
                    Toast.makeText(Bdulgi.this, "Password를 입력하세요", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bdulgi, menu);
        return true;
    }

}


