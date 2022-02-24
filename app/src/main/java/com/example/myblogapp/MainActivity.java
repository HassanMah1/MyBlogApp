package com.example.myblogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myblogapp.model.ModelClass;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    RecyclerView recyclerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        context = this;
     //   iv = findViewById(R.id.iv_add);
        recyclerView = findViewById(R.id.recycler1);
        // btnCalls = findViewById(R.id.btn_calls);
    }
    //options menu  start
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<ModelClass> articles_Models = DataRepo.getAllClassItem(context);
        myAdapter = new MyAdapter(this,articles_Models);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_activity:
                Intent i = new Intent(this,Insert_Article_Activity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //options menu end

}