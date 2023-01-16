package com.example.tastebuds;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tastebuds.databinding.FragmentFeedListBinding;
import com.example.tastebuds.model.Model;

public class FeedListFragment extends Fragment {
    FragmentFeedListBinding binding;
    FeedRecyclerAdapter adapter;
    FeedListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedListBinding.inflate(inflater, container, false);

        /*RecyclerView include:
         * 1. Layout Manager
         * 2. Adapter
         * 3. ViewHolder
         * 4. Row View*/
        binding.recyclerView.setHasFixedSize(true);

        // 1
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 2
        adapter = new FeedRecyclerAdapter(getLayoutInflater(), viewModel.getData());
        binding.recyclerView.setAdapter(adapter);

//        /*-> Handle row click in the activity*/
//        adapter.setOnItemClickListener(new FeedRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                Log.d("TAG", "row click handle in activity " + pos);
//                Post post = viewModel.getData().get(pos);
//                FeedListFragmentDirections.ActionStudentListFragmentToBlueFragment action =  FeedListFragmentDirections.actionStudentListFragmentToBlueFragment(post.getName());
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

        viewModel = new ViewModelProvider(this).get(FeedListFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllPosts((psList) -> {
            viewModel.setData(psList);
            adapter.setData(viewModel.getData());
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}