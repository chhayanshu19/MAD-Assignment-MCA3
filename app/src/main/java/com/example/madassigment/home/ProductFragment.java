package com.example.madassigment.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madassigment.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    private ProductAdapter adapter;
    private List<String> productNames = new ArrayList<>();
    private List<String> productPrices = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new ProductAdapter(productNames, productPrices);
        recyclerView.setAdapter(adapter);
        return view;
    }

    // Runs every time the user views this tab (even after returning from AddProductActivity)
    @Override
    public void onResume() {
        super.onResume();
        loadProductsFromPreferences();
    }

    private void loadProductsFromPreferences() {
        SharedPreferences prefs = requireActivity().getSharedPreferences("ProductPrefs", Context.MODE_PRIVATE);
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
            adapter.notifyDataSetChanged(); // Tell RecyclerView to refresh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Dynamic Adapter ---
    private static class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
        private final List<String> names;
        private final List<String> prices;

        public ProductAdapter(List<String> names, List<String> prices) {
            this.names = names;
            this.prices = prices;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.tvName.setText(names.get(position));
            holder.tvPrice.setText(prices.get(position));
        }

        @Override
        public int getItemCount() {
            return names.size();
        }

        static class ProductViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPrice;
            public ProductViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_product_name);
                tvPrice = itemView.findViewById(R.id.tv_product_price);
            }
        }
    }
}