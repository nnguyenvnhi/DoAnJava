package com.example.javaprojectg5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.javaprojectg5.DAO.ThuThuDAO;


public class LoginActivity extends AppCompatActivity {
    EditText edUserName,edPassword;
    Button btnLogin,btnCancel;
    CheckBox chkRememberPass;
    String strUser,strPass;
    ThuThuDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edUserName=findViewById(R.id.edUserName);
        edPassword=findViewById(R.id.edPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnCancel=findViewById(R.id.btnCancel);
        chkRememberPass=findViewById(R.id.chkRememberPass);
        dao=new ThuThuDAO(this);

        SharedPreferences pref =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        String user =pref.getString("USERNAME","");
        String pass =pref.getString("PASSWORD","");
        Boolean rem =pref.getBoolean("REMEMBER",false);

        edUserName.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassword.setText("");
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    public void rememberUser(String u,String p,boolean status){
        SharedPreferences pref =getSharedPreferences("USER_FILE",MODE_PRIVATE);
        SharedPreferences.Editor edit =pref.edit();
        if (!status){
            edit.clear();
        }else {
            edit.putString("USERNAME",u);
            edit.putString("PASSWORD",p);
            edit.putBoolean("REMEMBER",status);
        }
        edit.commit();
    }
    public void checkLogin(){
        strUser=edUserName.getText().toString();
        strPass=edPassword.getText().toString();
        if (strUser.isEmpty()||strPass.isEmpty()){
            Toast.makeText(getApplicationContext(),"khong duoc de trong",Toast.LENGTH_SHORT).show();
        }else {
            if (dao.checkLogin(strUser,strPass)>0){
                Toast.makeText(getApplicationContext(),"Login thanh cong",Toast.LENGTH_SHORT).show();
                rememberUser(strUser,strPass,chkRememberPass.isChecked());
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("user",strUser);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"nhap sai",Toast.LENGTH_SHORT).show();
            }
        }
    }

}