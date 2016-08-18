package com.example.user.bdulgi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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

    public EditText using;
    public EditText pricing;
    public EditText dating;
    public EditText briefing;
    private Spinner spinner;
    AccountTitleSpinnerList spinnerList;
    int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        day = calendar.get(Calendar.DAY_OF_MONTH);

        findViewById(R.id.When_save).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Bdulgi_List_edit.this, dateSetListener, year, month, day).show();
            }
        });
        getAccountList(null);
    }




        public void toolbarRightButtonClick(View v) {
            try{
                using = (EditText) findViewById(R.id.use_text);
                pricing = (EditText) findViewById(R.id.price_save);
                dating = (EditText) findViewById(R.id.When_save);
                briefing = (EditText) findViewById(R.id.brief_save);
                spinner = (Spinner)findViewById(R.id.account_save);
                if( TextUtils.isEmpty(using.getText())||
                        TextUtils.isEmpty(pricing.getText())||
                        TextUtils.isEmpty(dating.getText())||
                        TextUtils.isEmpty(briefing.getText()))
                {
                    Toast.makeText(this , "Please 다시봐", Toast.LENGTH_SHORT).show();
                    return;
                }



                JSONObject requestObject = new JSONObject();
                requestObject.put("PAYMENT_STORE_NM", using.getText().toString());
                requestObject.put("PAYMENT_AMT", pricing.getText().toString());
                requestObject.put("PAYMENT_DTTM", dating.getTag().toString());
                requestObject.put("SUMMARY", briefing.getText().toString());
                requestObject.put("ACCOUNT_TTL_CD", spinnerList.getAccountTitleCd(spinner.getSelectedItemPosition()));
                requestObject.put("USER_ID", "test_user1");

                Log.d("dilky", requestObject.toString());

                commnetwork network = new commnetwork(this, this);
                network.requestToServer("EXPENSE_I001",requestObject);


            }

             catch (Exception e) {
                ErrorUtil.AlertException(this, "오류다", e);
            }
        }



    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String msg = String.format("%d년%d월%d일", year,monthOfYear+1, dayOfMonth);
            TextView date = (TextView)findViewById(R.id.When_save);
            date.setText(msg);
            date.setTag(String.format("%04d%02d%02d", year, monthOfYear + 1, dayOfMonth));
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
        try{
        if("ACCOUNT_L001".equals(api_key)) {

            Toast.makeText(this, "요청 성공", Toast.LENGTH_SHORT).show();

            JSONArray array = response.getJSONArray("REC");
            spinnerList = new AccountTitleSpinnerList(array);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Bdulgi_List_edit.this, android.R.layout.simple_spinner_item, spinnerList.getArrayList());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner spinner = (Spinner) findViewById(R.id.account_save);
            spinner.setAdapter(adapter);
        }else if("EXPENSE_I001".equals(api_key))
            {
                Toast.makeText(this, "보내기 성공", Toast.LENGTH_SHORT).show();
                if(!TextUtils.isEmpty(response.getString("EXPENSE_SEQ"))){
                    //Intent intent = new Intent(Bdulgi_List_edit.this, Bdulgi_List.class);
                    //intent.putExtra("EXPENSE_SEQ", response.getString("EXPENSE_SEQ"));
                    //startActivity(intent);

                    Intent Go = new Intent(Bdulgi_List_edit.this, ViewExpense.class);
                    Go.putExtra("EXPENSE_SEQ", response.getString("EXPENSE_SEQ"));
                    startActivity(Go);
                    finish();

                }
            }



        }
        catch (Exception e) {
            ErrorUtil.AlertException(this, "오류입니다", e);

        }
    }

    @Override
    public void onFailure(String api_key, String error_cd, String error_msg) {
        if("ACCOUNT_L001".equals(api_key))
            Toast.makeText(this, "요청 실패",Toast.LENGTH_SHORT).show();
        else if("EXPENSE_I001".equals(api_key))
        {

        }
    }

}
