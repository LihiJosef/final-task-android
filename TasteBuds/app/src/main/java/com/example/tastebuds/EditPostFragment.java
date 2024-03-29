package com.example.tastebuds;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.tastebuds.databinding.FragmentEditPostBinding;
import com.example.tastebuds.model.PostModel;
import com.example.tastebuds.model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class EditPostFragment extends Fragment {
    FragmentEditPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isImageSelected = false;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    Post post;

    public static EditPostFragment newInstance(String editTitle){
        EditPostFragment frag = new EditPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", editTitle);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            this.post = bundle.getParcelable("post");
        }


        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.editPostFragment);
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
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // set views
        post = EditPostFragmentArgs.fromBundle(getArguments()).getPost();
        binding.locationEt.setText(post.getLocation());
        binding.ratingBar.setRating((float)post.getStars());
        binding.reviewEt.setText(post.getReview());

        if (post.getImageUrl().trim().length() != 0) {
            Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.ic_baseline_image_24).into(binding.postImage);
        } else {
            binding.postImage.setImageResource(R.drawable.ic_baseline_image_24);
        }


        binding.saveBtn.setOnClickListener(view1 -> {
            String id = post.getId();
            String userName = post.getUserName();
            String location = binding.locationEt.getText().toString();
            String imageUrl = post.getImageUrl();
            Integer stars = (int)binding.ratingBar.getRating();
            String review = binding.reviewEt.getText().toString();

            Post post = new Post(id, userName, imageUrl ,location, stars, review);
            String validationResult = post.validationMessage();
            if(validationResult.trim() != "") {
                binding.validationEt.setText(validationResult);
            } else {
                if (isImageSelected) {
                    binding.postImage.setDrawingCacheEnabled(true);
                    binding.postImage.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.postImage.getDrawable()).getBitmap();
                    PostModel.instance().uploadPostImage(id, bitmap, url -> {
                        if (url != null) {
                            post.setImageUrl(url);
                        }

                        PostModel.instance().editPost(post, (unused) -> {
                            Navigation.findNavController(view1).popBackStack();
                        });
                    });
                } else {
                    PostModel.instance().addPost(post, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                }
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
