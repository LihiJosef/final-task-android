package com.example.tastebuds;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tastebuds.databinding.FragmentFeedListBinding;
import com.example.tastebuds.databinding.FragmentNewPostBinding;

public class FeedListFragment extends Fragment {
    FragmentFeedListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFeedListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}