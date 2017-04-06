package cybulski.tomasz.tomaszcybulskilab1.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import cybulski.tomasz.tomaszcybulskilab1.R;

public class SettingsActivity extends AppCompatActivity {

    @Bind(R.id.radio_button_metric_units)
    RadioButton radioButtonMetricUnits;
    @Bind(R.id.radio_button_imperial_units)
    RadioButton radioButtonImperialUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        showCurrentUnit();
    }

    private void showCurrentUnit(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useMetricUnits = sharedPreferences.getBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        if(useMetricUnits){
            radioButtonMetricUnits.setChecked(true);
        } else {
            radioButtonImperialUnits.setChecked(true);
        }
    }

    public void setUnits(View view){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        if(view.getId() == radioButtonMetricUnits.getId()){
            editor.putBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        } else {
            editor.putBoolean(getString(R.string.use_metric_units_shared_pref_key), false);
        }
        editor.apply();
    }
}
