package itstep.learning.android_212;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.google.zxing.BarcodeFormat;

public class QrActivity extends AppCompatActivity {

    private EditText etInput;
    private ImageView ivQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        etInput = findViewById(R.id.et_qr_input);
        ivQr = findViewById(R.id.iv_qr_code);
        Button btnGenerate = findViewById(R.id.btn_generate_qr);

        btnGenerate.setOnClickListener(v -> generateQr());
    }

    private void generateQr() {
        String text = etInput.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "enter text/link", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 600, 600);
            ivQr.setImageBitmap(bitmap);
            ivQr.setVisibility(ImageView.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }
}