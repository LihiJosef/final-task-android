package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    /*Localdb implement*/
    private Executor executor = Executors.newSingleThreadExecutor();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

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

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    };

    final public MutableLiveData<LoadingState> EventPostsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Post>> postsList;
    public LiveData<List<Post>> getAllPosts(){
        if(postsList == null) {
            postsList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;
    }

    public void refreshAllPosts(){
        EventPostsListLoadingState.setValue(LoadingState.LOADING);

        // 1. get local last update
        Long  localLastUpdate = Post.getLocalLastUpdate();

        // 2. get all updated records from firebase since last local update
        fireBaseModel.getAllPostsSince(localLastUpdate, list-> {
            executor.execute(()-> {
                Log.d("TAG", "FIREBASE RETURN " + list.size());
                Long time = localLastUpdate;
                for (Post post: list) {
                    // 3. insert new records into ROOM
                    localDb.postDao().insertAll(post);
                    if(time < post.getLastUpdated()) {
                        time = post.getLastUpdated();
                    }
                }

                // For testing loading - not necessary
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 4. update local last update
                Post.setLocalLastUpdate(time);
                EventPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });





        /*Localdb implement*/
        /*
        executor.execute(()->{
            List<Student> data = localDb.studentDao().getAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainHandler.post(()->{
                callback.onComplete(data);
            });
        });
         */
    }

//    public void getAllPosts(Listener<List<Post>> callback){
//        Log.d("Post", "get all posts");
//        /*Firebase implement*/
//        fireBaseModel.getAllPosts(callback);
//    }

    public void getAllUserPosts(Listener<List<Post>> callback){
        Log.d("Post", "get all user posts");
        callback.onComplete(this.userPosts);
    }

    public User getUser(){
        return this.user;
    }

    public void addPost(Post post,  Listener<Void> listener) {
        fireBaseModel.addPost(post, (Void)-> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void addUserPost(Post post) {
        userPosts.add(post);
    }

    public void uploadImage(String folderName, String fileName, Bitmap bitmap, Listener<String> listener) {
        fireBaseModel.uploadImage(folderName, fileName, bitmap, listener);
    }
}


















