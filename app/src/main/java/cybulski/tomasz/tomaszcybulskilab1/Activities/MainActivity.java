package cybulski.tomasz.tomaszcybulskilab1.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cybulski.tomasz.tomaszcybulskilab1.Concrete.CountBMIImperial;
import cybulski.tomasz.tomaszcybulskilab1.Concrete.CountBMIMetric;
import cybulski.tomasz.tomaszcybulskilab1.Abstract.ICountBMI;
import cybulski.tomasz.tomaszcybulskilab1.Entities.InvalidMassOrHeightException;
import cybulski.tomasz.tomaszcybulskilab1.R;

import butterknife.Bind;
/*
*TODO
* przyciski:
* - save
* - share - wyskakuje lista aplikacji
* - author - opis + zdjęcie
* ma zachować wyliczone dane po obróceniu i don't keep activity
*
*
*/

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.edit_text_height) EditText editTextHeight;
    @Bind(R.id.edit_text_mass) EditText editTextMass;
    @Bind(R.id.text_view_result_message) TextView textViewResultMessage;
    @Bind(R.id.text_view_result_bmi) TextView textViewResultBMI;
    @Bind(R.id.relative_layout_main) RelativeLayout relativeLayoutMain;

    private ICountBMI countBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBMICounter();

        if(savedInstanceState == null){
            restoreSavedData();
        }
    }

    private void restoreSavedData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedHeightText = sharedPreferences.getString(
                getString(R.string.saved_height_shared_pref_key), "");
        String savedMassText = sharedPreferences.getString(
                getString(R.string.saved_mass_shared_pref_key), "");

        editTextHeight.setText(savedHeightText);
        editTextMass.setText(savedMassText);
        if(!savedHeightText.isEmpty() && !savedMassText.isEmpty()){
            countBMI();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        setBMICounter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.calculator_menu, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        //mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString(getString(R.string.mass_saved_instance_state_key),
                    editTextMass.getText().toString());
            outState.putString(getString(R.string.height_saved_instance_state_key),
                    editTextHeight.getText().toString());
            outState.putString(getString(R.string.result_bmi_saved_instance_state_key),
                    textViewResultBMI.getText().toString());
            outState.putString(getString(R.string.message_saved_instance_state_key),
                    textViewResultMessage.getText().toString());
            outState.putInt(getString(R.string.color_saved_instance_state_key),
                    textViewResultBMI.getCurrentTextColor());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextMass.setText(savedInstanceState.getString(getString(
                R.string.mass_saved_instance_state_key)));
        editTextHeight.setText(savedInstanceState.getString(getString(
                R.string.height_saved_instance_state_key)));
        textViewResultBMI.setText(savedInstanceState.getString(getString(
                R.string.result_bmi_saved_instance_state_key)));
        textViewResultMessage.setText(savedInstanceState.getString(getString(
                R.string.message_saved_instance_state_key)));
        int colorId = savedInstanceState.getInt(getString(R.string.color_saved_instance_state_key));
        textViewResultBMI.setTextColor(colorId);
        textViewResultMessage.setTextColor(colorId);
    }

    @OnClick(R.id.button_count_bmi)
    public void countBMI() {
        try {
            float height = Float.parseFloat(editTextHeight.getText().toString());
            float mass = Float.parseFloat(editTextMass.getText().toString());
            float countedBMI = countBMI.countBMI(mass, height);

            textViewResultBMI.setText(String.format(Locale.getDefault(), "%.2f", countedBMI));
            setTextViewResultMessage(countedBMI);

        } catch (InvalidMassOrHeightException e){
            Toast.makeText(getApplicationContext(), R.string.incorrect_input_message,
                    Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), R.string.empty_input_message,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setTextViewResultMessage(float countedBMI) {
        String message;
        int textMessageColor;
        if(countedBMI<18.5) {
            message = getString(R.string.bmi_result_underweight);
            textMessageColor = Color.RED;
        } else if(countedBMI <= 25){
            message = getString(R.string.bmi_result_normal_range);
            textMessageColor = Color.GREEN;
        } else if (countedBMI <= 30){
            message = getString(R.string.bmi_result_overweight);
            textMessageColor = Color.YELLOW;
        } else {
            message = getString(R.string.bmi_result_obese);
            textMessageColor = Color.RED;
        }
        textViewResultMessage.setText(message);
        textViewResultMessage.setTextColor(textMessageColor);
        textViewResultBMI.setTextColor(textMessageColor);
    }

    private void setBMICounter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useMetricUnits = sharedPreferences.getBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        if (useMetricUnits) {
            countBMI = new CountBMIMetric();
        } else {
            countBMI = new CountBMIImperial();
        }
    }

    public void startSettingsActivity(MenuItem menuItem){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void startAuthorActivity(MenuItem menuItem) {
        Intent intent = new Intent(this, AuthorActivity.class);
        startActivity(intent);
    }

    public void saveData(MenuItem menuItem){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.saved_height_shared_pref_key), editTextHeight.getText().toString());
        editor.putString(getString(R.string.saved_mass_shared_pref_key), editTextMass.getText().toString());
        editor.apply();
        Toast.makeText(this, getString(R.string.saved_data_message), Toast.LENGTH_SHORT).show();
    }
    private ShareActionProvider mShareActionProvider;

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
