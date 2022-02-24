package com.example.myblogapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myblogapp.model.ModelClass;
import com.example.myblogapp.utils.DateUtils;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    Context context;
    int singleData;
    List<ModelClass> modelArrayList;
    SQLiteDatabase sqLiteDatabase;
    public MyAdapter(Context context,List<ModelClass> modelArrayList){
        this.modelArrayList = modelArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_structure, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ModelClass article_data = modelArrayList.get(position);
        byte[] image = article_data.getArticle_Picture();
        //
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//        holder.tv_date.setText(article_data.getArticle_Date()+"");
        holder.tv_delete.setText("delete");
        holder.tv_date.setText(DateUtils.getStringDate(article_data.getArticle_Date()));
        holder.tv_description.setText(article_data.getArticle_Description() );
        holder.art_image.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_delete, tv_date, tv_description;
        ImageView art_image;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_delete = itemView.findViewById(R.id.article_structure_delete);
            tv_date = itemView.findViewById(R.id.article_structure_date);
            tv_description = itemView.findViewById(R.id.article_structure_des);
            art_image = itemView.findViewById(R.id.article_structure_picture);
        }
    }
}
