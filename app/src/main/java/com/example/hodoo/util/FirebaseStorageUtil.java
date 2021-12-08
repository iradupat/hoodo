package com.example.hodoo.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.hodoo.controller.PostInterface;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

public class FirebaseStorageUtil {

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public FirebaseStorageUtil(){

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("img/");
    }

    public void uploadImage(ImageCallback callback, Uri uri, Context context){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("Uploading...");
        pd.show();
        long today = new Date().getTime();

        String imgUrl = null;
        StorageReference ref = storageRef.child("dog/"+today);
        ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onImageUploaded(uri.toString());
                        pd.dismiss();
                        Toast.makeText(context, "Post created",Toast.LENGTH_LONG).show();

                    }});


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        });

    }
}
