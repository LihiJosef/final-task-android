package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*Singelton model*/
public class Model {
    private static final Model _instance = new Model();

    public static Model instance(){
        return _instance;
    }
    List<Post> data = new LinkedList<>();
    List<Post> userPosts = new LinkedList<>();


    private  Model(){
        for(int i=0; i<20; i++){
            addPost(new Post(""+i, "name " + i, "", "blala", 4, "sdsdad"));
        }

        for(int i=0; i<10; i++){
            addPost(new Post(""+i, "yossi", "", "blala", 4, "sdsdad"));
        }
    }

    public interface  Listener<T>{
        void onComplete(T data);
    }

    public void getAllPosts(Listener<List<Post>> callback){
        Log.d("Post", "check");
        callback.onComplete(this.data);
    }

    public void getAllUserPosts(Listener<List<Post>> callback){
        Log.d("Post", "check");
        callback.onComplete(this.data);
    }

    public void addPost(Post post) {
        data.add(post);
    }

    public void addUserPost(Post post) {
        data.add(post);
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


















