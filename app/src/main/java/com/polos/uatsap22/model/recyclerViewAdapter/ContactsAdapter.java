package com.polos.uatsap22.model.recyclerViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.polos.uatsap22.R;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private List<UserDB> contacts;

    public ContactsAdapter(List<UserDB> contacts){

        this.contacts = contacts;
    }

    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.contact_row, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        UserDB contact = contacts.get(position);

        // Set item views based on your views and data model
        TextView phoneNumber = viewHolder.contactName;
        phoneNumber.setText(contact.getPhoneNumber());
        Button messageButton = viewHolder.goToMessages;
        messageButton.setText("Message");
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Button goToMessages;
        public TextView contactName;

        public ViewHolder(final View itemView){

            super(itemView);

            goToMessages = (Button) itemView.findViewById(R.id.message);
            contactName = (TextView) itemView.findViewById(R.id.contact);

            goToMessages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(itemView.getContext(), contactName.getText().toString() + " WAS CLICKED", Toast.LENGTH_LONG);
                    //toast.show();
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }

                }
            });
        }
    }

}
