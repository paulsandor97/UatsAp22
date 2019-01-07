package com.polos.uatsap22.model.recyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.polos.uatsap22.R;
import com.polos.uatsap22.database.MessageDB;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<MessageDB> messages;
    private UserDB contact;
    private UserDB me;

    public MessagesAdapter(List<MessageDB> messages, UserDB me, UserDB contact){

        this.messages = messages;
        this.contact = contact;
        this.me = me;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View messageView = inflater.inflate(R.layout.message_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(messageView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder viewHolder, int position) {

        MessageDB message = messages.get(position);

        // Set item views based on your views and data model

        TextView name = viewHolder.name;
        TextView messageText = viewHolder.message;
        messageText.setText((CharSequence) message.getMessage());

        if(message.getFromId() == me.getUserId()) {
            name.setText("YOU");
        }else{
            name.setText(contact.getPhoneNumber());
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView message;


        public ViewHolder(final View itemView){

            super(itemView);

            name = (TextView) itemView.findViewById(R.id.from);
            message = (TextView) itemView.findViewById(R.id.textMessage);
        }
    }

}
