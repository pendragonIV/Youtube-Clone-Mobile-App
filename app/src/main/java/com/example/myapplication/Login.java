package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.user.Register;

public class Login extends AppCompatActivity {

    TextView to_register;
    Button btn_login;
    static Database database;

    public static final int REGISTER_CODE = 1001;

    private EditText login_username, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        getSupportActionBar().hide();

        database = new Database(this,"love_u_tube.sqlite",null,1);

        login_password = (EditText) findViewById(R.id.password_login);
        login_username = (EditText) findViewById(R.id.username_login);
        to_register = (TextView)findViewById(R.id.to_register);
        btn_login =(Button) findViewById(R.id.btlogin);

        to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWindow();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLoginIn4();
            }
        });

    }

    private void checkLoginIn4() {
        String userName, userPassw;
        userName = login_username.getText().toString().trim();
        userPassw = login_password.getText().toString().trim();

        if(userName.equals("")){
            Toast.makeText(this, "User name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (userPassw.equals("")) {
            Toast.makeText(this, "Password name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            if(CheckIsDataAlreadyInDBorNot(userName)){
                String passWord = "";
                int userId = 0;
                Cursor dataUser = database.GetData("SELECT * FROM User WHERE Username = '"+userName+"'");
                while (dataUser.moveToNext()){
                    passWord = dataUser.getString(3);
                    userId = dataUser.getInt(0);
                }
                if(userPassw.equals(passWord)){
                    Intent i = new Intent();
                    i.setClass(Login.this,Home.class);
                    i.putExtra("user_id",userId);
                    startActivity(i);
                }else {
                    Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Username is doesn't exist!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    public boolean CheckIsDataAlreadyInDBorNot( String field) {

        String query = "Select * from User where Username = '" + field+"'";

        Cursor dataUser = database.GetData(query);
        if(dataUser.moveToFirst()){
            dataUser.close();
            return true;
        }
        dataUser.close();
        return false;
    }

    private void registerWindow() {
        Intent register = new Intent();
        register.setClass(Login.this, Register.class);
        String userName = login_username.getText().toString();
            register.putExtra("login_username", userName);
            startActivityForResult(register,REGISTER_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REGISTER_CODE){
            String userName = data.getStringExtra("username_register");
            String userPassw = data.getStringExtra("password_register");
            String userMail = data.getStringExtra("email_register");
            login_password.setText(userPassw);
            login_username.setText(userName);
//            user = new user_in4(userFName,userName,userMail, userPassw);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}