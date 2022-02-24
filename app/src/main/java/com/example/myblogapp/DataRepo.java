package com.example.myblogapp;

import android.content.Context;

import com.example.myblogapp.model.ModelClass;

import java.util.List;

public class DataRepo {

    public static List<ModelClass> getAllClassItem(Context context) {

        DbHelper db = new DbHelper(context);
        /*List<ContactModel> list = db.getEveryone();*/

        return db.getEveryone();
    }

//    public static boolean addContact(Context context, ContactModel contactModel){
//        DataBaseHelper db = new DataBaseHelper(context);
//        return db.addContact(contactModel);
//    }
}
