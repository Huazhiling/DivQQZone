package com.example.administrator.qqzone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyPullList mainlist;
    private View headView;
    private ImageView iv;
    private ImageView leftIv;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mainlist = (MyPullList) findViewById(R.id.main_list);
        headView = View.inflate(this, R.layout.listview_head, null);
        iv = (ImageView) headView.findViewById(R.id.head_img);
        leftIv = (ImageView) headView.findViewById(R.id.head_leftimg);
        //消除阴影
        mainlist.setOverScrollMode(View.OVER_SCROLL_NEVER);
        initData();
        mainlist.setImageBig(iv);

    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add("嗯哼嗯哼蹦擦擦~" + (i + 1));
        }
        initAdapter();
    }

    private void initAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        mainlist.addHeaderView(headView);
        mainlist.setAdapter(adapter);
    }
}
