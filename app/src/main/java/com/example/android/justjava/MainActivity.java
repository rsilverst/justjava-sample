package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee. This is just a test of branching.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int unitPrice = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public String getName() {

        EditText customerNameField = (EditText) findViewById(R.id.customer_name);
        String customerName = customerNameField.getText().toString();
        return customerName;
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String customerName = getName();
        int price = calculatePrice();
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        String priceMessage = createOrderSummary(customerName, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);
    }

    /**
     *
     * @param price the price of the coffee
     * @param hasWhippedCream says whether the coffee should have whipped cream or not
     * @param hasChocolate says whether the coffee should have chocolate or not
     * @return summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {

        String addWhippedCream = "No";
        String addChocolate = "No";
        if (hasWhippedCream == true) addWhippedCream = "Yes";
        if (hasChocolate == true) addChocolate = "Yes";
        String summaryMessage = "Name: " + name;
        summaryMessage += "\nAdd whipped cream? " + addWhippedCream;
        summaryMessage += "\nAdd chocolate? " + addChocolate;
        summaryMessage += "\nQuantity: " + quantity;
        summaryMessage += "\nTotal: $" + price;
        summaryMessage += "\nThank you!";
        return summaryMessage;
    }
    /**
     *
     * @return price
     *
     * This method is to calculate the price according to formula price = unitPrice * quantity
     *
     */
    private int calculatePrice() {
        return quantity * unitPrice;
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
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
