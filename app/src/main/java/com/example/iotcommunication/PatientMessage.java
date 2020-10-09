package com.example.iotcommunication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMessage extends ListFragment {

    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    List<String> ml;
    ApiInterface apiInterface;
    private Handler listhandler=new Handler();
    public PatientMessage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listmessage, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        apiInterface=ApiClient.getClient().create(ApiInterface.class);
        //ArrayAdapter arrayAdapter=new ArrayAdapter(getActivity(),sim)
        listupdate.run();
    }


    private Runnable listupdate=new Runnable(){
        @Override
        public void run(){
            Call<List<String>> call=apiInterface.downloadmessage();
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    ml=response.body();
                    arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, ml);
                    setListAdapter(arrayAdapter);
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(getActivity(),"could not get data...",Toast.LENGTH_SHORT).show();
                }
            });
            Toast.makeText(getActivity(),"Refreshed",Toast.LENGTH_SHORT).show();
            listhandler.postDelayed(this,10000);
        }
    };
}