package com.example.tastebuds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tastebuds.databinding.FragmentProfileBinding;
import com.example.tastebuds.model.Post;
import com.example.tastebuds.model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    UserPostRecyclerAdapter adapter;
    UserPostListFragmentViewModel viewModel;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.profileFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        }, this, Lifecycle.State.RESUMED);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        ImageView postImage = view.findViewById(R.id.profile_avatar_img);

        binding.profileNicknameTv.setText(user.getDisplayName());
        binding.profileUsernameTv.setText(user.getEmail());

        if (user.getPhotoUrl() != null) {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.avatar).into(postImage);
        } else {
            postImage.setImageResource(R.drawable.avatar);
        }

        /*RecyclerView include:
         * 1. Layout Manager
         * 2. Adapter
         * 3. ViewHolder
         * 4. Row View*/
        binding.recyclerView.setHasFixedSize(true);

        // 1
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 2
        adapter = new UserPostRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        /*-> Handle row click in the activity*/
        adapter.setOnItemClickListener(new UserPostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Post post = viewModel.getData().getValue().get(pos);
                ProfileFragmentDirections.ActionProfileFragmentToEditPostFragment action = ProfileFragmentDirections.actionProfileFragmentToEditPostFragment(post);
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        ImageView logoutButton = view.findViewById(R.id.profile_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });


        ImageView editButton = view.findViewById(R.id.profile_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(action);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(UserPostListFragmentViewModel.class);
    }

    void reloadData() {
        PostModel.instance().refreshAllPosts();
    }
}