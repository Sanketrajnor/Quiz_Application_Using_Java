package easy.tuto.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    TextView scoreTextView;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        scoreTextView = findViewById(R.id.score_text_view);
        resultTextView = findViewById(R.id.result_text_view);

        int score = getIntent().getIntExtra("score", 0);
        int totalQuestions = getIntent().getIntExtra("totalQuestions", 0);

        scoreTextView.setText("Score: " + score + "/" + totalQuestions);

        if (score > totalQuestions * 0.6) {
            resultTextView.setText("Congratulations! You passed the quiz.");
        } else {
            resultTextView.setText("Sorry, you didn't pass the quiz.");
        }
    }
}
