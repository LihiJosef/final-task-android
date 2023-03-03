package com.example.tastebuds;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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

import com.example.tastebuds.databinding.FragmentEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


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

        // צריך את הבלוק הזה? todo :
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.editProfileFragment);
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

        EditText displaynameEt = view.findViewById(R.id.editprofile_displayname_et);
        ImageView avatarImage = view.findViewById(R.id.editprofile_avatar_img);

        displaynameEt.setText(user.getDisplayName());

        if (user.getPhotoUrl() != null) {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.avatar).into(avatarImage);
        } else {
            avatarImage.setImageResource(R.drawable.avatar);
        }

        // todo : save edited profile
//        binding.saveBtn.setOnClickListener(view1 -> {
//            String nickName = binding.nameEt.getText().toString();
//            P st = new Student(stId,name,"",false);
//
//            if (isAvatarSelected){
//                binding.editprofileAvatarImg.setDrawingCacheEnabled(true);
//                binding.editprofileAvatarImg.buildDrawingCache();
//                Bitmap bitmap = ((BitmapDrawable) binding.editprofileAvatarImg.getDrawable()).getBitmap();
//                Model.instance().uploadImage(stId, bitmap, url->{
//                    if (url != null){
//                        st.setAvatarUrl(url);
//                    }
//                    Model.instance().addStudent(st, (unused) -> {
//                        Navigation.findNavController(view1).popBackStack();
//                    });
//                });
//            }else {
//                Model.instance().addStudent(st, (unused) -> {
//                    Navigation.findNavController(view1).popBackStack();
//                });
//            }
//        });

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