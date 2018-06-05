package com.lifeline.fyp.bankingsystem.models;

/**
 * Created by Mujtaba_khalid on 5/23/2018.
 */

public class Member {

    private int _id;

    private String _owner;
    private String _username;
    private String _cellnumber;
    private String _cnic;

    private String status;
    private String amount;
    private String _description;
   private String date;


    public Member(int _id, String _username, String _cellnumber, String _cnic, String status,String _description,String amount,String date) {
        this._id = _id;
        this._username = _username;
        this._cellnumber = _cellnumber;
        this._cnic = _cnic;
        this.status = status;
        this._description = _description;
        this.amount = amount;
        this.date = date;
    }

    public Member(String _owner, String _username,
                  String _cellnumber, String _cnic, String status,
                  String amount, String _description, String date) {
        this._owner = _owner;
        this._username = _username;
        this._cellnumber = _cellnumber;
        this._cnic = _cnic;
        this.status = status;
        this.amount = amount;
        this._description = _description;
        this.date = date;
    }

    public Member(String _owner) {
        this._owner = _owner;
    }

    public int get_id() {
        return _id;
    }

    public String get_owner() {
        return _owner;
    }

    public String get_username() {
        return _username;
    }

    public String get_cellnumber() {
        return _cellnumber;
    }

    public String get_cnic() {
        return _cnic;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String get_description() {
        return _description;
    }

    public String getDate() {
        return date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_owner(String _owner) {
        this._owner = _owner;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_cellnumber(String _cellnumber) {
        this._cellnumber = _cellnumber;
    }

    public void set_cnic(String _cnic) {
        this._cnic = _cnic;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
