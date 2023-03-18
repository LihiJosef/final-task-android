package com.example.tastebuds.model;

import android.graphics.Bitmap;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*Singelton model*/
public class PostModel {
    private static final PostModel _instance = new PostModel();
    /*Firebase implement*/
    private FirebaseModel fireBaseModel = new FirebaseModel();
    /*Localdb implement*/
    private Executor executor = Executors.newSingleThreadExecutor();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static PostModel instance() {
        return _instance;
    }

    private PostModel() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventPostsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Post>> postsList;

    public void refreshAllPosts() {
        EventPostsListLoadingState.setValue(LoadingState.LOADING);

        // 1. get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();

        // 2. get all updated records from firebase since last local update
        fireBaseModel.getAllPostsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", "FIREBASE RETURN " + list.size());
                Long time = localLastUpdate;
                for (Post post : list) {
                    // 3. insert new records into ROOM
                    localDb.postDao().insertAll(post);
                    if (time < post.getLastUpdated()) {
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
    }

    public LiveData<List<Post>> getAllPosts() {
        if (postsList == null) {
            postsList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postsList;
    }

    public LiveData<List<Post>> getAllUserPosts() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        return localDb.postDao().getPostsByUser(user.getEmail());
    }

    public void addPost(Post post, Listener<Void> listener) {
        fireBaseModel.addPost(post, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void editPost(Post post, Listener<Void> listener) {
        fireBaseModel.editPost(post, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void uploadPostImage(String fileName, Bitmap bitmap, Listener<String> listener) {
        fireBaseModel.uploadImage(Post.IMAGE_FOLDER_NAME, fileName, bitmap, listener);
    }

    public void uploadImage(String folderName, String fileName, Bitmap bitmap, Listener<String> listener) {
        fireBaseModel.uploadImage(folderName, fileName, bitmap, listener);
    }
}


















