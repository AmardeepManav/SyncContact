package com.contdemo.contdemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

//    public interface OnItemClickListener {
//        //void onItemClick(ContactItem item);
//        void onItemLongClick (ContactItem item);
//    }

    private List<ContactItem> items;
  //  private final OnItemClickListener listener;
    private Context context;

    public ContactAdapter(List<ContactItem> items, Context context) {
        this.items = items;
        this.context = context;
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
       // viewHolder.bind(items.get(position), listener);

        viewHolder.contactName.setText(items.get(position).getContactName());
        viewHolder.contactNumber.setText(items.get(position).getContactNumber());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ContactItem contactItem = items.get(position);
                Toast.makeText(context, contactItem.getContactName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView contactNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactNumber = itemView.findViewById(R.id.contact_number);

        }

//        public void bind(final ContactItem item, final onItemLongClick listener) {
//            contactName.setText(item.getContactName());
//            contactNumber.setText(item.getContactNumber());
//          //  Picasso.with(itemView.getContext()).load(item.imageUrl).into(image);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    listener.onItemClick(item);
//                }
//            });
//        }
    }
}
