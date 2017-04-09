package cybulski.tomasz.tomaszcybulskilab1.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.support.v7.widget.ShareActionProvider;
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

    @Override
    protected void onResume(){
        super.onResume();
        setBMICounter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_share:
                shareResult();
                return true;
            case R.id.menu_item_save:
                saveData();
                return true;
            case R.id.menu_item_settings:
                startSettingsActivity();
                return true;
            case R.id.menu_item_author:
                startAuthorActivity();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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

    private void setBMICounter() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean useMetricUnits = sharedPreferences.getBoolean(getString(R.string.use_metric_units_shared_pref_key), true);
        if (useMetricUnits) {
            countBMI = new CountBMIMetric();
        } else {
            countBMI = new CountBMIImperial();
        }
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startAuthorActivity() {
        Intent intent = new Intent(this, AuthorActivity.class);
        startActivity(intent);
    }

    private void saveData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.saved_height_shared_pref_key), editTextHeight.getText().toString());
        editor.putString(getString(R.string.saved_mass_shared_pref_key), editTextMass.getText().toString());
        editor.apply();
        Toast.makeText(this, getString(R.string.saved_data_message), Toast.LENGTH_SHORT).show();
    }

    private void shareResult(){
        if(textViewResultBMI.getText().toString().isEmpty()){
            return;
        }
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(getString(R.string.type_text_plain));
        String shareBody = getString(R.string.share_result_sentence);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.options)));
    }
}
