package com.example.johnnylee.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.johnnylee.quizapp.R.array.answers;

public class MainActivity extends AppCompatActivity {

    String[] correctAnswers = new String[10];
    int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correctAnswers = getResources().getStringArray(answers);
    }

    public void onSubmitAnswers(View v) {
        score = 0;
        String message = "";

        int[] ids = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4, R.id.answer5};

        for (int index = 0; index < ids.length; index++) {
            int id = ids[index];
            View view = findViewById(id);
            boolean test = checkAnswer(view, index);
            if (test == false) {
                message = "You did not answer Question " + (index + 1);
                break;
            }
        }

        message = "You scored " + score + "!";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean checkAnswer(View view, int index) {
        if (view instanceof RadioGroup) {
            RadioGroup radiogroup = (RadioGroup) view;
            return checkAnswer(radiogroup, index);
        }
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            return checkAnswer(editText, index);
        }
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            return checkAnswer(linearLayout, index);
        }
        return false;
    }

    public boolean checkAnswer(RadioGroup radiogroup, int index) {
        int checkedId = radiogroup.getCheckedRadioButtonId();

        if (checkedId == -1)
            return false;

        RadioButton radioButton = (RadioButton) findViewById(checkedId);
        String answer = radioButton.getText().toString().trim();
        String correctAnswer = correctAnswers[index];

        if (correctAnswer.equals(answer))
            score += 20;

        return true;
    }

    public boolean checkAnswer(EditText editText, int index) {
        String answer = editText.getText().toString();

        if (answer.isEmpty())
            return false;

        String correctAnswer = correctAnswers[index];

        if (Integer.parseInt(answer) == Integer.parseInt(correctAnswer))
            score = score + 20;

        return true;
    }

    public boolean checkAnswer(LinearLayout linearLayout, int index) {
        int childcount = linearLayout.getChildCount();

        List<String> answers = new ArrayList<String>();
        String correctAnswer = correctAnswers[index];
        List<String> correctList = new ArrayList<String>(Arrays.asList(correctAnswer.split(",")));

        for (int i = 0; i < childcount; i++) {
            View view = linearLayout.getChildAt(i);
            if (view instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    answers.add(checkBox.getText().toString());
                }
            }
        }

        if (answers.isEmpty() == true)
            return false;

        Collections.sort(answers);
        Collections.sort(correctList);

        if (answers.equals(correctList))
            score += 20;

        return true;
    }


}
