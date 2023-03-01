package com.example.tastebuds;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tastebuds.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    Boolean isAvatarSelected = false;
    Button registerButton;
    ImageButton galleryButton, cameraButton, backButton;
    ImageView imageView;

    EditText passwordET, displayNameET, emailET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.register_btn_register);
        galleryButton = findViewById(R.id.galleryButton);
        cameraButton = findViewById(R.id.cameraButton);
        imageView = findViewById(R.id.avatarImg);

        displayNameET = findViewById(R.id.displayname_edit_text);
        emailET = findViewById(R.id.email_edit_text);
        passwordET = findViewById(R.id.password_edittext);

        ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imageView.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryButton.setOnClickListener(view -> {
            galleryLauncher.launch("image/*");
        });

        cameraButton.setOnClickListener(view -> {
            cameraLauncher.launch(null);
        });

        registerButton.setOnClickListener(view -> {
            String displayName = displayNameET.getText().toString();
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();

            if (email != null && email.length() > 0 && password != null && password.length() > 0) {

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("AUTH", "Inserted user");

//                                // User registration successful, update user profile
//                                FirebaseUser user = mAuth.getCurrentUser();
//
//                                // Set display name
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(displayName)
//                                        .build();
//                                user.updateProfile(profileUpdates);
//
//                                // Set email
//                                user.updateEmail(email);
//
//                                // Set profile image URL
//                                if (isAvatarSelected) {
//
//                                    String id = UUID.randomUUID().toString();
//                                    String FOLDER_NAME = "profileImages";
//
//                                    imageView.setDrawingCacheEnabled(true);
//                                    imageView.buildDrawingCache();
//                                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//                                    Model.instance().uploadImage(FOLDER_NAME, id, bitmap, url -> {
//                                        if (url != null) {
//                                            Uri photoUri = Uri.parse(url);
//                                            UserProfileChangeRequest profilePictureUpdates = new UserProfileChangeRequest.Builder()
//                                                    .setPhotoUri(photoUri)
//                                                    .build();
//                                            user.updateProfile(profilePictureUpdates);
//                                        }
//                                    });
//
//                                }

                                // Go to main app
                                Intent intent = new Intent(this,MainActivity.class);
                                startActivity(intent);
                            } else {
                                Log.d("AUTH", "failed - Inserted user");

//                                Toast.makeText(Register.this, "Authentication failed", Toast.LENGTH_SHORT.show())
                            }
                        });

            }
        });

    }
}