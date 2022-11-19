package com.example.techathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.techathon.databinding.ActivityForgotPasswordBinding;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DBHelper db = new DBHelper(this);

        binding.change.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String email = binding.emailET.getText().toString().trim();
                String password = binding.passwordET.getText().toString().trim();
                String confirmPassword = binding.confirmPasswordET.getText().toString().trim();


                if (email.equals("")){
                    binding.emailET.setError("Email cannot be empty");
                }
                else if (password.length()<4){
                    binding.passwordET.setError("Password should contain at least 4 characters");
                }
                else if (!password.equals(confirmPassword)){
                    binding.confirmPasswordET.setError("Password Mismatch");
                }
                else{
//                    byte[] salt = new byte[16];
//                    SecureRandom random = new SecureRandom("XgPWJErFHXQxqW5lRHeTDA==".getBytes(StandardCharsets.UTF_8));
//                    random.nextBytes(salt);
//                    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//                    SecretKeyFactory f = null;
//                    try {
//                        f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    }
//                    byte[] hash = new byte[0];
//                    try {
//                        hash = f.generateSecret(spec).getEncoded();
//                    } catch (InvalidKeySpecException e) {
//                        e.printStackTrace();
//                    }
//                    Base64.Encoder enc = Base64.getEncoder();
//                    String hash1 = enc.encodeToString(hash);
                    MessageDigest msgDst = null;
                    try {
                        msgDst = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    byte[] msgArr = msgDst.digest(password.getBytes());
                    BigInteger bi = new BigInteger(1, msgArr);
                    String hshtxt = bi.toString(16);

                    while (hshtxt.length() < 32)
                    {
                        hshtxt = "0" + hshtxt;
                    }
                    try{
                        ApiClasses.PostForgotPass postLogin = new ApiClasses.PostForgotPass(email, hshtxt);
                        postLogin.execute().get();
                        JSONObject jsonObject = postLogin.getData();
                        if (jsonObject.getInt("message")==1){
                            Intent intent1 = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                            startActivity(intent1);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email Does not exsist", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}