package com.example.user.bdulgi;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bdulgi.Commnetwork.commnetwork;
import com.example.user.bdulgi.Commnetwork.onNetworkResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by user on 2016-07-21.
 */
public class Bdulgi_List_edit extends AppCompatActivity implements onNetworkResponseListener {

    int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listedit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(" Expense Application");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.When_save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Bdulgi_List_edit.this, dateSetListener, year, month, day).show();
            }
        });
        getAccountList(null);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
            TextView date = (TextView)findViewById(R.id.When_save);
            date.setText(msg);
        }
    };

    public void getAccountList (View view){

        JSONObject req_data = new JSONObject();
        try {
            req_data.put("USER_ID", "test_user1");


            commnetwork commNetwork = new commnetwork(this, this);
            commNetwork.requestToServer("ACCOUNT_L001", req_data);
        } catch (Exception e) {
            ErrorUtil.AlertException(this, "오류다", e);

        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
    @Override
    public void onSuccess(String api_key, JSONObject response) throws JSONException {
        Toast.makeText(this, "요청 성공",Toast.LENGTH_SHORT).show();
        JSONArray array = response.getJSONArray("REC");

        try {
            AccountTitleSpinnerList spinnerList = new AccountTitleSpinnerList(array);



        ArrayAdapter<String> adapter = new ArrayAdapter<>(Bdulgi_List_edit.this, android.R.layout.simple_spinner_item, spinnerList.getArrayList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.account_save);
        spinner.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String api_key, String error_cd, String error_msg) {
        Toast.makeText(this, "요청 실패",Toast.LENGTH_SHORT).show();
    }

}
