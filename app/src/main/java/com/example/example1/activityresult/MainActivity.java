package com.example.example1.activityresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity.this,
                        Main2Activity.class);
                intent1.putExtra("classname","android design");
                intent1.putExtra("grade",100);

                startActivityForResult(intent1,401);
            }
        });

        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(MainActivity.this,
                        Main2Activity.class);
                intent3.putExtra("classname","computer");
                intent3.putExtra("grade",80);

                startActivityForResult(intent3,402);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==401&&resultCode==500)
        {
            Log.i("activitycallback","onActivityResult is running.");
            Toast.makeText(this,data.getStringExtra("result"),
                    Toast.LENGTH_LONG).show();
        }

        if (requestCode==402&&resultCode==500)
        {
            Log.i("activitycallback","onActivityResult is running.");
            Toast.makeText(this,data.getStringExtra("result"),
                    Toast.LENGTH_LONG).show();
        }
    }
}
