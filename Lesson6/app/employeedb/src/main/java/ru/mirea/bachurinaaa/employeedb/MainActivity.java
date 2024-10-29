package ru.mirea.bachurinaaa.employeedb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeDB";

    private EditText nameEditText;
    private EditText salaryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        salaryEditText = findViewById(R.id.salaryEditText);
        Button addButton = findViewById(R.id.addButton);
        Button getAllButton = findViewById(R.id.getAllButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                int salary = Integer.parseInt(salaryEditText.getText().toString());
                addEmployee(name, salary);
            }
        });

        getAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllEmployees();
            }
        });
    }

    private void addEmployee(String name, int salary) {
        Employee employee = new Employee(name, salary);
        App.getInstance().getDatabase().employeeDao().insert(employee);
        Log.d(TAG, "Added: " + employee.name + " with salary: " + employee.salary);
    }

    private void getAllEmployees() {
        List<Employee> employees = App.getInstance().getDatabase().employeeDao().getAll();
        for (Employee emp : employees) {
            Log.d(TAG, "Employee: " + emp.name + ", Salary: " + emp.salary);
        }
    }
}