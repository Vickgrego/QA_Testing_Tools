package testing.hryhorchuk.com.testingtools;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import testing.hryhorchuk.com.testingtools.StringGenerator.OPTIONS;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    static final String STATE_LENGTH = "string_length";
    static final String STATE_STR_GENERATED = "generated_string";
    private CopyToClipboardManager clipManager;
    private Button copyButt;
    private String defaultLen = "50";
    private OPTIONS newOpt;
    private OPTIONS opt;
    private Spinner spinner;
    private String[] spinnerOptions = new String[]{"String without spaces", "String with spaces", NotificationCompat.CATEGORY_EMAIL, "digits"};
    private StringGenerator strGenerator;
    private EditText strLength;
    private TextView txtViewWithStr;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.opt = OPTIONS.STR_NO_SPACE;
        this.newOpt = this.opt;
        this.strGenerator = new StringGenerator();

        setUi();

        if (savedInstanceState != null) {
            try {
                this.strLength.append(savedInstanceState.getString(STATE_LENGTH));
                this.txtViewWithStr.setText(savedInstanceState.getString(STATE_STR_GENERATED));
            } catch (NullPointerException e) {
                this.txtViewWithStr.setText(StringGenerator.errorMessage);
            }
        } else {
            this.strLength.append(this.defaultLen);
            setGenerateString();
        }

        setSpinnerForOptSelection();
        setStrLengthListener();
        setButtonListener();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        String str_len = this.strLength.getText().toString();
        String str_generated = this.txtViewWithStr.getText().toString();
        savedInstanceState.putString(STATE_LENGTH, str_len);
        savedInstanceState.putString(STATE_STR_GENERATED, str_generated);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setUi() {
        this.strLength = (EditText) findViewById(R.id.strLength);
        this.txtViewWithStr = (TextView) findViewById(R.id.text_generated);
        this.copyButt = (Button) findViewById(R.id.generateBut);
        this.clipManager = new CopyToClipboardManager(this);
        this.spinner = (Spinner) findViewById(R.id.select_opt);
    }

    private void setGenerateString() {
        runOnUiThread(new StringGeneratorThread());
    }

    class StringGeneratorThread implements Runnable {

        public void run() {
            txtViewWithStr.setText(strGenerator.generateString(strGenerator.StringToInt(strLength.getText().toString()), MainActivity.this.opt));
        }
    }

    private void setButtonListener() {
        this.copyButt.setOnClickListener(new CopyButListener());
    }

    class CopyButListener implements OnClickListener {
        public void onClick(View v) {
            String text = MainActivity.this.txtViewWithStr.getText().toString();
            if (text.equals("") || text.equals(StringGenerator.errorMessage)) {
                Toast.makeText(MainActivity.this.getApplicationContext(), R.string.toast_fail, Toast.LENGTH_LONG).show();
                return;
            }
            MainActivity.this.clipManager.copyToClipboard(text);
            Toast.makeText(MainActivity.this.getApplicationContext(), R.string.toast_succ, Toast.LENGTH_LONG).show();
        }
    }

    private void setStrLengthListener() {
        this.strLength.addTextChangedListener(new StrLengthListener());
    }

    class StrLengthListener implements TextWatcher {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            MainActivity.this.setGenerateString();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    private void setSpinnerForOptSelection() {
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(this.spinnerOptions));
        this.spinner.setAdapter(SpinnerAdapter);
        this.spinner.setOnItemSelectedListener(new OptionSelectedListener());
    }
    class OptionSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView parent, View view, int position, long id) {
            MainActivity.this.optionSelected(position);
        }
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private void optionSelected(int itemPosition) {
        this.newOpt = OPTIONS.values()[itemPosition];
        if (this.opt != this.newOpt) {
            this.opt = this.newOpt;
            setGenerateString();
        }
    }

}