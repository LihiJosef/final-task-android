package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FireBaseModel {
    FirebaseFirestore db;
//    FirebaseStorage storage;

//    FirebaseModel () {
//        db = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new
//                FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        db.setFirestoreSettings(settings);
////        storage = FirebaseStorage.getInstance();
//    }

//    public void getAllPosts(Model.Listener<List<Post>> callback) {
//        db.collection("students").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                List<Post> list = new LinkedList<>();
//                if(task.isSuccessful()){
//                    QuerySnapshot studentsListJson = task.getResult();
//                    for (DocumentSnapshot studentJson: studentsListJson){
//                        Post st = Post.parseJson(studentJson.getData());
//                        list.add(st);
//                    }
//                }
//                callback.onComplete(list);
//            }
//        });
//    };
//
//    public void addStudent(Student st, Model.Listener<Void> listener){
//        Log.d("URL1", "studentfirebase: " + st.getAvatarUrl());
//        // Add a new document with a generated ID
//        db.collection("students").document(st.getId()).set(st.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                listener.onComplete(null);
//            }
//        });
//    }

//    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
//        /*https://firebase.google.com/docs/storage/android/upload-files*/
//        StorageReference storageRef = storage.getReference();
//        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = imagesRef.putBytes(data);
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                listener.onComplete(null);
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        listener.onComplete(uri.toString());
//                    }
//                });
//            }
//        });
//
//    }
}
