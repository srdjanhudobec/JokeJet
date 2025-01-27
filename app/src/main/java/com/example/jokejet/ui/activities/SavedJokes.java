package com.example.jokejet.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokejet.R;
import com.example.jokejet.adapter.JokeAdapter;
import com.example.jokejet.data.Database;
import com.example.jokejet.model.Joke;
import com.example.jokejet.data.JokeRepository;

import java.util.List;

public class SavedJokes extends AppCompatActivity {

    private JokeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saved_jokes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Database db = new Database(this);
        JokeRepository jr = new JokeRepository(db);
        List<Joke> jokes = jr.getAllJokes("saved");
        RecyclerView recyclerView = findViewById(R.id.savedJokesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new JokeAdapter(jokes, id -> {
            JokeRepository repository = new JokeRepository(db);

            repository.deleteJoke(id);

            jokes.removeIf(joke -> joke.getId() == id);

            adapter.notifyDataSetChanged();
        });

        recyclerView.setAdapter(adapter);

        ImageButton back = findViewById(R.id.backSavedButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}