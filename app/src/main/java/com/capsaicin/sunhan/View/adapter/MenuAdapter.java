package com.capsaicin.sunhan.View.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capsaicin.sunhan.Model.MenuItem;
import com.capsaicin.sunhan.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    ArrayList<MenuItem> items = new ArrayList<MenuItem>();
    private Context context;

    public MenuAdapter(Context context, ArrayList<MenuItem> items){
        this.context = context ;
        this.items= items;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.menu_item, parent, false);
        return new MenuAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        MenuItem item =items.get(position);
        holder.menuName.setText(items.get(position).getMenuName());
        holder.menuPrice.setText(items.get(position).getMenuPrice());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView menuPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName = itemView.findViewById(R.id.menuName);
            menuPrice = itemView.findViewById(R.id.menuPrice);

            itemView.setClickable(true);
        }
    }

    public void addItem(MenuItem item){
        items.add(item);
    }
    public void setArrayList(ArrayList<MenuItem> arrayList) {

        this.items = arrayList;
    }

    public MenuItem getItem(int position) {

        return items.get(position);
    }

    public void setItem(int position, MenuItem item) {

        items.set(position, item);
    }

}
