package com.example.coupledfragmentsubmission;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // List to store game data.
    public ArrayList<Game> list = new ArrayList<>();

    // Private variable to hold the current position of the index.
    private int indexPos = 0;

    // Tag for log messages.
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call to super method to call the super class onCreate methods.
        super.onCreate(savedInstanceState);

        // Log message to show current lifecycle state.
        Log.d(TAG, " ****** Lifecycle at onCreate ******");

        // Sets activity view using XML layout file.
        setContentView(R.layout.activity_main);

        // Sample test data added to the collection.
        Game game1 = new Game("Smash Brothers", "Switch", "Fighting game for up to 8 players");
        list.add(game1);
        Game game2 = new Game("Skyrim", "PS3", "Single player fantasy role playing game");
        list.add(game2);
        Game game3 = new Game("Stardew Valley", "PC", "Single player country life simulator");
        list.add(game3);

        updateView();

        // Initialises previous button and listener to decrement array index.
        Button buttonPrev = findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexPos = ((indexPos - 1) + list.size()) % list.size();
                updateView();
            }
        });

        // Initialises edit button and listener to begin the fragment transactions.
        Button buttonDetail = findViewById(R.id.buttonEdit);
        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFragment();
            }
        });

        // Initialises next button and listener to increment array index.
        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                indexPos = (indexPos + 1) % list.size();
                updateView();
            }
        });

    }

    // Method to update the view elements to display game details from list.
    private void updateView() {

        // Initialises view elements using corresponding ID's from the XML layout file.
        final TextView gameTextView = findViewById(R.id.textViewGame);
        final TextView platformTextView = findViewById(R.id.textViewPlatform);
        final TextView descTextView = findViewById(R.id.textViewDescription);
        final TextView completeTextView = findViewById(R.id.textViewComplete);

        // Sets values of view elements using the list with the current index position.
        for (Game game : list) {
            if (list.indexOf(game) == indexPos) {
                gameTextView.setText(game.getTitle());
                platformTextView.setText(game.getPlatform());
                descTextView.setText(game.getDescription());

                    // Sets the complete message and colour based on completion status.
                    if(game.isComplete()) {
                    completeTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.backgroundSuccess));
                    completeTextView.setText("Complete");
                    } else {
                        completeTextView.setBackgroundColor(ContextCompat.getColor(this,R.color.backgroundDefault));
                        completeTextView.setText("");
                    }
            }
        }
    }

    // Method to update a current list entry with new edited game details.
    public void updateEntry(Game newGame) {
        Game oldGame = list.get(indexPos);

        // Updates the current list item with the details passed by the newGame argument.
        oldGame.setTitle(newGame.getTitle());
        oldGame.setPlatform(newGame.getPlatform());
        oldGame.setDescription(newGame.getDescription());
        oldGame.setDateComplete(newGame.getDateComplete());
        oldGame.setComplete(newGame.isComplete());

        // Calls the view to update with new details.
        updateView();
    }

    // Method to start the edit fragment.
    private void editFragment() {

        // Initialises activity fragment manager and set fragment to show in the activities container.
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.container);

        if (fragment == null){

            // Packages the index position to be passed to the fragment.
            Bundle bundle = new Bundle();
            bundle.putInt("index", indexPos);
            EditFragment editFragment = new EditFragment();
            editFragment.setArguments(bundle);

            // Fragment transaction to show the edit fragment within the activity.
            fm.beginTransaction()
                    .add(R.id.container, editFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
