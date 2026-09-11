package com.example.madassigment.home; // Adjust to your package

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madassigment.R;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Attach the adapter
        recyclerView.setAdapter(new ContactAdapter());
    }

    // --- Inner Adapter Class ---
    private static class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

        // Dummy data for contacts
        private final String[] contactNames = {
                "Alice Smith", "Bob Johnson", "Charlie Brown",
                "Diana Prince", "Evan Wright", "Fiona Gallagher"
        };
        private final String[] contactPhones = {
                "+1 555-0101", "+1 555-0102", "+1 555-0103",
                "+1 555-0104", "+1 555-0105", "+1 555-0106"
        };

        @NonNull
        @Override
        public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
            holder.tvName.setText(contactNames[position]);
            holder.tvPhone.setText(contactPhones[position]);
        }

        @Override
        public int getItemCount() {
            return contactNames.length;
        }

        static class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPhone;

            public ContactViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_contact_name);
                tvPhone = itemView.findViewById(R.id.tv_contact_phone);
            }
        }
    }
}