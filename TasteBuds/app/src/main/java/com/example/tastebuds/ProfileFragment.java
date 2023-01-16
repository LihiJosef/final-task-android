package com.example.tastebuds;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tastebuds.databinding.FragmentProfileBinding;
import com.example.tastebuds.model.Model;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    UserPostRecyclerAdapter adapter;
    UserPostListFragmentViewModel viewModel;

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

        /*RecyclerView include:
         * 1. Layout Manager
         * 2. Adapter
         * 3. ViewHolder
         * 4. Row View*/
        binding.recyclerView.setHasFixedSize(true);

        // 1
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 2
        adapter = new UserPostRecyclerAdapter(getLayoutInflater(), viewModel.getData());
        binding.recyclerView.setAdapter(adapter);

//        /*-> Handle row click in the activity*/
//        adapter.setOnItemClickListener(new UserPostRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                Log.d("TAG", "row click handle in activity " + pos);
//                Post post = viewModel.getData().get(pos);
//                UserPostListFragmentDirections.ActionStudentListFragmentToBlueFragment action =  UserPostListFragmentDirections.actionStudentListFragmentToBlueFragment(post.getName());
//                Navigation.findNavController(view).navigate(action);
//            }
//        });
//
//        // Define global action
//        NavDirections action = StudentListFragmentDirections.actionGlobalAddStudentFragment();

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(UserPostListFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
//        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllUserPosts((psList) -> {
            viewModel.setData(psList);
            adapter.setData(viewModel.getData());
//            binding.progressBar.setVisibility(View.GONE);
        });
    }
}