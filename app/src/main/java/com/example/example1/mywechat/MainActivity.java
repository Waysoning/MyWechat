package com.example.example1.mywechat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.example1.mywechat.crud.MyDBHelper;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {

    private Fragment mTab01 = new playerFragment();
    private Fragment mTab02 = new frdFragment();
    private Fragment mTab03 = new contactFragment();
    private Fragment mTab04 = new settingsFragment();

    private FragmentManager fm;

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSettings;

    private ImageButton mImgWeixin;
    private ImageButton mImgFrd;
    private ImageButton mImgAddress;
    private ImageButton mImgSettings;

    //数据库相关变量定义
    public static StringBuilder result = new StringBuilder("程序所做的数据库操作为：\n");
    SQLiteDatabase db;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        initEvent();
        checkPermission();
        selectFragment(0);
    }

    private void initFragment(){
        fm = getFragmentManager();
        FragmentTransaction transaction =fm.beginTransaction();
        transaction.add(R.id.id_content,mTab01);
        transaction.add(R.id.id_content,mTab02);
        transaction.add(R.id.id_content,mTab03);
        transaction.add(R.id.id_content,mTab04);
        transaction.commit();
    }

    private void initView(){
        mTabWeixin = (LinearLayout)findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout)findViewById(R.id.id_tab_frd);
        mTabAddress=(LinearLayout)findViewById(R.id.id_tab_contact);
        mTabSettings=(LinearLayout)findViewById(R.id.id_tab_settings);

        mImgWeixin=(ImageButton)findViewById(R.id.id_tab_weixin_img);
        mImgFrd=(ImageButton)findViewById(R.id.id_tab_frd_img);
        mImgAddress=(ImageButton)findViewById(R.id.id_tab_contact_img);
        mImgSettings=(ImageButton)findViewById(R.id.id_tab_settings_img);
    }

    private void initEvent(){
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSettings.setOnClickListener(this);
    }

    private void selectFragment(int i){
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        //把图片设置为亮的
        //设置内容区域
        switch (i){
            case 0:
                Log.d("setSelect","1");
                transaction.show(mTab01);
                mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                Log.d("setSelect","2");
                transaction.show(mTab02);
                mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            case 2:
                Log.d("setSelect","3");
                transaction.show(mTab03);
                mImgAddress.setImageResource(R.drawable.tab_address_pressed);
                break;
            case 3:
                Log.d("setSelect","4");
                transaction.show(mTab04);
                mImgSettings.setImageResource(R.drawable.tab_settings_pressed);
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction){
        transaction.hide(mTab01);
        transaction.hide(mTab02);
        transaction.hide(mTab03);
        transaction.hide(mTab04);
    }

    @Override
    public void onClick(View view) {
        Log.d("onClick","1");
        resetImages();
        switch (view.getId()){
            case R.id.id_tab_weixin:
                selectFragment(0);
                break;
            case R.id.id_tab_frd:
                selectFragment(1);
                break;
            case R.id.id_tab_contact:
                selectFragment(2);
                break;
            case R.id.id_tab_settings:
                selectFragment(3);
                break;
            default:
                break;
        }
    }

    public void resetImages(){
        mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
        mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
        mImgAddress.setImageResource(R.drawable.tab_address_normal);
        mImgSettings.setImageResource(R.drawable.tab_settings_normal);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission(){
        int hasWriteContactsPermisson = checkSelfPermission(
                android.Manifest.permission.READ_CONTACTS);
        if(hasWriteContactsPermisson != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]
                            {Manifest.permission.WRITE_CONTACTS},
                    1);

            return;
        }
    }

}
