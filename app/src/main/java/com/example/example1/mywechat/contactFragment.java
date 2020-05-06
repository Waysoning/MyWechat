package com.example.example1.mywechat;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;

import com.example.example1.mywechat.friends.Friend;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class contactFragment extends Fragment {

    private List<Friend> friendList = new ArrayList<>();


    public contactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab03, container, false);
//        RecyclerView recyclerView = view.findViewById(R.id.contact_view);
//        initContacts();
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(manager);
//        friendAdapter adapter = new friendAdapter(friendList);
//        recyclerView.setAdapter(adapter);
        return view;
    }

//    private void initContacts(){
//        Friend friend1 = new Friend("wsh","1434131","dknae@qq.com");
//        friendList.add(friend1);
//        Friend friend2 = new Friend("dshfiu","1434131","dknae@qq.com");
//        friendList.add(friend2);
//    }
}
