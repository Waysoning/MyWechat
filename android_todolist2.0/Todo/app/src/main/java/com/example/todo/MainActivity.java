package com.example.todo;
import androidx.appcompat.app.AppCompatActivity;

import java.text.CollationKey;
import java.text.Collator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Calendar;




public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    RecyclerView list;

    List<ToDolistinfo> todoList = new LinkedList();
    MainActivity.ListAdapater adapter;

    @BindView(R.id.btn_add)
    Button btnAdd;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager listLayoutMgr = new LinearLayoutManager(this);
        listLayoutMgr.setOrientation(RecyclerView.VERTICAL);
        this.list.setLayoutManager(listLayoutMgr);
        this.adapter = new MainActivity.ListAdapater(this.todoList);
        this.list.setAdapter(this.adapter);
        this.list.setItemAnimator(new DefaultItemAnimator());
        MainActivity.this.adapter.notifyDataSetChanged();
        mIntent=new Intent();
        mIntent.setClass(MainActivity.this,AddActivity.class);
        this.btnAdd.setOnClickListener(new OnClickListener() {          //点击添加进入添加页面
            public void onClick(View view) {
                startActivityForResult(mIntent,6666);
            }
        });
    }

    //对待办事项根据日期先后进行排序
    public void Sort(){
                Collections.sort(todoList, new Comparator<ToDolistinfo>() {
                    public int compare(ToDolistinfo t1, ToDolistinfo t2) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            if (sdf.parse(t1.getDate()).before(sdf.parse(t2.getDate())))
                            {
                                return -1;
                            }
                            else if (sdf.parse(t1.getDate()).after(sdf.parse(t2.getDate())))
                            {
                                return 1;
                            }
                            else{
                                return 0;
                            }
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                            return 0;
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 6666:                                                        //得到AddActivity 添加待办事项后返回的数据
                super.onActivityResult(requestCode, resultCode, data);
                String event = data.getExtras().getString("event");
                String date = data.getExtras().getString("date");
                ToDolistinfo info = new ToDolistinfo();
                info.setDate(date);
                info.setEvent(event);
                todoList.add(info);
                Sort();
                adapter.notifyDataSetChanged();
                break;
            case 8888:                                                            //得到ProcessActivity 编辑完成返回的数据
                super.onActivityResult(requestCode, resultCode, data);
                int rate=Integer.parseInt(data.getStringExtra("rate").toString());
                int pos=Integer.parseInt(data.getStringExtra("pos").toString());
                String alter_event = data.getExtras().getString("event");
                String alter_date = data.getExtras().getString("date");
                todoList.get(pos).setEvent(alter_event);
                todoList.get(pos).setDate(alter_date);
                todoList.get(pos).setProcess(rate);
                Sort();
                adapter.notifyDataSetChanged();
                break;
        }
    }


    //listview多件套
    private class ListViewHolder extends ViewHolder {
        View vv;
        TextView event;
        TextView date;
        Button  bt;
        ProgressBar pro;
        int pos;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            vv = itemView;
            event = (TextView)itemView.findViewById(R.id.txt_event);
            date  = (TextView)itemView.findViewById(R.id.txt_date);
            pro   = (ProgressBar) itemView.findViewById(R.id.process);
            bt    = (Button)itemView.findViewById(R.id.finish);
            pro.setProgress(todoList.get(pos).getProcess());
            bt.setOnClickListener(new View.OnClickListener(){           //完成按钮触发的事件
                public void onClick(View v){
                    String event=todoList.get(pos).getEvent();
                    todoList.remove(pos);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this,"你已经完成了事件"+event,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class ListAdapater extends RecyclerView.Adapter<ListViewHolder>
    {
        List<ToDolistinfo> todoInfos;
        private int Editcode=8888;
        public ListAdapater(List<ToDolistinfo> todoInfos) {
            this.todoInfos = todoInfos;
        }
        @NonNull
        public MainActivity.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.item_contactinfo, parent, false);
            MainActivity.ListViewHolder holder = MainActivity.this.new ListViewHolder(v);
            v.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){                                                //点击项目后进入编辑界面
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this,Processor.class);
                    intent.putExtra("position",String.valueOf(holder.getAdapterPosition()));
                    intent.putExtra("event1",holder.event.getText().toString());
                    intent.putExtra("date1",holder.date.getText().toString());
                    startActivityForResult(intent,Editcode);
                }
            });
            return holder;
        }

        public void onBindViewHolder(@NonNull MainActivity.ListViewHolder holder, int position) {
                if (MainActivity.this.todoList != null) {
                ToDolistinfo info = (ToDolistinfo) MainActivity.this.todoList.get(position);
                holder.event.setText(info.getEvent());
                holder.date.setText(info.getDate());
                holder.pro.setProgress(info.getProcess());
            }
        }
        public int getItemCount()
        {
            return this.todoInfos.size();
        }
    }

}
