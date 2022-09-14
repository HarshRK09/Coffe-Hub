package com.harshkataria.justjava;
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void Incriment(View view) {
        if(quantity >= 100){
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);
    }

    public void Decrement(View view) {
        if(quantity <= 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean choco = chocolate.isChecked();

        EditText Name = (EditText) findViewById(R.id.enter_name);
        String name = Name.getText().toString();

        int price = calculatePrice(hasWhippedCream, choco);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, choco);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int priceOfCoffee = 5;

        if(addWhippedCream)
            priceOfCoffee += 1;

        if(addChocolate)
            priceOfCoffee+=2;

        return quantity * priceOfCoffee;
    }

    /**
     * Create Summary of the order
     * @param name
     * @param price of the order
     * @param whippedCream
     * @param chocolate
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean whippedCream, boolean chocolate){
        String priceMessage = "ORDER SUMMARY" +
                "\nName: " + name +
                "\nAdd whipped cream? " + whippedCream +
                "\nAdd chocolate? " + chocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price +
                "\nThankyou!";
        return priceMessage;
    }
}