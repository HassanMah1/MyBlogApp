package com.example.myblogapp;

import static com.example.myblogapp.DbHelper.COLUMN_ARTICLE_DATE;
import static com.example.myblogapp.DbHelper.COLUMN_ARTICLE_DESCRIPTION;
import static com.example.myblogapp.DbHelper.COLUMN_ARTICLE_PICTURE;
import static com.example.myblogapp.DbHelper.COLUMN_ARTICLE_TITLE;
import static com.example.myblogapp.DbHelper.COLUMN_BLOGGER_NAME;
import static com.example.myblogapp.DbHelper.TABLENAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myblogapp.model.ModelClass;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class Insert_Article_Activity extends AppCompatActivity {
    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Button savebtn;
    EditText etTitle, etDescription, etBloggerName;
    ImageView articleImage;
    ModelClass modelClass;
    int id = 0;
    Context context;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String[] cameraPremission;
    String[] storagePermission;
    private boolean imageIsSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_article);

        dbHelper = new DbHelper(this);


        init();
        permissionInit();
        // insertion
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String bloggerName = etBloggerName.getText().toString();
                String articleDes = etDescription.getText().toString();
                String articleTitle = etTitle.getText().toString();


                if (!imageIsSelected || bloggerName.isEmpty() || articleDes.isEmpty() || articleTitle.isEmpty()) {
                    Toast.makeText(Insert_Article_Activity.this, " No Image Selected or some fields missing ", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(Insert_Article_Activity.this, "No image found", Toast.LENGTH_SHORT).show();

                    modelClass = new ModelClass();
                    modelClass.setBlogger_name(etBloggerName.getText().toString());
                    modelClass.setArticle_Description(etDescription.getText().toString());
                    modelClass.setArticle_Title(etTitle.getText().toString());
                    ContentValues cv = new ContentValues();

                    //cv.put(COLUMN_PERSON_NAME, modelClass.getName());
                    cv.put(COLUMN_BLOGGER_NAME, modelClass.getBlogger_name());
                    cv.put(COLUMN_ARTICLE_DESCRIPTION, modelClass.getArticle_Description());
                    cv.put(COLUMN_ARTICLE_TITLE, modelClass.getArticle_Title());
                    cv.put(COLUMN_ARTICLE_DATE, modelClass.getArticle_Date());
                    cv.put(COLUMN_ARTICLE_PICTURE, ImageViewToByte(articleImage));
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    long insert = sqLiteDatabase.insert(TABLENAME, null, cv);

                    if (insert == -1) {
                        Toast.makeText(Insert_Article_Activity.this, "record  not inserted", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(Insert_Article_Activity.this, "record  inserted", Toast.LENGTH_SHORT).show();
                        finish();
//                    avatar.setImageResource(R.mipmap.ic_launcher);
//                    name.setText("");
                    }
                }


            }
        });
        //image
        //avatar is imageview name
        articleImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int avatar = 0;
                if (avatar == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (avatar == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });

    }

    private void permissionInit() {
        cameraPremission = new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }


    //picture related functions start

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPremission, CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    private byte[] ImageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public void init() {
        etBloggerName = findViewById(R.id.edittext_blogger_name);
        articleImage = findViewById(R.id.imageview_article_image);
        etTitle = findViewById(R.id.edittext_title);
        etDescription = findViewById(R.id.edittext_description);
        savebtn = findViewById(R.id.save_button);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accept && storage_accept) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean storage_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storage_accept) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(articleImage);
                imageIsSelected = true;
            }
        }
    }

    // picture related functions end

}