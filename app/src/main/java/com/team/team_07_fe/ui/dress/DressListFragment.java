package com.team.team_07_fe.ui.dress;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.team.team_07_fe.R;

public class DressListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DressViewModel dressViewModel;


    public DressListFragment() {
        // Required empty public constructor
    }


    public static DressListFragment newInstance(String param1, String param2) {
        DressListFragment fragment = new DressListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dress_list, container, false);
        FloatingActionButton fab = view.findViewById(R.id.button_more);
        fab.setOnClickListener(v->{
            NavHostFragment.findNavController(DressListFragment.this).navigate(R.id.action_navigation_dress_to_dressAddFragment);
        });
        return view;
    }

}