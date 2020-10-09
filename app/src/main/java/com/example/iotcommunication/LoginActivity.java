package com.example.iotcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etemail;
    EditText etpassword;
    Button loginbtn;
    TextView signuplink;
    ApiInterface apiInterface;
    static final int REQUEST_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail=(EditText)findViewById(R.id.email);
        etpassword=(EditText)findViewById(R.id.password);
        loginbtn=(Button)findViewById(R.id.login_btn);
        signuplink=(TextView)findViewById(R.id.signup_link);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String email = etemail.getText().toString();
                    String password = etpassword.getText().toString();
                    Signin(email,password);
                }
            }
        });

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Signup.class);
                startActivity(intent);
            }
        });
    }


    public void Signin(String email, String pass){

        Call<StringResponse> call1=apiInterface.acclogin(email,pass);
        call1.enqueue(new Callback<StringResponse>() {
            @Override
            public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {
                //Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                String check2=response.body().response;
                if(check2.equals("OK")){

                    Intent i1=new Intent(LoginActivity.this,Dashboard.class);
                    startActivity(i1);
                }
                else{
                    Toast.makeText(getApplicationContext(),"wrong username or password",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StringResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    public boolean validate() {
        boolean valid = true;

        String email = this.etemail.getText().toString();
        String password = this.etpassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"enter valid email address",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(getApplicationContext(),"enter valid password",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}