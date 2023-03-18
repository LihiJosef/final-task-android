package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new
                FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
    }

    public void getAllPostsSince(Long since, PostModel.Listener<List<Post>> callback) {
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot postsListJson = task.getResult();
                            for (DocumentSnapshot postJson : postsListJson) {
                                Post post = Post.parseJson(postJson.getData());
                                list.add(post);
                            }
                        }
                        callback.onComplete(list);
                    }
                });
    }

    ;

    public void addPost(Post post, PostModel.Listener<Void> listener) {
        Log.d("Post", "postsFireBase: " + post.getUserName());
        // Add a new document with a generated ID
        db.collection(Post.COLLECTION).document(post.getId()).set(post.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void editPost(Post post, PostModel.Listener<Void> listener) {
        Log.d("Post", "edit post : " + post.getUserName());
        // Add a new document with a generated ID
        db.collection(Post.COLLECTION).document(post.getId()).update(post.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    void uploadImage(String folderName, String fileName, Bitmap bitmap, PostModel.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child(folderName + "/" + fileName + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });

    }
}
