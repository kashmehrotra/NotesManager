package com.example.kashishmehrotra.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by Kashish Mehrotra on 03-May-15.
 */

class CustomAdapter extends ArrayAdapter<User>{

    CustomAdapter(Context context, ArrayList<User> users) {
        super(context, R.layout.custom_row, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        User user = getItem(position);
        ImageView dragIcon = (ImageView) customView.findViewById(R.id.dragIcon);
        EditText titleText = (EditText) customView.findViewById(R.id.titleText);
        EditText contentText = (EditText) customView.findViewById(R.id.contentText);
        RadioGroup radiogroup = (RadioGroup) customView.findViewById(R.id.radiogroup);
        LinearLayout buttonlayout = (LinearLayout) customView.findViewById(R.id.buttonlayout);
        buttonlayout.setVisibility(View.GONE);
        dragIcon.setImageResource(R.drawable.menu);
        radiogroup.clearCheck();
        titleText.setText(user.title);
        contentText.setText(user.content);
        int p = user.priority;
        if(p==0){
            radiogroup.check(R.id.trivial);
        }
        else if(p==1){
            radiogroup.check(R.id.important);
        }
        else if(p==2){
            radiogroup.check(R.id.critical);
        }
        return customView;
    }

}
