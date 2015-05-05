package com.example.kashishmehrotra.notes;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHandler handler;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        handler = new MyDBHandler(this, null, null, 1);
    }

    public void addButtonClicked(View v){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_row);
        dialog.setTitle("Add Note");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
        Button okButton = (Button)dialog.findViewById(R.id.ok);
        Button cancelButton = (Button)dialog.findViewById(R.id.cancel);
        final EditText titleText = (EditText)dialog.findViewById(R.id.titleText);
        final EditText contentText = (EditText)dialog.findViewById(R.id.contentText);
        final RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.radiogroup);
        okButton.setText("OK");
        cancelButton.setText("Cancel");
        dialog.show();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesManager manager = new NotesManager();
                manager.set_title(titleText.getText().toString());
                manager.set_content(contentText.getText().toString());
                switch(radioGroup.getCheckedRadioButtonId()){
                    case R.id.trivial: manager.set_priority(0);
                                        break;
                    case R.id.important: manager.set_priority(1);
                                        break;
                    case R.id.critical: manager.set_priority(2);
                                        break;
                    default: manager.set_priority(3);
                }
                if(handler.checkDuplicate(manager)==0)
                    handler.addRow(manager);
                else
                    Toast.makeText(mContext, "Duplicate Note Not Allowed!", Toast.LENGTH_LONG).show();
                LeftFragment.printDatabase();
                RightFragment.setlist();
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                RightFragment.setlist();
            }
        });
    }

}
