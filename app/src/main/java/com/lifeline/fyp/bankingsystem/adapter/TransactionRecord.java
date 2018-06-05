package com.lifeline.fyp.bankingsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lifeline.fyp.bankingsystem.R;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.models.Transaction;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 5/26/2018.
 */

public class TransactionRecord extends RecyclerView.Adapter<TransactionRecord.TransactionDetail> {

    public ArrayList<Transaction> transactions;
    private Context mContext;
    Database database;

    public TransactionRecord(ArrayList<Transaction> transactions, Context mContext) {
        this.transactions = transactions;
        this.mContext = mContext;
    }

    @Override
    public TransactionDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.alltrans, parent, false );
        return new TransactionDetail( view );
    }

    @Override
    public void onBindViewHolder(TransactionDetail holder, int position) {
        final Transaction transaction;
        transaction = transactions.get( position );

        if (transaction.getStatus().equals( "borrowed" )){
            holder.des.setText( "You borrow money from "+ transaction.getUsername()  );
            holder.amount.setText( transaction.getDescription() );
            holder.date.setText( transaction.getDate() );

        }
        else if(transaction.getStatus().equals( "lend" )){
            holder.des.setText(  "You  lend money to "+ transaction.getUsername()  );
            holder.amount.setText( transaction.getDescription() );
            holder.date.setText( transaction.getDate() );
        }

    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }

    public class TransactionDetail extends RecyclerView.ViewHolder {

        TextView des,amount,date;
        public TransactionDetail(View itemView) {
            super( itemView );
            des = (TextView) itemView.findViewById( R.id.des );
            amount = (TextView) itemView.findViewById( R.id.amount );
            date = (TextView) itemView.findViewById( R.id.date );

        }
    }
}
