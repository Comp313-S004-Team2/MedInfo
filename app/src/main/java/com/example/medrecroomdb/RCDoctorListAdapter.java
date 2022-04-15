package com.example.medrecroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.User;
import com.example.medrecroomdb.activity.SetAppointment;

import java.util.ArrayList;

public class RCDoctorListAdapter extends RecyclerView.Adapter<RCDoctorListAdapter.DoctorListViewHolder> {
    private ArrayList<User> doctors;
    private Context context;
    SharedPreferences sharedPreferences;

    public RCDoctorListAdapter(ArrayList<User> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_card_view, parent, false);
        return new DoctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListViewHolder holder, int position) {
        holder.doctorName.setText("Dr. " + doctors.get(position).getFirstName() + " " + doctors.get(position).getLastName());
        holder.licenseNumber.setText(doctors.get(position).getIdNumber());
        holder.email.setText(doctors.get(position).getEmail());
        holder.phone.setText(doctors.get(position).getPhoneNumber());
        holder.doctorCardView.setOnClickListener(v -> {
            Intent setAppointmentIntent = new Intent(context, SetAppointment.class);
            sharedPreferences = context.getSharedPreferences("doctorList", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("doctorForAppointment", doctors.get(position).getIdNumber());
            editor.commit();
            context.startActivity(setAppointmentIntent);
            ((Activity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public  class DoctorListViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName, licenseNumber, email, phone;
        CardView doctorCardView;

        public DoctorListViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorName = itemView.findViewById(R.id.tvDocListName);
            licenseNumber = itemView.findViewById(R.id.tvDocListLicense);
            email = itemView.findViewById(R.id.tvDocListEmail);
            phone = itemView.findViewById(R.id.tvDocListPhone);
            doctorCardView = itemView.findViewById(R.id.cvDoctor);
        }
    }
}
