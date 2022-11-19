package com.example.techathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.techathon.databinding.ActivityRegisterBinding;

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

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DBHelper db = new DBHelper(this);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String name = binding.nameET.getText().toString().trim();
                String email = binding.emailET.getText().toString().trim();
                String password = binding.passwordET.getText().toString().trim();
                String mobile = binding.mobileET.getText().toString().trim();
                String confirmPassword = binding.confirmPasswordET.getText().toString().trim();
                int userType = Integer.parseInt(binding.usertype.getText().toString().trim());

                if (name.equals("")){
                    binding.nameET.setError("Name Cannot be empty");
                }
                else if (email.equals("")){
                    binding.emailET.setError("Email Cannot be empty");
                }
                else if (mobile.equals("")){
                    binding.mobileET.setError("Mobile Number cannot be empty");
                }
                else if (password.length()<4){
                    binding.passwordET.setError("Password should contain at least 4 characters");
                }
                else if (!password.equals(confirmPassword)){
                    binding.confirmPasswordET.setError("Password mismatch");
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
                        ApiClasses.PostRegister postRegister = new ApiClasses.PostRegister(name,
                                mobile,email, hshtxt, userType);
                        postRegister.execute().get();
                        JSONObject jsonObject = postRegister.getData();
                        if (jsonObject.getInt("message")==1){
                            db.deleteData();
                            db.insertData(email);
                            Intent intent1 = new Intent(RegisterActivity.this, MainActivity2.class);
                            startActivity(intent1);
                        }
                        else if (jsonObject.getInt("message")==2){
                            Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}