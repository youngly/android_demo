package com.youngly.myautofilldemo;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.autofill.AutofillManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private AutofillManager mAutofillManager;
    private static final int REQUEST_CODE_SET_DEFAULT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAutofillManager = getSystemService(AutofillManager.class);

        setupSettingsSwitch(R.id.settingsSetServiceContainer,
                R.id.settingsSetServiceLabel,
                R.id.settingsSetServiceSwitch,
                mAutofillManager.hasEnabledAutofillServices(),
                (compoundButton, serviceSet) -> setService(serviceSet));
    }

    private void setupSettingsSwitch(int containerId, int labelId, int switchId, boolean checked,
                                     CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        ViewGroup container = findViewById(containerId);
        String switchLabel = ((TextView) container.findViewById(labelId)).getText().toString();
        final Switch switchView = container.findViewById(switchId);
        switchView.setContentDescription(switchLabel);
        switchView.setChecked(checked);
        container.setOnClickListener((view) -> switchView.performClick());
        switchView.setOnCheckedChangeListener(checkedChangeListener);
    }

    private void setService(boolean enableService) {
        if (enableService) {
            startEnableService();
        } else {
            disableService();
        }
    }

    private void disableService() {
        if (mAutofillManager != null && mAutofillManager.hasEnabledAutofillServices()) {
            mAutofillManager.disableAutofillServices();
        } else {
        }
    }

    private void startEnableService() {
        if (mAutofillManager != null && !mAutofillManager.hasEnabledAutofillServices()) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE);
            intent.setData(Uri.parse("package:com.example.android.autofill.service"));
            startActivityForResult(intent, REQUEST_CODE_SET_DEFAULT);
        } else {
        }
    }
}
