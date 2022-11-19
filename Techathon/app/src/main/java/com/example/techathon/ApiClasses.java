package com.example.techathon;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClasses {
//    static String url = "http://192.168.1.30:8000/api/";
    static String url = "http://172.16.9.149:8000/api/";


    static class PostLogin extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String email;
        String password;

        PostLogin(String email, String password){
            this.email=email;
            this.password=password;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"login");
                String site_url_json = url+"login";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("email=" + email + "&password=" + password );
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    static class PostRegister extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String password, name, mobile, email;
        int userType;

        PostRegister(String name, String mobile, String email,String password, int userType){
            this.password=password;
            this.name=name;
            this.mobile=mobile;
            this.email=email;
            this.userType=userType;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"register");
                String site_url_json = url+"register";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("password=" + password + "&name="+name
                        + "&email="+email+"&mobile="+mobile+"&userType="+userType);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    public static class PostForgotPass extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        String email, password;

        public PostForgotPass(String email, String password){
            this.email=email;
            this.password=password;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"forgotPass");
                String site_url_json = url+"forgotPass";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("password=" + password + "&email="+email);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    static class PostAddDish extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        int type;
        String dish, email;

        PostAddDish(int type,String dish,String email){
            this.type=type;
            this.dish=dish;
            this.email=email;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"addDish");
                String site_url_json = url+"addDish";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("type=" + type + "&dish=" + dish+"&email="+email);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }



    public static class PostViewDish extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        String email;
        int type;

        public PostViewDish(String email, int type){
            this.email=email;
            this.type=type;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"viewDish");
                String site_url_json = url+"viewDish";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("type=" + type + "&email="+email);
                wr.flush();
                wr.close();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONArray getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONArray(resultJson);
        }
    }




    public static class PostRemoveDish extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        int id;

        public PostRemoveDish(int id){
            this.id=id;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"removeDish");
                String site_url_json = url+"removeDish";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("id=" + id);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }


    static class PostPlaceOrder extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";
        int type;
        String email;

        PostPlaceOrder(int type, String email){
            this.type=type;
            this.email=email;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.e("error", url+"order");
                String site_url_json = url+"order";
                URL url = new URL(site_url_json);


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.connect();

                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
                wr.writeBytes("type=" + type + "&email="+email);
                wr.flush();
                wr.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error", e.toString());
            }
            return resultJson;
        }


        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
        }

        public JSONObject getData() throws Exception{
            Log.e("error","Result "+resultJson);
            return new JSONObject(resultJson);
        }
    }

}