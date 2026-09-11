package com.example.madassigment.home; // Adjust to your package

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.madassigment.R;
import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private EditText etNewTask;
    private TodoAdapter adapter;
    private List<String> taskList = new ArrayList<>();
    private TaskDatabaseHelper dbHelper; // Declare our new database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        etNewTask = findViewById(R.id.et_new_task);
        Button btnAddTask = findViewById(R.id.btn_add_task);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_todo);

        // Initialize the Database Helper
        dbHelper = new TaskDatabaseHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TodoAdapter(taskList);
        recyclerView.setAdapter(adapter);

        // Load tasks from SQLite
        loadTasks();

        btnAddTask.setOnClickListener(v -> {
            String task = etNewTask.getText().toString().trim();
            if (!task.isEmpty()) {
                addTask(task);
                etNewTask.setText(""); // Clear the input field
            } else {
                Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadTasks() {
        // Fetch tasks from SQLite instead of SharedPreferences
        taskList.clear();
        taskList.addAll(dbHelper.getAllTasks());
        adapter.notifyDataSetChanged();
    }

    private void addTask(String task) {
        // 1. Save to SQLite database
        dbHelper.addTask(task);

        // 2. Add to local list and update UI
        taskList.add(task);
        adapter.notifyItemInserted(taskList.size() - 1);
    }

    // --- Inner Adapter Class (Remains exactly the same) ---
    private static class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
        private final List<String> tasks;

        public TodoAdapter(List<String> tasks) {
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
            return new TodoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            holder.tvTaskName.setText(tasks.get(position));
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        static class TodoViewHolder extends RecyclerView.ViewHolder {
            TextView tvTaskName;
            public TodoViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTaskName = itemView.findViewById(R.id.tv_task_name);
            }
        }
    }
}