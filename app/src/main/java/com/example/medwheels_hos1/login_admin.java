package com.example.medwheels_hos1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_admin extends AppCompatActivity {
    Button login;
    EditText email,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_admin);
        login=findViewById(R.id.klop);
        email=findViewById(R.id.loginemail);
        pass=findViewById(R.id.loginpass);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail=email.getText().toString().trim(),pwd=pass.getText().toString().trim();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://medwheels-4b07d-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference reference = database.getReference("Admin");

                HelperClassAdmin helperClass = new HelperClassAdmin(mail, pwd);
                reference.child(mail.replace(".", ",")).setValue(helperClass);
                Intent intent=new Intent(login_admin.this, patRecyclerView.class);
                startActivity((intent));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}