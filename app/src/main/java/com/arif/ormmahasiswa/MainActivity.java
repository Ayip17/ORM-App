package com.arif.ormmahasiswa;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNama, editTextNIM, editTextAlamat, editTextAsalSekolah;
    private Button buttonSave;
    private TextView textViewDisplay;
    private StudentDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = StudentDatabase.getInstance(this);

        editTextNama = findViewById(R.id.editTextNama);
        editTextNIM = findViewById(R.id.editTextNIM);
        editTextAlamat = findViewById(R.id.editTextAlamat);
        editTextAsalSekolah = findViewById(R.id.editTextAsalSekolah);
        buttonSave = findViewById(R.id.buttonSave);
        textViewDisplay = findViewById(R.id.textViewDisplay);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = editTextNama.getText().toString();
                String nim = editTextNIM.getText().toString();
                String alamat = editTextAlamat.getText().toString();
                String asalSekolah = editTextAsalSekolah.getText().toString();

                Student student = new Student();
                student.setNama(nama);
                student.setNim(nim);
                student.setAlamat(alamat);
                student.setAsalSekolah(asalSekolah);

                // Insert the student into the database
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        database.studentDao().insertStudent(student);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayStudents();
                            }
                        });
                    }
                }).start();
            }
        });

        displayStudents();
    }

    private void displayStudents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Student> students = database.studentDao().getAllStudents();
                StringBuilder displayText = new StringBuilder();
                for (Student student : students) {
                    displayText.append("Nama: ").append(student.getNama()).append("\n")
                            .append("NIM: ").append(student.getNim()).append("\n")
                            .append("Alamat: ").append(student.getAlamat()).append("\n")
                            .append("Asal Sekolah: ").append(student.getAsalSekolah()).append("\n")
                            .append("\n\n"); // This adds a blank line between each student's details
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewDisplay.setText(displayText.toString());
                    }
                });
            }
        }).start();
    }
}