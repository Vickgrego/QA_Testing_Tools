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

        opt = OPTIONS.STR_NO_SPACE;
        newOpt = opt;
        strGenerator = new StringGenerator();

        setUi();

        setSpinnerForOptSelection();
        setStrLengthListener();
        setButtonListener();

        if (savedInstanceState != null) {
            try {
                strLength.append(savedInstanceState.getString(STATE_LENGTH));
                txtViewWithStr.setText(savedInstanceState.getString(STATE_STR_GENERATED));
            } catch (NullPointerException e) {
                txtViewWithStr.setText(StringGenerator.errorMessage);
            }
        } else {
            strLength.append(defaultLen);
            setGenerateString();
        }

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        String str_len = strLength.getText().toString();
        String str_generated = txtViewWithStr.getText().toString();
        savedInstanceState.putString(STATE_LENGTH, str_len);
        savedInstanceState.putString(STATE_STR_GENERATED, str_generated);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void setUi() {
        strLength = (EditText) findViewById(R.id.strLength);
        txtViewWithStr = (TextView) findViewById(R.id.text_generated);
        copyButt = (Button) findViewById(R.id.generateBut);
        clipManager = new CopyToClipboardManager(this);
        spinner = (Spinner) findViewById(R.id.select_opt);
    }

    private void setGenerateString() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtViewWithStr.setText(strGenerator.generateString(strGenerator.StringToInt(strLength.getText().toString()), opt));
            }
        });
    }


    private void setButtonListener() {
        this.copyButt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = txtViewWithStr.getText().toString();
                if (text.equals("") || text.equals(StringGenerator.errorMessage)) {
                    Toast.makeText(MainActivity.this.getApplicationContext(), R.string.toast_fail, Toast.LENGTH_LONG).show();
                    return;
                }
                clipManager.copyToClipboard(text);
                Toast.makeText(MainActivity.this.getApplicationContext(), R.string.toast_succ, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void setStrLengthListener() {
        strLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setGenerateString();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void setSpinnerForOptSelection() {
        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, Arrays.asList(this.spinnerOptions));
        spinner.setAdapter(SpinnerAdapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                optionSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void optionSelected(int itemPosition) {
        newOpt = OPTIONS.values()[itemPosition];
        if (opt != newOpt) {
            opt = newOpt;
            setGenerateString();
        }
    }

}