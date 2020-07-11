package com.example.icp9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int COFFEE_PRICE = 5;
    final int WHIPPED_CREAM_PRICE = 1;
    final int CHOCOLATE_PRICE = 2;
    final int CARAMEL_PRICE=2;
    final int SKIM_MILK_PRICE=1;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * When order button is clicked this method is called
     */

    public void submitOrder(View view) {

        // input is taken from user
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // checkbox to check if whipped cream is selected by user
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();

        // to check chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();

        // to check caramel is selected
        CheckBox caramel = (CheckBox) findViewById(R.id.caramel_checked);
        boolean hasCaramel = caramel.isChecked();

        //  to check skim milk is selected
        CheckBox skimMilk = (CheckBox) findViewById(R.id.skim_milk_checked);
        boolean hasSkimMilk = skimMilk.isChecked();

        // total price is calculated and stored
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate,hasCaramel,hasSkimMilk);

        // summary is stored here
        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate,hasCaramel,hasSkimMilk, totalPrice);
        Intent redirect = new Intent(MainActivity.this, SummaryPage.class);
        redirect.putExtra("summary", orderSummaryMessage);
        startActivity(redirect);

    }

    public void sendEmail(View view) {
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();

        CheckBox caramel = (CheckBox) findViewById(R.id.caramel_checked);
        boolean hasCaramel = caramel.isChecked();

        CheckBox skimMilk = (CheckBox) findViewById(R.id.skim_milk_checked);
        boolean hasSkimMilk = skimMilk.isChecked();

        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate,hasCaramel,hasSkimMilk);

        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate,hasCaramel,hasSkimMilk, totalPrice);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ksowmyareddy14@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order");
        i.putExtra(Intent.EXTRA_TEXT   , orderSummaryMessage);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasWhippedCream, boolean hasChocolate,boolean hasCaramel,boolean hasSkimMilk, float price) {
        String orderSummaryMessage = "Name: "+userInputName + "\n" +
                "Add Whipped Cream? "+boolToString(hasWhippedCream) + "\n" +
                "Add Chocolate? "+boolToString(hasChocolate) + "\n" +
                "Add Caramel? "+boolToString(hasCaramel) + "\n" +
                "Add Skim Milk? "+boolToString(hasSkimMilk) + "\n" +
                "Quantity: "+quantity + "\n" +
                "Total: $ "+ price + "\n" +
                "Thank You!";
        return orderSummaryMessage;
    }

    /**
     * total price is calculated in this method and returns total price
     */
    private float calculatePrice(boolean hasWhippedCream, boolean hasChocolate,boolean hasCaramel,boolean hasSkimMilk) {
        int basePrice = COFFEE_PRICE;
        if (hasWhippedCream) {
            basePrice += WHIPPED_CREAM_PRICE;
        }
        if (hasChocolate) {
            basePrice += CHOCOLATE_PRICE;
        }
        if (hasCaramel) {
            basePrice += CARAMEL_PRICE;
        }
        if (hasSkimMilk) {
            basePrice += SKIM_MILK_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * incrementing the quantity
     **/

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred cups of coffee");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * decrementing the quantity
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one cup of coffee");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_coffee);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}