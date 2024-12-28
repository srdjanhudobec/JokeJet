package com.example.jokejet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetJokeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetJokeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String url;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GetJokeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetJokeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetJokeFragment newInstance(String param1, String param2) {
        GetJokeFragment fragment = new GetJokeFragment();
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

        View view = inflater.inflate(R.layout.fragment_get_joke, container, false);

        NavController cont = NavHostFragment.findNavController(this);

        generateUrl();

        Button filterButton = view.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startDestination = cont.getCurrentDestination().getId();
//                Bundle bundle = new Bundle();
//                bundle.putString("fromGetJokeToFilter", "fromGet");
                if (startDestination == R.id.getJokeFragment) {
                    cont.navigate(R.id.action_getJokeFragment_to_filterJokeFragment);
                } else {
                    cont.popBackStack();
                }
            }
        });

        return view;
    }

    private void generateUrl(){
        getParentFragmentManager().setFragmentResultListener("filters", getViewLifecycleOwner(), (requestKey, result) -> {
            List<String> categories = new ArrayList<>();
            if (result.getBoolean("Programming")) categories.add("Programming");
            if (result.getBoolean("Misc")) categories.add("Misc");
            if (result.getBoolean("Dark")) categories.add("Dark");
            if (result.getBoolean("Pun")) categories.add("Pun");
            if (result.getBoolean("Spooky")) categories.add("Spooky");
            if (result.getBoolean("Christmas")) categories.add("Christmas");

            String categoriesPart = categories.isEmpty() ? "Any" : String.join(",", categories);

            List<String> flags = new ArrayList<>();
            if (result.getBoolean("Nsfw")) flags.add("nsfw");
            if (result.getBoolean("Religious")) flags.add("religious");
            if (result.getBoolean("Political")) flags.add("political");
            if (result.getBoolean("Racist")) flags.add("racist");
            if (result.getBoolean("Sexist")) flags.add("sexist");
            if (result.getBoolean("Explicit")) flags.add("explicit");

            String flagsPart = flags.isEmpty() ? "" : "?&blacklistFlags=" + String.join(",", flags);

            boolean single = result.getBoolean("Single");
            boolean twopart = result.getBoolean("Twopart");
            String typePart = "";
            if (single && !twopart) {
                typePart = "&type=single";
            } else if (!single && twopart) {
                typePart = "&type=twopart";
            }

            String jokeContains = result.getString("JokeContains");
            String containsPart = "";
            if (jokeContains != null && !jokeContains.isEmpty()) {
                containsPart = "&contains=" + jokeContains;
            }

            url = "https://v2.jokeapi.dev/joke/" + categoriesPart + flagsPart + typePart + containsPart;

            Log.d("url",url);
            getParentFragmentManager().setFragmentResult("rememberedFilters", result);
        });
    }
}