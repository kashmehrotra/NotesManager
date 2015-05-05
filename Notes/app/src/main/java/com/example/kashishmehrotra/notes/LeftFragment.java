package com.example.kashishmehrotra.notes;

/**
 * Created by Kashish Mehrotra on 02-May-15.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LeftFragment extends Fragment {

    public static TextView noNotesText;
    public static ListView list;
    public static ArrayList<User> notelist;
    public static CustomAdapter adapter;
    public static MyDBHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        noNotesText = (TextView) view.findViewById(R.id.noNotesText);
        list = (ListView) view.findViewById(R.id.list);
        handler = new MyDBHandler(getActivity(), null, null, 1);
        return view;
    }

    void loadinfo() {

        notelist = handler.fetchRows();
        if (notelist.size() != 0)
            noNotesText.setText("");
        else
            noNotesText.setText("No Notes To Display");
        RightFragment.setlist();
        adapter = new CustomAdapter(getActivity(), notelist);
        list.setAdapter(adapter);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final User user = (User) parent.getItemAtPosition(position);
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RightFragment.setlist();
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showAlert(user);
                        RightFragment.setlist();
                    }
                });
                builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemClicked(user);
                        printDatabase();
                        RightFragment.setlist();
                    }
                });
                builder.show();
                printDatabase();
                RightFragment.setlist();
                return true;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadinfo();
        RightFragment.setlist();
    }

    public static void printDatabase() {
        notelist = handler.fetchRows();
        if (notelist.size() != 0)
            noNotesText.setText("");
        else
            noNotesText.setText("No Notes To Display");
        RightFragment.setlist();
        adapter = new CustomAdapter(MainActivity.mContext, notelist);
        list.setAdapter(adapter);

    }

    public void showAlert(final User user) {
        handler.deleteRow(user);
        printDatabase();
        RightFragment.setlist();
    }

    public void itemClicked(final User user) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_row);
        dialog.setTitle("Add Note");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
        Button okButton = (Button) dialog.findViewById(R.id.ok);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancel);
        final EditText titleText = (EditText) dialog.findViewById(R.id.titleText);
        final EditText contentText = (EditText) dialog.findViewById(R.id.contentText);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogroup);
        titleText.setText(user.title);
        contentText.setText(user.content);
        radioGroup.check(user.priority);
        okButton.setText("OK");
        cancelButton.setText("Cancel");
        RightFragment.setlist();
        dialog.show();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesManager manager = new NotesManager();
                manager.set_title(titleText.getText().toString());
                manager.set_content(contentText.getText().toString());
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.trivial:
                        manager.set_priority(0);
                        break;
                    case R.id.important:
                        manager.set_priority(1);
                        break;
                    case R.id.critical:
                        manager.set_priority(2);
                        break;
                    default:
                        manager.set_priority(3);
                }
                if(handler.checkDuplicate(manager)==0) {
                    handler.deleteRow(user);
                    handler.addRow(manager);
                }
                else
                    Toast.makeText(getActivity(), "Duplicate Note Not Allowed!", Toast.LENGTH_LONG).show();
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