package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Class created for the adapter.
// It is responsible for taking the data an putting it into the view holder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    public interface OnLongClickListener {
        void onItemLongClick(int position);
    }

    // Class attribute for the items of the list
    List<String> items;
    OnLongClickListener longClickListener;

    // Constructor for the class
    public ItemsAdapter(List<String> items, OnLongClickListener LongClickListener) {
        this.items = items;
        this.longClickListener = LongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view and not create a View manually
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        // Wrap it inside a ViewHolder and return it
        // (Create a new ViewHolder object that will be displayed)
        return new ViewHolder(todoView);
    }

    // Responsible to binding data to a particular ViewHolder
    // Updates a ViewHolder item by using it's bind method to update the value
    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        // Grab item at a specific position
        // (Get the string at position X of the list)
        String item = items.get(position);

        // Bind the item into the specific ViewHolder
        // (Set item's value at position X to string retrieved in last step)
        holder.bind(item);
    }

    // Returns the length of the list
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    // Container that provides easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside of the ViewHolder
        public void bind(String item) {
            this.tvItem.setText(item);

            // Set a Listener to whenever the ViewHolder (item) is Long Clicked
            this.tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long pressed
                    longClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
