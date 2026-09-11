package com.example.madassigment.home; // Adjust package

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.madassigment.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        EditText etName = findViewById(R.id.et_product_name);
        EditText etPrice = findViewById(R.id.et_product_price);
        Button btnSave = findViewById(R.id.btn_save_product);

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String price = etPrice.getText().toString().trim();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            saveProductToPreferences(name, price);
            finish(); // Close activity and go back to Home
        });
    }

    private void saveProductToPreferences(String name, String price) {
        SharedPreferences prefs = getSharedPreferences("ProductPrefs", Context.MODE_PRIVATE);

        // 1. Get the existing list of products (Default is an empty JSON array "[]")
        String existingJson = prefs.getString("product_list", "[]");

        try {
            // 2. Parse it into a JSONArray
            JSONArray jsonArray = new JSONArray(existingJson);

            // 3. Create a new JSONObject for our new product
            JSONObject newProduct = new JSONObject();
            newProduct.put("name", name);
            newProduct.put("price", price);

            // 4. Add the new product to the array and save it back
            jsonArray.put(newProduct);
            prefs.edit().putString("product_list", jsonArray.toString()).apply();

            Toast.makeText(this, "Product Saved!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving product", Toast.LENGTH_SHORT).show();
        }
    }
}