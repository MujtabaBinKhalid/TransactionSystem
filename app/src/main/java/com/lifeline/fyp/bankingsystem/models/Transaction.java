package com.lifeline.fyp.bankingsystem.models;

/**
 * Created by Mujtaba_khalid on 5/26/2018.
 */

public class Transaction {

    private Integer _id;
    private String ownername;
    private String username;
    private String status;
    private String amount;
    private String description;
    private String date;

    public Transaction(String ownername, String username, String status, String amount, String description, String date) {
        this.ownername = ownername;
        this.username = username;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Integer get_id() {
        return _id;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwnername() {
        return ownername;
    }

    public String getUsername() {
        return username;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

