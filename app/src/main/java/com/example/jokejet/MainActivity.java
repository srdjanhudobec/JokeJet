package com.example.jokejet;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NavHostFragment nhf = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainterView);
        assert nhf != null;
        NavController cont = nhf.getNavController();
        Database db = new Database(this);
        JokeRepository jr = new JokeRepository(db);
        jr.addJoke(1,"neka sala","programming","single",null,"saved");
        jr.addJoke(2,"sta kaaze neko","programming","twopart","nista","blacklisted");
        jr.addJoke(3,"sta kaaze neko","programming","twopart","nista","blacklisted");
        Log.d("saved",jr.getAllJokes("saved").toString());
        Log.d("blacklisted",jr.getAllJokes("blacklisted").toString());
//        Context context = getApplicationContext();
//        boolean isDeleted = context.deleteDatabase("jokejet.sqlite");
//
//        if (isDeleted) {
//            Log.d("Database", "Baza uspešno obrisana.");
//        } else {
//            Log.d("Database", "Brisanje baze nije uspelo ili baza ne postoji.");
//        }

//        TextView jokeTextView = findViewById(R.id.jokeTextView);
//        TextView categoryTextView = findViewById(R.id.categoryTextView);
//        Button getJokeButton = findViewById(R.id.getJokeButton);
//
//        getJokeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getJoke(jokeTextView,categoryTextView);
//            }
//        });
    }
    /*moze biti vise kategorija, da se napravi kompletno dobavljanje sa svim filterima,
    sve proci kako se url generise, fragment za filtere (koristiti navigaciju pogledati vezbe), karticu napraviti kao za salu,
    Promeniti temu da ne bude sve ljubicaste boje, ima isto na gitu kako se menja
    ubaciti neku sliku, swajpovanje koje ce za pocetak izbacivati toast
    Posle ubaciti 2 activity odvojena za sacuvane sale i za blacklisted
    Raditi po primeru sa gita gde ima repository i add i remove funkcionalnosti u recyclerviewu
     */


//    private void getJoke(TextView jokeTextView,TextView categoryTextView){
//        String baseUrl = "https://v2.jokeapi.dev/joke/Any";
//        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseUrl, new JSONObject(),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String joke = response.getString("joke");
//                            String category = response.getString("category");
//                            jokeTextView.setText(joke);
//                            categoryTextView.setText(category);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error",error.toString());
//            }
//        });
//
//        queue.add(jsonObjectRequest);
//    }
}