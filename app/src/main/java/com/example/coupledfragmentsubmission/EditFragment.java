package com.example.coupledfragmentsubmission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Date;

public class EditFragment extends Fragment {

    // Variable to store the current position of the list passed by the activity.
    private int indexPos;

    // Variable to store completion date upon checkbox check.
    private Date newCompleteDate = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Calls onCreate methods from super class.
        super.onCreate(savedInstanceState);

        // Gets arguments using bundle passed from activity to declare index position of the list.
        Bundle args = getArguments();
        int index = args.getInt("index", 0);
        indexPos = index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflates the fragment view using the XML layout file.
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        // Gathers list of games using direct coupling with activity class.
        ArrayList<Game> lists = ((MainActivity)getActivity()).list;

        // Initialises view elements using corresponding ID's from the XML layout file.
        final EditText gameTitle = view.findViewById(R.id.gameTitle);
        final EditText gamePlatform = view.findViewById(R.id.gamePlatform);
        final EditText gameDescription = view.findViewById(R.id.gameDescription);
        final Button dateButton = view.findViewById(R.id.completedDate);
        final CheckBox isComplete = view.findViewById(R.id.gameComplete);
        final Button buttonApply = view.findViewById(R.id.buttonApply);

        // Sets the starting values of view elements using the list with the current index position.
        for(Game game : lists) {
            if (lists.indexOf(game) == indexPos) {
                gameTitle.setText(game.getTitle());
                gamePlatform.setText(game.getPlatform());
                gameDescription.setText(game.getDescription());
                isComplete.setChecked(game.isComplete());
                if (game.isComplete()) {
                    dateButton.setText(game.getDateComplete().toString());
                }
            }
        }

        // Initialise listener for checkbox on checked change.
        isComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                // Declares new date and updates view based on checkbox status.
                if (isChecked) {
                    newCompleteDate = new Date();
                    dateButton.setText(newCompleteDate.toString());
                } else {
                    dateButton.setText("");
                }
            }
        });

        // Initialises next button listener to increment array index.
        buttonApply.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // Creates a new game to pass updated details to activity method.
                Game game = new Game(gameTitle.getText().toString(),gamePlatform.getText().toString(),gameDescription.getText().toString());

                // Sets details for game updates completion status and date.
                if(newCompleteDate != null) {
                    game.setDateComplete(newCompleteDate);
                }
                if(isComplete.isChecked())
                {
                    game.setComplete(true);
                }

                // Calls update method from
                ((MainActivity)getActivity()).updateEntry(game);

                // Uses activity fragment manager to pop the entire backstack to remove fragments and return to activity.
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
            }
        });

        return view;
    }
}

