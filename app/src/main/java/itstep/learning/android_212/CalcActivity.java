package itstep.learning.android_212;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CalcActivity extends AppCompatActivity {
    private final int maxResultLength = 12;
    private final int maxResultDigits = 10;
    private TextView tvExpression;
    private TextView tvResult;
    private boolean needClearResult = true;
    private boolean isErrorDisplayed = false;

    private double operand = 0;
    private String pendingOperation = "";

    @SuppressLint("DiscouragedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        tvExpression = findViewById(R.id.calc_tv_expression);
        tvResult = findViewById(R.id.calc_tv_result);
        onClearClick(null);

        for (int i = 0; i < 10; i++) {
            findViewById(
                    getResources().getIdentifier("calc_btn_" + i, "id", getPackageName())
            ).setOnClickListener(this::onDigitClick);
        }

        findViewById(R.id.calc_btn_c).setOnClickListener(this::onClearClick);
        findViewById(R.id.calc_btn_ce).setOnClickListener(this::onClearEntryClick);
        findViewById(R.id.calc_btn_backspace).setOnClickListener(this::onBackspaceClick);
        findViewById(R.id.calc_btn_inv).setOnClickListener(this::onInverseClick);
        findViewById(R.id.calc_btn_sqrt).setOnClickListener(this::onSqrtClick);
        findViewById(R.id.calc_btn_pm).setOnClickListener(this::onPlusMinusClick);

        findViewById(R.id.calc_btn_add).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.calc_btn_sub).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.calc_btn_mul).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.calc_btn_div).setOnClickListener(this::onOperatorClick);

        findViewById(R.id.calc_btn_equal).setOnClickListener(this::onEqualsClick);
        findViewById(R.id.calc_btn_dot).setOnClickListener(this::onDotClick);
        findViewById(R.id.calc_btn_square).setOnClickListener(this::onSquareClick);
        findViewById(R.id.calc_btn_percent).setOnClickListener(this::onPercentClick);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("tvResult", tvResult.getText());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tvResult.setText(savedInstanceState.getCharSequence("tvResult"));
    }

    private void onDotClick(View view) {
        if (isErrorDisplayed) return;
        String result = tvResult.getText().toString();
        if (needClearResult) {
            result = "0";
            needClearResult = false;
        }
        if (!result.contains(".")) {
            tvResult.setText(result + ".");
        }
    }

    private void onSquareClick(View view) {
        if (isErrorDisplayed) return;
        String result = tvResult.getText().toString();
        double value = Double.parseDouble(result);
        result = resultFromDouble(value * value);
        tvResult.setText(result);
        needClearResult = true;
    }

    private void onPercentClick(View view) {
        if (isErrorDisplayed) return;
        String result = tvResult.getText().toString();
        double value = Double.parseDouble(result);
        value = operand * value / 100.0;
        result = resultFromDouble(value);
        tvResult.setText(result);
        needClearResult = true;
    }

    private void onPlusMinusClick(View view) {
        if (isErrorDisplayed) return;
        String result = tvResult.getText().toString();
        if (result.startsWith("-")) {
            result = result.substring(1);
        } else {
            result = "-" + result;
        }
        tvResult.setText(result);
    }

    private void onOperatorClick(View view) {
        if (isErrorDisplayed) return;
        double currentValue = Double.parseDouble(tvResult.getText().toString());
        if (!pendingOperation.isEmpty()) {
            calculate(currentValue);
        } else {
            operand = currentValue;
        }
        pendingOperation = ((Button) view).getText().toString();
        tvExpression.setText(operand + " " + pendingOperation);
        tvResult.setText("0");
        needClearResult = false;
    }

    private void calculate(double secondOperand) {
        switch (pendingOperation) {
            case "+": operand += secondOperand; break;
            case "-": operand -= secondOperand; break;
            case "×": operand *= secondOperand; break;
            case "÷":
                if (secondOperand == 0) {
                    tvResult.setText(getString(R.string.calc_div_zero));
                    isErrorDisplayed = true;
                    return;
                }
                operand /= secondOperand;
                break;
        }
        tvResult.setText(resultFromDouble(operand));
        needClearResult = true;
    }

    private void onSqrtClick(View view) {
        if (isErrorDisplayed) return;
        double arg = Double.parseDouble(tvResult.getText().toString());
        if (arg < 0) {
            tvResult.setText(getString(R.string.calc_negative_sqrt));
            isErrorDisplayed = true;
        } else {
            tvResult.setText(resultFromDouble(Math.sqrt(arg)));
        }
        needClearResult = true;
    }

    private void onInverseClick(View view) {
        if (isErrorDisplayed) return;
        double arg = Double.parseDouble(tvResult.getText().toString());
        if (arg == 0) {
            tvResult.setText(getString(R.string.calc_div_zero));
            isErrorDisplayed = true;
        } else {
            tvResult.setText(resultFromDouble(1.0 / arg));
        }
        needClearResult = true;
    }

    private String resultFromDouble(double arg) {
        String ret = (arg == (int) arg) ? String.valueOf((int) arg) : String.valueOf(arg);
        return ret.length() > maxResultLength ? ret.substring(0, maxResultLength) : ret;
    }

    private void onClearClick(View view) {
        tvResult.setText("0");
        tvExpression.setText("");
        isErrorDisplayed = false;
        operand = 0;
        pendingOperation = "";
        needClearResult = true;
    }

    private void onClearEntryClick(View view) {
        tvResult.setText("0");
        isErrorDisplayed = false;
    }

    private void onBackspaceClick(View view) {
        if (isErrorDisplayed) {
            onClearClick(view);
            return;
        }
        String result = tvResult.getText().toString();
        result = result.length() <= 1 ? "0" : result.substring(0, result.length() - 1);
        tvResult.setText(result);
        isErrorDisplayed = false;
    }

    private void onEqualsClick(View view) {
        if (isErrorDisplayed || pendingOperation.isEmpty()) return;
        double currentValue = Double.parseDouble(tvResult.getText().toString());
        calculate(currentValue);
        tvExpression.setText("");
        pendingOperation = "";
    }

    private void onDigitClick(View view) {
        String result = needClearResult ? "" : tvResult.getText().toString();
        if (result.equals("0")) result = "";
        if (result.length() >= maxResultLength) return;
        result += ((Button) view).getText();
        tvResult.setText(result);
        isErrorDisplayed = false;
        needClearResult = false;
    }
}
