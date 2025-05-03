package itstep.learning.android_212;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStartPause;
    private Handler handler = new Handler();
    private boolean isRunning = false;
    private int seconds = 0;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            int mins = seconds / 60;
            int secs = seconds % 60;
            tvTimer.setText(String.format("%02d:%02d", mins, secs));
            if (isRunning) {
                seconds++;
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvTimer = findViewById(R.id.tv_timer);
        btnStartPause = findViewById(R.id.btn_start_pause);
        Button btnReset = findViewById(R.id.btn_reset);

        btnStartPause.setOnClickListener(v -> {
            isRunning = !isRunning;
            btnStartPause.setText(isRunning ? "pause" : "start");
            if (isRunning) handler.post(timerRunnable);
        });

        btnReset.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(timerRunnable);
            seconds = 0;
            tvTimer.setText("00:00");
            btnStartPause.setText("start");
        });
    }
}