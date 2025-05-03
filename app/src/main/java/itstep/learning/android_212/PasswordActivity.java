package itstep.learning.android_212;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    private final Random random = new Random();
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        TextView tvPassword = findViewById(R.id.tv_password);
        TextView tvLength = findViewById(R.id.tv_length);
        SeekBar seekLength = findViewById(R.id.seek_length);
        CheckBox cbUpper = findViewById(R.id.cb_uppercase);
        CheckBox cbNum = findViewById(R.id.cb_numbers);
        CheckBox cbSym = findViewById(R.id.cb_symbols);
        Button btnGenerate = findViewById(R.id.btn_generate);

        Button btnCopy = findViewById(R.id.btn_copy);
        btnCopy.setOnClickListener(v -> copyToClipboard(tvPassword.getText().toString()));

        seekLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLength.setText("lenght: " + progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnGenerate.setOnClickListener(v -> {
            int length = seekLength.getProgress();
            StringBuilder chars = new StringBuilder(LOWER);
            if (cbUpper.isChecked()) chars.append(UPPER);
            if (cbNum.isChecked()) chars.append(NUMBERS);
            if (cbSym.isChecked()) chars.append(SYMBOLS);

            if (chars.length() == 0) {
                tvPassword.setText("select at least 1 char");
                return;
            }

            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int idx = random.nextInt(chars.length());
                password.append(chars.charAt(idx));
            }

            tvPassword.setText(password.toString());
        });
    }

    private void copyToClipboard(String text) {
        if (text.isEmpty() || text.equals("pswrd")) {
            Toast.makeText(this, "no pass to copy", Toast.LENGTH_SHORT).show();
            return;
        }

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Generated Password", text);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(this, "pass copied", Toast.LENGTH_SHORT).show();
    }
}