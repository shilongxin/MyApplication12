package com.bwie.d.boni.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.d.boni.LieBiaoActivity;
import com.bwie.d.boni.R;
import com.bwie.d.boni.adapter.BiaoAdapter;

/**
 * Created by d on 2017/12/28.
 */

public class Fragment2 extends Fragment{

    private RecyclerView recyclerView;
    private TextView frag_text;
    private LinearLayoutManager manager;
    private BiaoAdapter biaoAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.frag2_recycle);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        //http://tingapi.ting.baidu.com/v1/restserver/ting?fromat=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=1&size=10&offset=0
        //http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&calback=&from=webapp_music&method=baidu.ting.billboard.billList&type=1&size=10&offset=0
        biaoAdapter = new BiaoAdapter(getActivity());

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(biaoAdapter);

        biaoAdapter.setClickListener(new BiaoAdapter.onClickListener() {
            @Override
            public void click(View view, int position) {
                //Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LieBiaoActivity.class);
                intent.putExtra("position",position);
                getActivity().startActivity(intent);
            }
        });
    }
}
