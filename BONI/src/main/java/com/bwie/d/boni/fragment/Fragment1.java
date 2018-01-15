package com.bwie.d.boni.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bwie.d.boni.R;
import com.bwie.d.boni.adapter.MineAdapter;
import com.bwie.d.boni.util.Model;
import com.bwie.d.boni.util.ScanMusic;

import java.util.List;

/**
 * Created by d on 2017/12/28.
 */

public class Fragment1 extends Fragment{


    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment1, container, false);         //切换至我的音乐Fragment
        listView = (ListView) view.findViewById(R.id.fragment1_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ScanMusic scanMusic = new ScanMusic();
        final List<Model> lists = scanMusic.query(getActivity());
        Log.i("---本地---",lists.size()+"");
        for (int i=0;i<lists.size();i++) {
            Log.i("---地址---",lists.get(i).getPath());
        }

        MineAdapter mineAdapter = new MineAdapter(lists,getActivity());

        if (lists.size()!=0) {
            listView.setAdapter(mineAdapter);
        }
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().postSticky(new Titlebean(lists.get(position).getText_song(),lists.get(position).getText_singer(),null));
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("model",lists.get(position));
                startActivity(intent);

            }
        });*/

    }
}
