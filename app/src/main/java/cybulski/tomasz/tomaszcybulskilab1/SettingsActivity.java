package cybulski.tomasz.tomaszcybulskilab1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        showCurrentUnit();
    }

    private void showCurrentUnit(){
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean useMetricUnits = sharedPreferences.getBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        if(useMetricUnits){
            ((RadioButton) findViewById(R.id.radio_button_metric_units)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.radio_button_imperial_units)).setChecked(true);
        }
    }

    public void setUnits(View view){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(view.getId() == R.id.radio_button_metric_units){
            editor.putBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        } else {
            editor.putBoolean(getString(R.string.use_metric_units_shared_pref_key), false);
        }
        editor.commit();
    }
}
