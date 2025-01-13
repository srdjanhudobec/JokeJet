package com.example.jokejet.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jokejet.R;
import com.example.jokejet.data.Database;
import com.example.jokejet.model.Joke;
import com.example.jokejet.data.JokeRepository;
import com.example.jokejet.ui.activities.BlacklistedJokes;
import com.example.jokejet.ui.activities.SavedJokes;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    private String url = "https://v2.jokeapi.dev/joke/Any";

    private Joke joke = new Joke();

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

        String deviceLanguage = Locale.getDefault().getLanguage();
        String languagePart = "";
        if (!deviceLanguage.isEmpty() && (deviceLanguage=="cs" || deviceLanguage == "de" || deviceLanguage == "es" || deviceLanguage=="fr" || deviceLanguage=="pt")) {
            languagePart = "?lang=" + deviceLanguage;
        }
        url += languagePart;

        generateUrl();

        Database db = new Database(requireActivity());
        JokeRepository jr = new JokeRepository(db);
        Button filterButton = view.findViewById(R.id.filterButton);
        Button savedJokesButton = view.findViewById(R.id.savedJokesButton);
        Button blacklistedJokesButton = view.findViewById(R.id.blackListedJokesButton);
        ImageButton saveJokeButton = view.findViewById(R.id.saveJokeButton);
        ImageButton blacklistJokeButton = view.findViewById(R.id.blacklistJokeButton);
        Button getJokeButton = view.findViewById(R.id.getJokeButton);
        TextView jokeTextView = view.findViewById(R.id.jokeTextView);
        TextView jokeCategoryTextView = view.findViewById(R.id.jokeCategoryTextView);
        Button revealDeliveryButton = view.findViewById(R.id.revealDeliveryButton);

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startDestination = cont.getCurrentDestination().getId();
                if (startDestination == R.id.getJokeFragment) {
                    cont.navigate(R.id.action_getJokeFragment_to_filterJokeFragment);
                } else {
                    cont.popBackStack();
                }
            }
        });

        savedJokesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), SavedJokes.class);
                startActivity(i);
            }
        });

        blacklistedJokesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), BlacklistedJokes.class);
                startActivity(i);
            }
        });

        getJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJoke(jr, jokeTextView, jokeCategoryTextView, revealDeliveryButton, new int[] {0});
            }});

        saveJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Joke result = null;
                if (!jokeTextView.getText().toString().isEmpty()) {
                    if (joke.getDelivery() != null) {
                        result = jr.addJoke(joke.getId(), joke.getContent(), joke.getCategory(), "twopart", joke.getDelivery(), "saved");
                    } else {
                        result = jr.addJoke(joke.getId(), joke.getContent(), joke.getCategory(), "single", null, "saved");
                    }
                    if(result != null) {
                        Toast.makeText(requireActivity(), "Joke saved successfully.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireActivity(), "Joke cannot be saved.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Joke content cannot be empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        blacklistJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Joke result = null;
                if (!jokeTextView.getText().toString().isEmpty()) {
                    if (joke.getDelivery() != null) {
                        result = jr.addJoke(joke.getId(), joke.getContent(), joke.getCategory(), "twopart", joke.getDelivery(), "blacklisted");
                    } else {
                        result = jr.addJoke(joke.getId(), joke.getContent(), joke.getCategory(), "single", null, "blacklisted");
                    }
                    if(result != null) {
                        Toast.makeText(requireActivity(), "Joke blacklisted successfully.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireActivity(), "Joke cannot be blacklisted.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Joke content cannot be empty.", Toast.LENGTH_SHORT).show();
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

            String deviceLanguage = Locale.getDefault().getLanguage();
            Log.d("language",deviceLanguage);
            String languagePart = "";
            if (!deviceLanguage.isEmpty() && (deviceLanguage=="cs" || deviceLanguage == "de" || deviceLanguage == "es" || deviceLanguage=="fr" || deviceLanguage=="pt")) {
                languagePart = "lang=" + deviceLanguage;
            }

            List<String> flags = new ArrayList<>();
            if (result.getBoolean("Nsfw")) flags.add("nsfw");
            if (result.getBoolean("Religious")) flags.add("religious");
            if (result.getBoolean("Political")) flags.add("political");
            if (result.getBoolean("Racist")) flags.add("racist");
            if (result.getBoolean("Sexist")) flags.add("sexist");
            if (result.getBoolean("Explicit")) flags.add("explicit");

            String flagsPart = flags.isEmpty() ? "" : "&blacklistFlags=" + String.join(",", flags);

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

            if(flagsPart != "" || typePart != "" || containsPart != "" || languagePart != "") {
                url = "https://v2.jokeapi.dev/joke/" + categoriesPart +  "?" + languagePart + flagsPart + typePart + containsPart;
            }else if(categoriesPart == "Any"){
                url = "https://v2.jokeapi.dev/joke/Any";
            }else{
                url = "https://v2.jokeapi.dev/joke/" + categoriesPart;
            }
            Log.d("url",url);
            getParentFragmentManager().setFragmentResult("rememberedFilters", result);
        });
    }

    private void getJoke(JokeRepository jr, TextView jokeTextView, TextView categoryTextView, Button revealDeliveryButton, int[] attemptCounter) {
        joke = new Joke();
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        final int maxAttempts = 3;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = Integer.parseInt(response.getString("id"));
                            joke.setId(id);

                            if (jr.isJokeBlacklisted(id)) {
                                attemptCounter[0]++;
                                if (attemptCounter[0] < maxAttempts) {
                                    jokeTextView.setText("This joke is blacklisted. Retrying...");
                                    categoryTextView.setText("");
                                    revealDeliveryButton.setVisibility(View.INVISIBLE);

                                    new Handler().postDelayed(() -> getJoke(jr, jokeTextView, categoryTextView, revealDeliveryButton, attemptCounter), 1000);
                                } else {
                                    Toast.makeText(requireActivity(), "No more jokes available after 3 attempts.", Toast.LENGTH_SHORT).show();
                                    jokeTextView.setText("No jokes available.");
                                    categoryTextView.setText("");
                                    revealDeliveryButton.setVisibility(View.INVISIBLE);
                                }
                                return;
                            }
                            attemptCounter[0] = 0;

                            if (response.has("setup") && response.has("delivery")) {
                                String setup = response.getString("setup");
                                joke.setContent(setup);
                                jokeTextView.setText(setup);

                                revealDeliveryButton.setVisibility(View.VISIBLE);
                                revealDeliveryButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            String delivery = response.getString("delivery");
                                            joke.setDelivery(delivery);
                                            jokeTextView.setText(delivery);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } else if (response.has("joke")) {
                                String content = response.getString("joke");
                                joke.setContent(content);
                                joke.setDelivery(null);
                                jokeTextView.setText(content);
                                revealDeliveryButton.setVisibility(View.INVISIBLE);
                            }

                            String category = response.getString("category");
                            joke.setCategory(category);
                            categoryTextView.setText(category);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Toast.makeText(requireActivity(), "Error occurred while fetching joke.", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}