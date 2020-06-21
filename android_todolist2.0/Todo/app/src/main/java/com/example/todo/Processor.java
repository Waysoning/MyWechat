package com.example.todo;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class Processor extends Activity {
    private int year, monthOfYear, dayOfMonth;
    private DatePickerDialog dateDialog;
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent come=getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processactivity);
        Button btn=(Button)findViewById(R.id.editpro);
        EditText et=(EditText)findViewById(R.id.edit_event);
        TextView tv=(TextView) findViewById(R.id.edit_date);
        String event=come.getExtras().getString("event1");
        String date=come.getExtras().getString("date1");
        et.setText(event);
        tv.setText(date);
        Calendar calendar=Calendar.getInstance();
        year=calendar.get(calendar.YEAR);
        monthOfYear=calendar.get(calendar.MONTH);
        dayOfMonth=calendar.get(calendar.DAY_OF_MONTH);

        //完成时间日期弹出框 同添加事项
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

        //更新信息后返回主界面
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r=come.getExtras().getString("position");
                TextView rate=(TextView) findViewById(R.id.rate);
                Intent intent=new Intent();
                intent.putExtra("rate",rate.getText().toString());
                intent.putExtra("date",tv.getText().toString());
                intent.putExtra("event",et.getText().toString());
                intent.putExtra("pos",r);
                setResult(3,intent);
                finish();
            }
        });

    }


}
