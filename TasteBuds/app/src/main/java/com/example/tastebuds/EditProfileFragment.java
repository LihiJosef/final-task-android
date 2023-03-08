package com.example.tastebuds;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tastebuds.databinding.FragmentEditProfileBinding;
import com.example.tastebuds.model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.util.UUID;


public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    Boolean isAvatarSelected = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editprofileAvatarImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editprofileAvatarImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ImageView avatarImage = view.findViewById(R.id.editprofile_avatar_img);

        binding.editprofileDisplaynameEt.setText(user.getDisplayName());

        if (user.getPhotoUrl() != null) {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.avatar).into(avatarImage);
        } else {
            avatarImage.setImageResource(R.drawable.avatar);
        }

        binding.saveBtn.setOnClickListener(view1 -> {
            String nickName = binding.editprofileDisplaynameEt.getText().toString();

            // Set display name
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nickName)
                    .build();
            user.updateProfile(profileUpdates);

            // Set profile image URL
            if (isAvatarSelected) {

                String id = UUID.randomUUID().toString();
                String FOLDER_NAME = "profileImages";

                avatarImage.setDrawingCacheEnabled(true);
                avatarImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) avatarImage.getDrawable()).getBitmap();
                PostModel.instance().uploadImage(FOLDER_NAME, id, bitmap, url -> {
                    if (url != null) {
                        Uri photoUri = Uri.parse(url);
                        UserProfileChangeRequest profilePictureUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(photoUri)
                                .build();
                        user.updateProfile(profilePictureUpdates);
                    }
                });
            }

            Toast.makeText(getContext(), "Profile information changed successfully!", Toast.LENGTH_SHORT).show();
        });

        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.profileFragment, false));

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });
        return view;
    }

}