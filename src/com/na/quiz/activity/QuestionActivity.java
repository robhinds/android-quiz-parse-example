package com.na.quiz.activity;

import com.na.quiz.QuizApplication;
import com.na.quiz.R;
import com.na.quiz.domain.GamePlay;
import com.na.quiz.domain.Question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuestionActivity extends Activity implements OnClickListener{

	private Question currentQ;
	private GamePlay currentGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        /**
         * Configure current game and get question
         */
        currentGame = ((QuizApplication)getApplication()).getCurrentGame();
        currentQ = currentGame.getNextQuestion();
		Button nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);
        
        /**
         * Update the current question and answer options..
         */
        setQuestions();
		
    }


	/**
	 * Method to set the text for the question and answers from the current games
	 * current question
	 */
	private void setQuestions() {
		//set the question text from current question
		String question = currentQ.getQuestion() + "?";
        TextView qText = (TextView) findViewById(R.id.question);
        qText.setText(question);
        
        //set the available options
        TextView option1 = (TextView) findViewById(R.id.answer1);
        option1.setText( currentQ.getOptionOne() );
        
        TextView option2 = (TextView) findViewById(R.id.answer2);
        option2.setText( currentQ.getOptionTwo() );
        
        TextView option3 = (TextView) findViewById(R.id.answer3);
        option3.setText( currentQ.getOptionThree() );
        
        TextView option4 = (TextView) findViewById(R.id.answer4);
        option4.setText( currentQ.getOptionFour() );
	}
    

	@Override public void onClick(View v) {
		//validate a checkbox has been selected
		if (!checkAnswer()) return;
		
		// check if end of game
		if (currentGame.isGameOver()){
			Intent i = new Intent(this, EndgameActivity.class);
			startActivity(i);
			finish();
		} else {
			Intent i = new Intent(this, QuestionActivity.class);
			startActivity(i);
			finish();
		}
	}
	
	
	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
			case KeyEvent.KEYCODE_BACK : return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * Check if a checkbox has been selected, and if it
	 * has then check if its correct and update gamescore
	 */
	private boolean checkAnswer() {
		RadioGroup options = (RadioGroup)findViewById(R.id.group1);
		Integer selected = options.getCheckedRadioButtonId();
		if ( selected < 0){
			return false;
		} else {
			if (currentQ.getCorrectAnswer() == selected+1 ) {
				currentGame.incrementRightAnswers();
			} else {
				currentGame.incrementWrongAnswers();
			}
			return true;
		}
	}
	
}