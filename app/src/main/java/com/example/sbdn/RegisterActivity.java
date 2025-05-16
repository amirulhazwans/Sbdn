package com.example.sbdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private Button buttonRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        inputEmail = findViewById(R.id.input_userEmail);
        inputPassword = findViewById(R.id.input_password);
        inputConfirmPassword = findViewById(R.id.input_password_confirm);
        buttonRegister = findViewById(R.id.button_register);

        // Register button click listener
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String confirmPassword = inputConfirmPassword.getText().toString().trim();

                // Input validation
                if (email.isEmpty()) {
                    inputEmail.setError("Email is required");
                    return;
                }
                if (password.isEmpty()) {
                    inputPassword.setError("Password is required");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    inputConfirmPassword.setError("Confirm Password is required");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    inputConfirmPassword.setError("Passwords do not match");
                    return;
                }

                // Register user with Firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Show dialog and redirect to login
                                    new AlertDialog.Builder(RegisterActivity.this)
                                            .setTitle("Account Created")
                                            .setMessage("Your account has been successfully created.")
                                            .setCancelable(false)
                                            .setPositiveButton("Back to Login", (dialog, which) -> {
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            })
                                            .show();
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        inputPassword.setError("Weak password. Minimum 6 characters.");
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        inputEmail.setError("Invalid email format.");
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        inputEmail.setError("Email already in use.");
                                    } catch (Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });
    }
}
