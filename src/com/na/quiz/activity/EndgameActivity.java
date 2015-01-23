package com.na.quiz.activity;

import java.util.List;

import com.na.quiz.QuizApplication;
import com.na.quiz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.na.quiz.domain.GamePlay;
import com.na.quiz.domain.Question;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Basic activity to display end results for the game
 *
 */
public class EndgameActivity extends Activity {
	
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_endgame);
		GamePlay currentGame = ((QuizApplication)getApplication()).getCurrentGame();
		String result = "You Got " + currentGame.getRight() + "/" + currentGame.getNumRounds() + ".. ";
		
		TextView results = (TextView)findViewById(R.id.endgameResult);
		results.setText( result );
	}
	
	
	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK )return true;
		return super.onKeyDown(keyCode, event);
	}


	public void startNewGame( View v ) throws ParseException {
		ParseQuery<Question> query = ParseQuery.getQuery("Question");
		query.fromLocalDatastore();
		List<Question> questions = query.find();
		GamePlay c = new GamePlay();
		c.setQuestions(questions);
		c.setNumRounds( questions.size() );
		((QuizApplication)getApplication()).setCurrentGame(c);  

		//Start new game..
		Intent i = new Intent(this, QuestionActivity.class);
		startActivityForResult(i, 1);
	}
	public void finish( View v ) {
		finish();
	}

}