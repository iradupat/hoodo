package com.example.hodoo.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.IntCallback;
import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.Post;
import com.example.hodoo.model.PostStatus;
import com.example.hodoo.model.User;
import com.example.hodoo.util.FirebaseStorageUtil;
import com.example.hodoo.util.ImageCallback;
import com.example.hodoo.util.PostBuilder;

import java.io.ByteArrayOutputStream;

public class CreateEditPostActivity extends AppCompatActivity {
   private TextView saveBtn, descriptionText;
   private  ImageView dogImage;
   private Uri imageUri = null;

   private  ActivityResultLauncher<Intent> someActivityResultLauncher;
   private boolean isEdit;
   private User user;
   private Post post;
   private RoomDB db;
   private PostInterface postController;
   private StoreUserInterface roomUserController;
   private int chosenStatus;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_create_post_layout);

        // initialize data
        postController = FactoryController.createPostController("FIREBASE_DB");
        db = RoomDB.getInstance(this);
        roomUserController = FactoryController.createStoreUserController("ROOM_DB");

        user = roomUserController.getCredentials(db);


        // load extras from intent
        isEdit = (boolean) getIntent().getExtras().get("isEdit");
        chosenStatus = (int)getIntent().getExtras().get("action");

        saveBtn = findViewById(R.id.edit_create_save_btn);
        dogImage = findViewById(R.id.dogImage);
        descriptionText = findViewById(R.id.edit_create_save_description);

//        someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == CreateEditPostActivity.RESULT_OK) {
//                            // There are no request codes
//                            Intent data = result.getData();
////                            doSomeOperations();
//                            dogImage.setImageURI(imageUri);
//                        }
//                    }
//                });




        dogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String []  permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 10);
                    }else{
                        useCamera();
                    }
                }else{
                    useCamera();
                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();

            }
        });
    }

    private void useCamera(){
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, "PIC THE DOGOO");
//        values.put(MediaStore.Images.Media.DESCRIPTION, "PIC THE DOGOO");
//        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        imageIntent.putExtra(MediaStore., imageIntent);
        startActivityForResult(imageIntent, 20);


//        someActivityResultLauncher.launch(imageIntent);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    useCamera();
                }else{
                     Toast.makeText(CreateEditPostActivity.this,"Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            }

        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20){
            if(data!=null) {
                Bitmap img = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                img.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), img, "dog", null);
                imageUri = Uri.parse(path);
                System.out.println(imageUri+" The image is here");
                dogImage.setImageBitmap(img);
            }else{
                Toast.makeText(CreateEditPostActivity.this,"Could you please pic the DOGOO for us!",Toast.LENGTH_LONG).show();
            }
        }
    }



    public void createPost(){

        if(imageUri==null){
            Toast.makeText(this,"Take a picture of the dog first!", Toast.LENGTH_LONG).show();
            return;
        }

        if(chosenStatus == 0){

            postController.getPostCount(new IntCallback() {
                @Override
                public void onComplete(int value) {
                    post = new PostBuilder(imageUri.toString(), PostStatus.SEEN, user)
                            .addPostId(10).addDescription(descriptionText.getText().toString()).buildPost();
                    new FirebaseStorageUtil().uploadImage(new ImageCallback() {
                        @Override
                        public void onImageUploaded(String url) {
                            post.setImage(url);
                            postController.addPost(post);
                        }
                    },imageUri, CreateEditPostActivity.this);
//                    Intent postDetailIntent = new Intent(CreateEditPostActivity.this, PostDetailActivity.class);
//                    postDetailIntent.putExtra("postId",post.getPostId());
//                    startActivity(postDetailIntent);

                }
            });

        }else if(chosenStatus == 1){

        }else if(chosenStatus == 2){

        }
    }
}
