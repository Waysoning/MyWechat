package com.example.example1.activityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button button3=findViewById(R.id.button3);
        final TextView textView2=findViewById(R.id.textView2);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=getIntent();
                String classname=intent2.getStringExtra("classname");
                Integer grade=intent2.getIntExtra("grade",8);
                textView2.setText(classname+grade);

                intent2.putExtra("result",classname+"参数已接收。");
                setResult(500,intent2);

                Log.i("activitycallback","onActivityResult begins to run.");
                finish();
            }
        });
    }
}