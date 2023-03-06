package com.example.tastebuds;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.tastebuds.databinding.FragmentStaffReviewsBinding;
import com.example.tastebuds.model.StaffReview;
import com.example.tastebuds.model.StaffReviewModel;

import java.util.List;

public class StaffReviewsFragment extends Fragment {
    FragmentStaffReviewsBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.staffReviewsFragment);
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
        binding = FragmentStaffReviewsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        LiveData<List<StaffReview>> data = StaffReviewModel.instance.getStaffReviews();

        // TODO : use the data to create review list (need to create recycler view shit)
        data.observe(getViewLifecycleOwner(), reviews->{
            Log.d("APIcheck", "Staff reviews changed: " + reviews.get(0).toString());

        });

        return view;
    }
}