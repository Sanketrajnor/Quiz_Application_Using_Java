package easy.tuto.myquizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView totalQuestionsTextView;
    TextView questionTextView;
    Button ansA, ansB, ansC, ansD;
    Button submitBtn;
    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    CountDownTimer countDownTimer;
    TextView countdownTextView;
    int remainingTime = 30; // Initial time in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionsTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        submitBtn = findViewById(R.id.submit_btn);
        countdownTextView = findViewById(R.id.countdown_text_view);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionsTextView.setText("Total questions: " + totalQuestion);
        loadNewQuestion();
        startTimer();
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        if (clickedButton.getId() == R.id.submit_btn) {
            // Submit button clicked
            if (selectedAnswer.equals("")) {
                // No option selected
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error").setMessage("Please select an option").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return;
            }

            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                // Selected answer is correct
                score++;
                clickedButton.setBackgroundColor(Color.GREEN);
            } else {
                // Selected answer is incorrect
                clickedButton.setBackgroundColor(Color.RED);
                highlightCorrectAnswer();
            }

            currentQuestionIndex++;
            loadNewQuestion();
            restartTimer();
            selectedAnswer = "";

            if (currentQuestionIndex == totalQuestion) {
                finishQuiz();
            }
        } else {
            // Choices button clicked
            ansA.setBackgroundResource(R.drawable.round_btn);
            ansB.setBackgroundResource(R.drawable.round_btn);
            ansC.setBackgroundResource(R.drawable.round_btn);
            ansD.setBackgroundResource(R.drawable.round_btn);

            selectedAnswer = clickedButton.getText().toString();
              clickedButton.setBackgroundColor(Color.GRAY);
        }
    }

    void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            return;
        }

        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        stopTimer();

        Intent intent = new Intent(MainActivity.this, ReportActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("totalQuestions", totalQuestion);
        startActivity(intent);
        finish();
    }

    void startTimer() {
        countDownTimer = new CountDownTimer(remainingTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) (millisUntilFinished / 1000);
                countdownTextView.setText(String.valueOf(remainingTime));
            }

            @Override
            public void onFinish() {
                countdownTextView.setText("Time's up!");
                finishQuiz();
            }
        };

        countDownTimer.start();
    }

    void restartTimer() {
        countDownTimer.cancel();
        remainingTime = 30; // Reset the timer to 30 seconds
        countdownTextView.setText(String.valueOf(remainingTime));
        startTimer();
    }

    void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
    private void highlightCorrectAnswer() {
        String correctAnswer = QuestionAnswer.correctAnswers[currentQuestionIndex];

        if (ansA.getText().toString().equals(correctAnswer)) {
            ansA.setBackgroundColor(Color.GREEN);
        } else if (ansB.getText().toString().equals(correctAnswer)) {
            ansB.setBackgroundColor(Color.GREEN);
        } else if (ansC.getText().toString().equals(correctAnswer)) {
            ansC.setBackgroundColor(Color.GREEN);
        } else if (ansD.getText().toString().equals(correctAnswer)) {
            ansD.setBackgroundColor(Color.GREEN);
        }
    }

}
