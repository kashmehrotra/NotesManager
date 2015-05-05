package com.example.kashishmehrotra.notes;

/**
 * Created by Kashish Mehrotra on 02-May-15.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RightFragment extends Fragment{

    public static ListView triviallist, implist, criticallist;
    public static ArrayList<String> trivialList, importantList, criticalList;
    public static MyDBHandler handler;
    public static ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        triviallist = (ListView)view.findViewById(R.id.triviallist);
        implist = (ListView)view.findViewById(R.id.implist);
        criticallist = (ListView)view.findViewById(R.id.criticallist);
        handler = new MyDBHandler(getActivity(), null, null, 1);
        trivialList = new ArrayList<String>();
        importantList = new ArrayList<String>();
        criticalList = new ArrayList<String>();
        return view;
    }

    public static void setlist(){
        trivialList = handler.fetchRow(0);
        importantList = handler.fetchRow(1);
        criticalList = handler.fetchRow(2);
        adapter = new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, trivialList);
        triviallist.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, importantList);
        implist.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(MainActivity.mContext, android.R.layout.simple_list_item_1, criticalList);
        criticallist.setAdapter(adapter);
    }

}
