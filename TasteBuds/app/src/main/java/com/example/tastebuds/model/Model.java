package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.navigation.Navigation;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*Singelton model*/
public class Model {
    private static final Model _instance = new Model();
    /*Firebase implement*/
    private FirebaseModel fireBaseModel = new FirebaseModel();

    public static Model instance(){
        return _instance;
    }
    List<Post> userPosts = new LinkedList<>();
    User user;

    private  Model(){
//        for(int i=0; i<20; i++){
//            Post post = new Post(""+i, "name " + i, "", "blala", 4, "sdsdad");
//            addPost(post, (unused) -> {
//                Log.d("Post", "post has been added");
//            });
//        }


        for(int i=0; i<4; i++){
            addUserPost(new Post(""+i, "yossi", "", "blala", 4, "sdsdad"));
        }

        user = new User("yossiCohen13", "Yossi Cohen", "");
    }

    public interface  Listener<T>{
        void onComplete(T data);
    }

    public void getAllPosts(Listener<List<Post>> callback){
        Log.d("Post", "get all posts");
        /*Firebase implement*/
        fireBaseModel.getAllPosts(callback);
    }

    public void getAllUserPosts(Listener<List<Post>> callback){
        Log.d("Post", "get all user posts");
        callback.onComplete(this.userPosts);
    }

    public User getUser(){
        return this.user;
    }

    public void addPost(Post post,  Listener<Void> listener) {
        /*Firebase implement*/
        fireBaseModel.addPost(post, listener);
    }

    public void addUserPost(Post post) {
        userPosts.add(post);
    }

    public void uploadImage(String folderName, String fileName, Bitmap bitmap, Listener<String> listener) {
        fireBaseModel.uploadImage(folderName, fileName, bitmap, listener);
    }



//    public void getAllStudents(Listener<List<Post>> callback){
//        /*Firebase implement*/
//        fireBaseModel.getAllStudents(callback);
//
//        /*Localdb implement*/
//        /*
//        executor.execute(()->{
//            List<Student> data = localDb.studentDao().getAll();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainHandler.post(()->{
//                callback.onComplete(data);
//            });
//        });
//         */
//    }


//
//
//        public void addPost(Post ps, Listener<Void> listener){
//        /*Firebase implement*/
//        fireBaseModel.addStudent(ps, listener);
//
//        /*Localdb implement*/
//        /*
//        executor.execute(()->{
//            localDb.studentDao().insertAll(st);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainHandler.post(()->{
//                listener.onComplete();
//            });
//        });
//         */
//    }

//    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
//        fireBaseModel.uploadImage(name, bitmap, listener);
//    }
}


















