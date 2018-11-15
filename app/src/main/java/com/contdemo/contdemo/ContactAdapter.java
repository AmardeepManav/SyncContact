package com.contdemo.contdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int item, View itemView);
        void onItemLongClick(int item, View itemView);
    }

    private final List<SelectableItem> mValues;
    private List<ContactItem> items;
    private final OnItemClickListener listener;
    private Context context;

    public ContactAdapter(List<ContactItem> items, Context context, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
        this.context = context;

        mValues = new ArrayList<>();
        for (ContactItem item : items) {
            mValues.add(new SelectableItem(item, false));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
      //  viewHolder.bind(items.get(position), listener);
        viewHolder.bind(position, listener);
        SelectableItem selectableItem = mValues.get(position);
        viewHolder.mItem = selectableItem;
        viewHolder.setChecked(viewHolder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactNumber;
        public ImageView contactAddTick;
        public RelativeLayout layout;

        private SelectableItem mItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);
            contactAddTick = itemView.findViewById(R.id.contact_add_tick);
            layout = itemView.findViewById(R.id.contact_item_layout);
        }

        public void bind(final int position, final OnItemClickListener listener) {
            contactName.setText(items.get(position).getContactName());
            contactNumber.setText(items.get(position).getContactNumber());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItem.isSelected()) {
                        setChecked(false);
                    } else {
                        setChecked(true);
                    }
                    listener.onItemClick(position, itemView);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(position, itemView);
                    return true;
                }
            });
        }

        public void setChecked(boolean value) {
            if (value) {
                layout.setBackgroundColor(Color.LTGRAY);
            } else {
                layout.setBackground(null);
            }
            mItem.setSelected(value);
        }
    }
}
