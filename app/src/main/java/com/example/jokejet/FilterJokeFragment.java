package com.example.jokejet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterJokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterJokeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterJokeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterJokeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterJokeFragment newInstance(String param1, String param2) {
        FilterJokeFragment fragment = new FilterJokeFragment();
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

        View fragmentView = inflater.inflate(R.layout.fragment_filter_joke, container, false);

        NavController cont = NavHostFragment.findNavController(this);

        getParentFragmentManager().setFragmentResultListener("rememberedFilters", getViewLifecycleOwner(), (requestKey, result) -> {
            setFiltersFromBundle(fragmentView, result);
        });

        Button submitButton = fragmentView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = setBundleValues(fragmentView);
                getParentFragmentManager().setFragmentResult("filters", bundle);
                cont.popBackStack();
            }
        });

        return fragmentView;
    }

    private void setFiltersFromBundle(View view, Bundle result) {
        CheckBox programmingCheckBox = view.findViewById(R.id.programmingCheckBox);
        CheckBox miscCheckBox = view.findViewById(R.id.miscCheckBox);
        CheckBox darkCheckBox = view.findViewById(R.id.darkCheckBox);
        CheckBox punCheckBox = view.findViewById(R.id.punCheckBox);
        CheckBox spookyCheckBox = view.findViewById(R.id.spookyCheckBox);
        CheckBox christmasCheckBox = view.findViewById(R.id.christmasCheckBox);

        CheckBox nsfwCheckBox = view.findViewById(R.id.nsfwCheckBox);
        CheckBox religiousCheckBox = view.findViewById(R.id.religiousCheckBox);
        CheckBox politicalCheckBox = view.findViewById(R.id.politicalCheckBox);
        CheckBox racistCheckBox = view.findViewById(R.id.racistCheckBox);
        CheckBox sexistCheckBox = view.findViewById(R.id.sexistCheckBox);
        CheckBox explicitCheckBox = view.findViewById(R.id.explicitCheckBox);

        CheckBox singleCheckBox = view.findViewById(R.id.singleCheckBox);
        CheckBox twoPartCheckBox = view.findViewById(R.id.twoPartCheckBox);

        programmingCheckBox.setChecked(result.getBoolean("Programming", false));
        miscCheckBox.setChecked(result.getBoolean("Misc", false));
        darkCheckBox.setChecked(result.getBoolean("Dark", false));
        punCheckBox.setChecked(result.getBoolean("Pun", false));
        spookyCheckBox.setChecked(result.getBoolean("Spooky", false));
        christmasCheckBox.setChecked(result.getBoolean("Christmas", false));

        nsfwCheckBox.setChecked(result.getBoolean("Nsfw", false));
        religiousCheckBox.setChecked(result.getBoolean("Religious", false));
        politicalCheckBox.setChecked(result.getBoolean("Political", false));
        racistCheckBox.setChecked(result.getBoolean("Racist", false));
        sexistCheckBox.setChecked(result.getBoolean("Sexist", false));
        explicitCheckBox.setChecked(result.getBoolean("Explicit", false));

        singleCheckBox.setChecked(result.getBoolean("Single", false));
        twoPartCheckBox.setChecked(result.getBoolean("Twopart", false));

        EditText jokeContainsEditText = view.findViewById(R.id.jokeContainsEditText);
        jokeContainsEditText.setText(result.getString("JokeContains", ""));
    }

    private Bundle setBundleValues(View view){
        Bundle bundle = new Bundle();

        CheckBox programmingCheckBox = view.findViewById(R.id.programmingCheckBox);
        CheckBox miscCheckBox = view.findViewById(R.id.miscCheckBox);
        CheckBox darkCheckBox = view.findViewById(R.id.darkCheckBox);
        CheckBox punCheckBox = view.findViewById(R.id.punCheckBox);
        CheckBox spookyCheckBox = view.findViewById(R.id.spookyCheckBox);
        CheckBox christmasCheckBox = view.findViewById(R.id.christmasCheckBox);

        CheckBox nsfwCheckBox = view.findViewById(R.id.nsfwCheckBox);
        CheckBox religiousCheckBox = view.findViewById(R.id.religiousCheckBox);
        CheckBox politicalCheckBox = view.findViewById(R.id.politicalCheckBox);
        CheckBox racistCheckBox = view.findViewById(R.id.racistCheckBox);
        CheckBox sexistCheckBox = view.findViewById(R.id.sexistCheckBox);
        CheckBox explicitCheckBox = view.findViewById(R.id.explicitCheckBox);

        CheckBox singleCheckBox = view.findViewById(R.id.singleCheckBox);
        CheckBox twoPartCheckBox = view.findViewById(R.id.twoPartCheckBox);

        EditText jokeContainsEditText = view.findViewById(R.id.jokeContainsEditText);

        bundle.putBoolean("Programming",programmingCheckBox.isChecked());
        bundle.putBoolean("Misc",miscCheckBox.isChecked());
        bundle.putBoolean("Dark",darkCheckBox.isChecked());
        bundle.putBoolean("Pun",punCheckBox.isChecked());
        bundle.putBoolean("Spooky",spookyCheckBox.isChecked());
        bundle.putBoolean("Christmas",christmasCheckBox.isChecked());

        bundle.putBoolean("Nsfw",nsfwCheckBox.isChecked());
        bundle.putBoolean("Religious",religiousCheckBox.isChecked());
        bundle.putBoolean("Political",politicalCheckBox.isChecked());
        bundle.putBoolean("Racist",racistCheckBox.isChecked());
        bundle.putBoolean("Sexist",sexistCheckBox.isChecked());
        bundle.putBoolean("Explicit",explicitCheckBox.isChecked());

        bundle.putBoolean("Single",singleCheckBox.isChecked());
        bundle.putBoolean("Twopart",twoPartCheckBox.isChecked());

        bundle.putString("JokeContains",jokeContainsEditText.getText().toString());
        return bundle;
    }
}