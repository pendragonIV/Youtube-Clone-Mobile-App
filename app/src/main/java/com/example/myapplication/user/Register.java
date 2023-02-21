package com.example.myapplication.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database;
import com.example.myapplication.Login;
import com.example.myapplication.R;

public class Register extends AppCompatActivity {

    public static final int LOGIN_CODE = 1000;
    private EditText email, userName, password, reEnterPassw;
    private Button register_btn;
    private TextView backLogin;
    static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getSupportActionBar().hide();

        database = new Database(this,"love_u_tube.sqlite",null,1);

        email = (EditText) findViewById(R.id.email_register);
        userName = (EditText) findViewById(R.id.username_register);
        password = (EditText) findViewById(R.id.password_register);
        reEnterPassw = (EditText) findViewById(R.id.re_password);

        register_btn = (Button) findViewById(R.id.btregis);

        backLogin = (TextView) findViewById(R.id.backtologin);

        //tao intent va get ra username tu login
        Intent i = getIntent();
        String usn = i.getStringExtra("login_username").toString();
        userName.setText(usn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitIn4();
            }
        });
        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Register.this,Login.class);
                startActivity(i);
            }
        });
    }

    private void submitIn4(){
        String sm_email = email.getText().toString().trim();
        String sm_userName = userName.getText().toString().trim();
        String sm_password = password.getText().toString().trim();
        String sm_repass  = reEnterPassw.getText().toString().trim();

        if(sm_email.equals("") || sm_userName.equals("") || sm_password.equals("") ||sm_repass.equals("")){
            Toast.makeText(this, "Have to fill all field!", Toast.LENGTH_SHORT).show();
            return;
        } else if (CheckIsDataAlreadyInDBorNot(sm_userName)) {
            Toast.makeText(this, "User name already exist!", Toast.LENGTH_SHORT).show();
            return;
        } else{
            if(!sm_repass.equals(sm_password)){
                Toast.makeText(Register.this,"Re-entered password is incorrect!",Toast.LENGTH_LONG).show();
                return;
            }else{
                Intent login_scr = new Intent();
                login_scr.setClass(Register.this, Login.class);
                login_scr.putExtra("email_register",sm_email);
                login_scr.putExtra("username_register",sm_userName);
                login_scr.putExtra("password_register",sm_password);
                setResult(Login.REGISTER_CODE,login_scr);

                database.QueryData("INSERT INTO User VALUES(null, '"+sm_userName+"', '"+sm_email+"', '"+sm_password+"')");

                finish();
            }
        }
    }

    //check if username already exist

    public boolean CheckIsDataAlreadyInDBorNot( String username) {

        String query = "Select * from User where Username = '" + username+"'";

        Cursor dataUser = database.GetData(query);
        if(dataUser.moveToFirst()){
            dataUser.close();
            return true;
        }
        dataUser.close();
        return false;
    }

}