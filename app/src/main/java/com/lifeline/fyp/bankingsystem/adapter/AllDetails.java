package com.lifeline.fyp.bankingsystem.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifeline.fyp.bankingsystem.activities.AllTransactions;
import com.lifeline.fyp.bankingsystem.activities.DetailedActivity;
import com.lifeline.fyp.bankingsystem.database.Database;
import com.lifeline.fyp.bankingsystem.models.Member;
import com.lifeline.fyp.bankingsystem.R;

import java.util.ArrayList;

/**
 * Created by Mujtaba_khalid on 5/24/2018.
 */

public class AllDetails extends RecyclerView.Adapter<AllDetails.MemberDetail> {

    public ArrayList<Member> members;
    private Context mContext;
    Database database;

    public AllDetails(ArrayList<Member> members, Context context) {
        this.members = members;
        this.mContext = context;
        database = new Database( context );
    }

    @Override
    public MemberDetail onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.memberdetails, parent, false );
        return new MemberDetail( view );
    }

    @Override
    public void onBindViewHolder(MemberDetail holder, final int position) {

        final Member member;
        member = members.get( position );

        holder.name.setText( member.get_username() );

        if(member.getStatus().equals( "No Balance" )){
            holder.number.setText( "null" );
        }
        else if(member.getStatus().equals( "borrowed" )){
            holder.number.setText( "- "+ member.getAmount() );
        }
        else if(member.getStatus().equals( "lend" )){
                holder.number.setText( "+ "+ member.getAmount() );
        }

      //  holder.number.setText( member.get_cellnumber() );
        holder.status.setText( member.getStatus() );
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                members.remove( position );
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,members.size());
                deletingUser(member.get_id());

            }
        } );

        holder.allrecord.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             viewFulldetail(member.get_id());
            }
        } );

    }

    private void deletingUser(Integer id){
        database.deleteUserAccount(id);


    }

    private void viewFulldetail( Integer id){

        Intent intent = new Intent( mContext,AllTransactions.class);
        intent.putExtra( "id",id );
        mContext.startActivity(intent);
        ((Activity)mContext).finish();




    }
    @Override
    public int getItemCount() {
        return members == null ? 0 : members.size();
    }

    public class MemberDetail extends RecyclerView.ViewHolder {
        TextView name, number, status;
        android.support.v7.widget.AppCompatButton delete;
        RelativeLayout allrecord;

        public MemberDetail(View itemView) {
            super( itemView );

            name = (TextView) itemView.findViewById( R.id.name );
            number = (TextView) itemView.findViewById( R.id.number );
            status = (TextView) itemView.findViewById( R.id.status );
            delete = (android.support.v7.widget.AppCompatButton) itemView.findViewById( R.id.delete );
            allrecord = (RelativeLayout) itemView.findViewById( R.id.allrecord );
        }
    }
}
