package com.example.jokejet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokejet.R;
import com.example.jokejet.model.Joke;

import java.util.List;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.MyViewHolder> {

    private final List<Joke> jokeList;
    private final OnDeleteClickListener deleteClickListener;

    public interface OnDeleteClickListener {
        void onDelete(int id);
    }

    public void addJoke(Joke joke) {
        this.jokeList.add(joke);
        notifyItemInserted(jokeList.size() - 1);
    }

    public JokeAdapter(List<Joke> jokeList, OnDeleteClickListener deleteClickListener) {
        this.jokeList = jokeList;
        this.deleteClickListener = deleteClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtId;
        private final TextView txtContent;
        private final TextView txtDelivery;
        private final TextView txtCategory;
        private final Button btnDelete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.idTextView);
            txtContent = itemView.findViewById(R.id.contentTextView);
            txtDelivery = itemView.findViewById(R.id.deliveryTextView);
            txtCategory = itemView.findViewById(R.id.categoryTextView);
            btnDelete = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(Joke joke, OnDeleteClickListener deleteClickListener) {
            txtId.setText(String.valueOf(joke.getId()));
            txtContent.setText(joke.getContent());
            if(joke.getDelivery() == null){
                txtDelivery.setText("");
            }else{
                txtDelivery.setText("Delivery: " + joke.getDelivery());
            }
            txtCategory.setText(joke.getCategory());
            btnDelete.setOnClickListener(v -> deleteClickListener.onDelete(joke.getId()));
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joke_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Joke joke = jokeList.get(position);
        holder.bind(joke, deleteClickListener);
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }
}
