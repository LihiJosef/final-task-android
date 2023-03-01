package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    // todo: Replace with firebase user
    User user;

    private  Model(){
//        for(int i=0; i<20; i++){
//            Post post = new Post(""+i, "name " + i, "", "blala", 4, "sdsdad");
//            addPost(post, (unused) -> {
//                Log.d("Post", "post has been added");
//            });
//        }

        user = new User("sivan", "Yossi Cohen", "");
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

    public LiveData<List<Post>> getAllPosts(){
        if(postsList == null) {
            postsList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;
    }

    public LiveData<List<Post>> getAllUserPosts(){
        return localDb.postDao().getPostsByUser(this.user.getUserName());
    }

    public void addPost(Post post,  Listener<Void> listener) {
        fireBaseModel.addPost(post, (Void)-> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String folderName, String fileName, Bitmap bitmap, Listener<String> listener) {
        fireBaseModel.uploadImage(folderName, fileName, bitmap, listener);
    }

    public User getUser(){
        return this.user;
    }
}


















