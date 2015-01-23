package com.na.quiz.activity;

import java.util.List;

import com.na.quiz.QuizApplication;
import com.na.quiz.R;
import com.na.quiz.domain.GamePlay;
import com.na.quiz.domain.Question;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Menu page welcome screen 
 */
public class WelcomeActivity extends Activity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    
    
    public void startNewGame( View v ) throws ParseException{
		ParseQuery<Question> query = ParseQuery.getQuery("Question");
		query.fromLocalDatastore();
		List<Question> questions = query.find();

		if ( questions.size() > 0 ){ 
			startGameActivity( questions );
		} else {
			query = ParseQuery.getQuery("Question");
			final Activity ctx = this;
			query.findInBackground( new FindCallback<Question>() {
				@Override public void done(List<Question> questions, ParseException e) {
					if ( e == null ){
						ParseObject.unpinAllInBackground( "QUESTIONS" );
						ParseObject.pinAllInBackground( "QUESTIONS",  questions );
						startGameActivity( questions );
					} else {
						Toast.makeText(ctx, 
								"Error updating questions - please make sure you have internet connection", 
								Toast.LENGTH_LONG).show();
					}
				}
			});		
		}
    }
    
    
    private void startGameActivity( List<Question> questions ){
		//Initialise Game with retrieved question set 
		GamePlay c = new GamePlay();
		c.setQuestions(questions);
		c.setNumRounds( questions.size() );
		((QuizApplication)getApplication()).setCurrentGame(c);  

		//Start Game Now.. //
		Intent i = new Intent(this, QuestionActivity.class);
		startActivityForResult(i, 1);
    }

}
