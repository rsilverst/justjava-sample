package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee. This is just a test of branching.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    int unitPrice = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getName() {

        EditText customerNameField = (EditText) findViewById(R.id.customer_name);
        return customerNameField.getText().toString();
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.max_order_error) + "!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.min_order_error) + "!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage;
        String customerName = getName();
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
            priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
//        displayMessage(priceMessage);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cashier@justjava.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject) + " " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Email..."));
        }
    }

    /**
     * @param price           the price of the coffee
     * @param hasWhippedCream says whether the coffee should have whipped cream or not
     * @param hasChocolate    says whether the coffee should have chocolate or not
     * @return summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {

        String addWhippedCream = getString(R.string.yes);
        String addChocolate = getString(R.string.no);
        if (hasWhippedCream) {
            addWhippedCream = getString(R.string.yes);
        }
        if (hasChocolate) {
            addChocolate = getString(R.string.yes);
        }
        String summaryMessage = getString(R.string.name) + ": " + name;
        summaryMessage += "\n" + getString(R.string.add_whipped) + "? " + addWhippedCream;
        summaryMessage += "\n" + getString(R.string.add_chocolate) + "? " + addChocolate;
        summaryMessage += "\n" + getString(R.string.quantity_text) + ": " + quantity;
        summaryMessage += "\n" + getString(R.string.total) + ": " + price;
        summaryMessage += "\n" + getString(R.string.thanks);
        return summaryMessage;
    }

    /**
     * @return price
     * <p>
     * This method is to calculate the price according to formula price = unitPrice * quantity
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int totalUnitPrice = unitPrice;
        if (hasWhippedCream) {
            totalUnitPrice += 1;
        }
        if (hasChocolate) {
            totalUnitPrice += 2;
        }

        return quantity * totalUnitPrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
