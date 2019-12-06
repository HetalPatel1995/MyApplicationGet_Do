package com.example.get_doapplication.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.get_doapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpGenerateActivity extends AppCompatActivity {

    private String verificationid;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText pinCode1,pinCode2,pinCode3,pincode4,pinCode5,pinCode6;
    Button BtnOtpGenerate;
    LinearLayout PinCodeEditLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_generate);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        PinCodeEditLayout=findViewById(R.id.PinCodeEditLayout);
//        editText = findViewById(R.id.editTextCode);
        pinCode1 = findViewById(R.id.PinCode1);
        pinCode2 = findViewById(R.id.PinCode2);
        pinCode3 = findViewById(R.id.PinCode3);
        pincode4 = findViewById(R.id.PinCode4);
        pinCode5 = findViewById(R.id.PinCode5);
        pinCode6 = findViewById(R.id.PinCode6);
        BtnOtpGenerate=findViewById(R.id.OtpGenerate);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationCode(phonenumber);

        BtnOtpGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code1 = pinCode1.getText().toString().trim();
                String code2 = pinCode2.getText().toString().trim();
                String code3 = pinCode3.getText().toString().trim();
                String code4 = pincode4.getText().toString().trim();
                String code5 = pinCode5.getText().toString().trim();
                String code6 = pinCode6.getText().toString().trim();
//                String code = editText.getText().toString().trim();
                String code=code1 + code2 + code3 + code4 + code5 + code6;
                if ((code1.isEmpty() ||code2.isEmpty()||code3.isEmpty()||code4.isEmpty()||code5.isEmpty()||code6.isEmpty() || code.length() < 6)){

//                    editText.setError("Enter code...");
                    pinCode1.setError("enter code..");
                    pinCode2.setError("enter code..");
                    pinCode3.setError("enter code..");
                    pincode4.setError("enter code..");
                    pinCode5.setError("enter code..");
                    pinCode6.setError("enter code..");

                    pinCode1.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
    }
    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(OtpGenerateActivity.this, SetPinGenerate.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            startActivity(intent);

                        } else {
                            Toast.makeText(OtpGenerateActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void sendVerificationCode(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
                PinCodeEditLayout.setVisibility(View.INVISIBLE);
//                editText.setText(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpGenerateActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
}
