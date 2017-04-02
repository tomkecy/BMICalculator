package cybulski.tomasz.tomaszcybulskilab1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    UnitOfMeasure unit = UnitOfMeasure.METRIC;
    private ICountBMI countBMI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBMICounter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.unit_selection_menu, menu);
        return true;
    }

    public void countBMI(View view) {
        try {
            float height = Float.parseFloat(((EditText) findViewById(R.id.edit_view_height)).getText().toString());
            float mass = Float.parseFloat(((EditText) findViewById(R.id.edit_text_mass)).getText().toString());
            float countedBMI = countBMI.countBMI(mass, height);
            TextView textViewBMIResult = (TextView) findViewById(R.id.text_view_result_bmi);
            textViewBMIResult.setText(String.valueOf(countedBMI));
            setTextViewResultMessage(countedBMI, textViewBMIResult);

        } catch (InvalidMassOrHeightException e){
            Toast.makeText(getApplicationContext(), R.string.incorrect_input_message,
                    Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), R.string.empty_input_message,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setTextViewResultMessage(float countedBMI, TextView textViewBMIResult) {
        TextView textViewMessage = (TextView) findViewById(R.id.text_view_result_message);
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
        textViewMessage.setText(message);
        textViewMessage.setTextColor(textMessageColor);
        textViewBMIResult.setTextColor(textMessageColor);
    }

    public void setUnits(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.use_imperial_units:
                unit = UnitOfMeasure.IMPERIAL;
                menuItem.setChecked(true);
                break;
            case R.id.use_metric_units:
                unit = UnitOfMeasure.METRIC;
                menuItem.setChecked(true);
                break;
            default:
                break;
        }
        setBMICounter();
    }

    private void setBMICounter(){
        switch (unit){
            case IMPERIAL:
                countBMI = new CountBMIImperial();
                break;
            case METRIC:
                countBMI = new CountBMIMetric();
                break;
        }
    }
}
