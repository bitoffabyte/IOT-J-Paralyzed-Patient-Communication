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

public class Signup extends AppCompatActivity {
    EditText supname;
    EditText supemail;
    EditText suppassword;
    Button signupbtn;
    TextView loginlink;
    ApiInterface apiInterface;
    static final int REQUEST_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        supname=(EditText)findViewById(R.id.supname);
        supemail=(EditText)findViewById(R.id.supemail);
        suppassword=(EditText)findViewById(R.id.suppassword);
        signupbtn=(Button)findViewById(R.id.sup_btn);
        loginlink=(TextView)findViewById(R.id.login_link);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);

        signupbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(validate()){
                    String name=supname.getText().toString();
                    String email=supemail.getText().toString();
                    String password=suppassword.getText().toString();
                    Signup(name,email,password);
                }
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent2=new Intent(Signup.this,LoginActivity.class);
                startActivity(intent2);

            }
        });
    }

    public void Signup(String name, String email, String pass){

        Call<StringResponse> call1=apiInterface.accsignup(name,email,pass);
        call1.enqueue(new Callback<StringResponse>() {
            @Override
            public void onResponse(Call<StringResponse> call, Response<StringResponse> response) {

                String check2=response.body().response;
                supname.setText("");
                supemail.setText("");
                suppassword.setText("");
                Toast.makeText(getApplicationContext(),check2,Toast.LENGTH_SHORT).show();
                Intent i1=new Intent(Signup.this,Dashboard.class);
                startActivity(i1);
            }

            @Override
            public void onFailure(Call<StringResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String name = supname.getText().toString();
        String email = supemail.getText().toString();
        String password = suppassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            Toast.makeText(getApplicationContext(),"Enter valid name",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"Enter valid email",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toast.makeText(getApplicationContext(),"Enter valid password",Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }
}