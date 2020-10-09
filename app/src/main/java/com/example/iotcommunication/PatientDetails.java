package com.example.iotcommunication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetails extends Fragment {

    TextView name;
    TextView contact;
    TextView email;
    TextView id;
    TextView address;
    ApiInterface apiInterface;
    Patient patient;

    public PatientDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_patient_details, container, false);
        name=(TextView)view.findViewById(R.id.name);
        contact=(TextView)view.findViewById(R.id.contact);
        email=(TextView)view.findViewById(R.id.email);
        id=(TextView)view.findViewById(R.id.id);
        address=(TextView)view.findViewById(R.id.address);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);


        Call<Patient> call1=apiInterface.downloaddetails();
        call1.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

                patient=response.body();
                name.setText(patient.name);
                contact.setText(patient.contact);
                email.setText(patient.email);
                id.setText(patient.id);
                address.setText(patient.address);

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}