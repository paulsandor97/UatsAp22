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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private List<UserDB> contacts;

    public UsersAdapter(List<UserDB> contacts){

        this.contacts = contacts;
    }

    private UsersAdapter.OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(UsersAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.user_row, parent, false);

        // Return a new holder instance
        UsersAdapter.ViewHolder viewHolder = new UsersAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        UserDB contact = contacts.get(position);

        // Set item views based on your views and data model
        TextView userName = viewHolder.userName;
        userName.setText(contact.getPhoneNumber());
        Button delete = viewHolder.delete;
        delete.setText("Delete");
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public Button delete;
        public TextView userName;

        public ViewHolder(final View itemView){

            super(itemView);

            delete = (Button) itemView.findViewById(R.id.delete);
            userName = (TextView) itemView.findViewById(R.id.user);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast toast = Toast.makeText(itemView.getContext(), userName.getText().toString() + " WAS CLICKED", Toast.LENGTH_LONG);
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
