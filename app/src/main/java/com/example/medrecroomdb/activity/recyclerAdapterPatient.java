package com.example.medrecroomdb.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medrecroomdb.R;
import com.example.medrecroomdb.model.MedicalRecord;
import com.example.medrecroomdb.model.Patient;

import java.util.ArrayList;


public class recyclerAdapterPatient extends RecyclerView.Adapter<recyclerAdapterPatient.MyViewHolder>{

    private ArrayList<MedicalRecord> medicalRecordList;

    public recyclerAdapterPatient(ArrayList<MedicalRecord> medicalRecordList){
        this.medicalRecordList = medicalRecordList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;

        public MyViewHolder(final View view){
            super(view);
            txtName = view.findViewById(R.id.rvMedicalRecordName);
        }
    }

    @NonNull
    @Override
    public recyclerAdapterPatient.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_items,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapterPatient.MyViewHolder holder, int position) {
        String name = medicalRecordList.get(position).getName();
        holder.txtName.setText(name);
    }

    @Override
    public int getItemCount() {
        return medicalRecordList.size();
    }
}
