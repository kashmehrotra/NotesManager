package com.example.kashishmehrotra.notes;

/**
 * Created by Kashish Mehrotra on 02-May-15.
 */
public class NotesManager {

    private long _id;
    private String _title;
    private String _content;
    private int _priority;

    public NotesManager() {
    }

    public NotesManager(String title, String content, int priority) {
        this._title = title;
        this._content = content;
        this._priority = priority;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void set_content(String _content) {
        this._content = _content;
    }

    public void set_priority(int _priority) {
        this._priority = _priority;
    }

    public long get_id() {
        return _id;
    }

    public String get_title() {
        return _title;
    }

    public String get_content() {
        return _content;
    }

    public int get_priority() {
        return _priority;
    }
}
