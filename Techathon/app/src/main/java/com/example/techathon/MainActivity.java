package com.example.techathon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.techathon.databinding.ActivityMainBinding;

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

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String email = binding.userET.getText().toString().trim();
                String password = binding.passwordET.getText().toString().trim();

//                byte[] salt = new byte[16];
//                SecureRandom random = new SecureRandom("XgPWJErFHXQxqW5lRHeTDA==".getBytes(StandardCharsets.UTF_8));
//                random.nextBytes(salt);
//                KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
//                SecretKeyFactory f = null;
//                try {
//                    f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
//                byte[] hash = new byte[0];
//                try {
//                    hash = f.generateSecret(spec).getEncoded();
//                } catch (InvalidKeySpecException e) {
//                    e.printStackTrace();
//                }
//                Base64.Encoder enc = Base64.getEncoder();
//                String hash1 = enc.encodeToString(hash);

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
                    ApiClasses.PostLogin postLogin = new ApiClasses.PostLogin(email, hshtxt);
                    postLogin.execute().get();
                    JSONObject jsonObject = postLogin.getData();
                    if (jsonObject.getInt("message")==1){
                        db.deleteData();
                        db.insertData(email);
                        if (jsonObject.getInt("userType")==1) {
                            Intent intent1 = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent1);
                        }
                    }
                    else{
                        binding.userET.setError("Invalid Credentials");
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}