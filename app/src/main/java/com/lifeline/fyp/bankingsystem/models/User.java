package com.lifeline.fyp.bankingsystem.models;

/**
 * Created by Mujtaba_khalid on 5/23/2018.
 */

public class User {

    private int _id;
    private String _username;
    private String _password;
    private String _cellnumber;


    public User(String _username, String _password, String _cellnumber) {
        this._username = _username;
        this._password = _password;
        this._cellnumber = _cellnumber;
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }

    public String get_cellnumber() {
        return _cellnumber;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_cellnumber(String _cellnumber) {
        this._cellnumber = _cellnumber;
    }
}
