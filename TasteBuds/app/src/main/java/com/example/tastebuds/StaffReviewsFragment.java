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

import com.example.tastebuds.databinding.FragmentStaffReviewsBinding;
import com.example.tastebuds.model.StaffReviewModel;

public class StaffReviewsFragment extends Fragment {
    FragmentStaffReviewsBinding binding;
    StaffRecyclerAdapter adapter;
    StaffReviewsFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStaffReviewsBinding.inflate(inflater, container, false);

        /*RecyclerView include:
         * 1. Layout Manager
         * 2. Adapter
         * 3. ViewHolder
         * 4. Row View*/
        binding.recyclerView.setHasFixedSize(true);

        // 1
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 2
        adapter = new StaffRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(), list -> {
            adapter.setData(list);
        });

        StaffReviewModel.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.swipeRefresh.setRefreshing(status == StaffReviewModel.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        viewModel = new ViewModelProvider(this).get(StaffReviewsFragmentViewModel.class);
    }

    void reloadData() {
        StaffReviewModel.instance().getStaffReviews();
    }
}