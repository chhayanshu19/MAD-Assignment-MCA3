package com.example.madassigment.home; // Adjust to your package

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madassigment.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class RemoveProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RemoveAdapter adapter;
    private List<String> productNames = new ArrayList<>();
    private List<String> productPrices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_product);

        recyclerView = findViewById(R.id.recycler_view_remove);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RemoveAdapter();
        recyclerView.setAdapter(adapter);

        loadProducts();
    }

    private void loadProducts() {
        SharedPreferences prefs = getSharedPreferences("ProductPrefs", Context.MODE_PRIVATE);
        String jsonString = prefs.getString("product_list", "[]");

        productNames.clear();
        productPrices.clear();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                productNames.add(obj.getString("name"));
                productPrices.add(obj.getString("price"));
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to permanently delete an item and update SharedPreferences
    private void deleteItem(int position) {
        // 1. Remove from local lists
        productNames.remove(position);
        productPrices.remove(position);

        // 2. Rebuild the JSON Array
        JSONArray newJsonArray = new JSONArray();
        try {
            for (int i = 0; i < productNames.size(); i++) {
                JSONObject obj = new JSONObject();
                obj.put("name", productNames.get(i));
                obj.put("price", productPrices.get(i));
                newJsonArray.put(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. Save the new array back to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("ProductPrefs", Context.MODE_PRIVATE);
        prefs.edit().putString("product_list", newJsonArray.toString()).apply();

        // 4. Update the visual list
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, productNames.size());

        Toast.makeText(this, "Product Deleted", Toast.LENGTH_SHORT).show();
    }

    // --- Inner Adapter Class ---
    private class RemoveAdapter extends RecyclerView.Adapter<RemoveAdapter.RemoveViewHolder> {

        @NonNull
        @Override
        public RemoveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_delete, parent, false);
            return new RemoveViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RemoveViewHolder holder, int position) {
            holder.tvName.setText(productNames.get(position));
            holder.tvPrice.setText(productPrices.get(position));

            // Attach the click listener to the delete button
            holder.btnDelete.setOnClickListener(v -> {
                // Get the current position inside the click listener to avoid index errors
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    deleteItem(adapterPosition);
                }
            });
        }

        @Override
        public int getItemCount() {
            return productNames.size();
        }

        class RemoveViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPrice;
            Button btnDelete;

            public RemoveViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_product_name_del);
                tvPrice = itemView.findViewById(R.id.tv_product_price_del);
                btnDelete = itemView.findViewById(R.id.btn_delete_item);
            }
        }
    }
}