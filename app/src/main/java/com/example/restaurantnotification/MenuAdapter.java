package com.example.restaurantnotification;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Item> productsList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public TextView name,price;

        public MyViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.rowproductphoto);
            name = (TextView) view.findViewById(R.id.rowproductname);
            price = (TextView) view.findViewById(R.id.rowproductprice);
        }
    }


    public MenuAdapter(List<Item> productsList,Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item c = productsList.get(position);

        if(c.getIdPhoto()!= 0){     //un calciatore da modificare potrebbe anche non avere la photo

            Picasso.with(context).load(c.getIdPhoto()).into(holder.photo);
            //holder.photo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.food));
        }else{
            holder.photo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.food));
        }

        holder.name.setText(c.getName());
        holder.price.setText(c.getPrice());
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    /*Metodo utilizzato da CalciatoriActivity per aggiornare la lista dell'adapter (in pratica
    per cancellare tutti gli elementi) */
    public void setList(List l){
        productsList=l;
    }



}