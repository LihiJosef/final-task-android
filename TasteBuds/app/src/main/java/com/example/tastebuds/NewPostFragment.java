package com.example.tastebuds;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastebuds.databinding.FragmentNewPostBinding;
import com.example.tastebuds.model.Model;
import com.example.tastebuds.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;

public class NewPostFragment extends Fragment {
    FragmentNewPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isImageSelected = false;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.newPostFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.postImage.setImageBitmap(result);
                    isImageSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.postImage.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.saveBtn.setOnClickListener(view1 -> {
            String location = binding.locationEt.getText().toString();
            // TODO: change xml to get stars as number
            Integer stars = Integer.parseInt(binding.starsEt.getText().toString());
            String review = binding.reviewEt.getText().toString();

            String id = UUID.randomUUID().toString();
            String userName = user.getEmail();
            String FOLDER_NAME = "postsImages";

            Post post = new Post(id, userName, "" ,location, stars, review);;

            if(isImageSelected) {
                binding.postImage.setDrawingCacheEnabled(true);
                binding.postImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.postImage.getDrawable()).getBitmap();
                Model.instance().uploadImage(FOLDER_NAME, id, bitmap, url -> {
                    if (url != null) {
                        post.setImageUrl(url);
                    }

                    Model.instance().addPost(post, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            } else {
                Model.instance().addPost(post, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack());

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });


        return view;
    }
}