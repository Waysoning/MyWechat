package com.example.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;

import java.util.Calendar;

public class AddActivity extends Activity {

    private DatePickerDialog dateDialog;
    private int year, monthOfYear, dayOfMonth;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);
        TextView tv=(TextView)findViewById(R.id.Date);
        EditText et=(EditText)findViewById(R.id.edit_name);
        Button btn=(Button) findViewById(R.id.addevent);
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(calendar.YEAR);
        monthOfYear=calendar.get(calendar.MONTH);
        dayOfMonth=calendar.get(calendar.DAY_OF_MONTH);

        //完成时间日期弹出框
        dateDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String text=year+"/"+(monthOfYear+1)+"/"+dayOfMonth;
                tv.setText(text);
            }
        }, year, monthOfYear,dayOfMonth);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog.show();
            }
        });

        // 添加完信息返回主界面
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("date",tv.getText());
                intent.putExtra("event",et.getText().toString());
                setResult(0,intent);
                finish();
            }
        });



    }



}
