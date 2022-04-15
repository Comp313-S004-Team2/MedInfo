package com.example.medrecroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Appointment;
import com.amplifyframework.datastore.generated.model.Note;
import com.example.medrecroomdb.activity.Empty;
import com.example.medrecroomdb.activity.RescheduleAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RVAppointmentListAdapter extends RecyclerView.Adapter<RVAppointmentListAdapter.AppointmentListViewHolder>{

    private Context context;
    private ArrayList<Appointment> appointments;
    SimpleDateFormat sdf;
    Calendar getAppointmentDate;
    ArrayList<String> weeks;
    ArrayList<String> months;
    List<String> timeSlot;
    private String userRole;

    public RVAppointmentListAdapter(Context context, ArrayList<Appointment> appointments, String userRole) {
        this.context = context;
        this.appointments = appointments;
        this.userRole = userRole;
        getAppointmentDate = Calendar.getInstance();
        sdf = new SimpleDateFormat("E, MMMM dd yyyy");
        weeks = new ArrayList<String>();
        months = new ArrayList<String>();
        timeSlot = Arrays.asList(context.getResources().getStringArray(R.array.timeSelection));
        weeks.add("SUN");
        weeks.add("MON");
        weeks.add("TUE");
        weeks.add("WED");
        weeks.add("THU");
        weeks.add("FRI");
        weeks.add("SAT");
        months.add("JAN");
        months.add("FEB");
        months.add("MAR");
        months.add("APR");
        months.add("MAY");
        months.add("JUN");
        months.add("JUL");
        months.add("AUG");
        months.add("SEP");
        months.add("OCT");
        months.add("NOV");
        months.add("DEC");
    }

    @NonNull
    @Override
    public AppointmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_card_view, parent, false);
        return new AppointmentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentListViewHolder holder, int position) {

        getAppointmentDate.set(appointments.get(position).getYear(), appointments.get(position).getMonth(), appointments.get(position).getDay());
        holder.tvSchedule.setText(weeks.get(getAppointmentDate.get(Calendar.DAY_OF_WEEK) - 1)  + " "
                + months.get(getAppointmentDate.get(Calendar.MONTH)) + " "
                + getAppointmentDate.get(Calendar.DATE) + " "
                + getAppointmentDate.get(Calendar.YEAR) + " "
                + timeSlot.get(appointments.get(position).getTimeSlot()));
        holder.tvDoctorName.setText(appointments.get(position).getDoctorName());

        if(userRole.matches("Doctor")){
            holder.btnReschedule.setVisibility(View.INVISIBLE);
            holder.tvLabel.setText("Patient's Name:");
            holder.tvDoctorName.setText(appointments.get(position).getPatientName());
        }

        holder.btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("appointmentList", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("appointmentToReschedule", appointments.get(holder.getAdapterPosition()).getId());
                editor.commit();
                Intent rescheduleAppointmentIntent = new Intent(context, RescheduleAppointment.class);
                context.startActivity(rescheduleAppointmentIntent);
            }
        });
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Cancel").setMessage("Are you sure you want to cancel this appointment?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Amplify.DataStore.query(
                                        Appointment.class, Where.matches(Appointment.ID.eq(appointments.get(holder.getAdapterPosition()).getId())),
                                        items -> {
                                            if (items.hasNext()) {
                                                Appointment item = items.next();
                                                Amplify.DataStore.delete(item,
                                                        deleted -> Log.i("Appointment", "Deleted item."),
                                                        failure -> Log.e("Appointment", "Delete failed.", failure)
                                                );
                                                Log.i("Amplify", "Id " + item.getId());
                                                Intent newIntent = new Intent(context, Empty.class);
                                                context.startActivity(newIntent);
                                            }
                                        },
                                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                                );
                    }
                }).show();
                alertDialog.create();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class AppointmentListViewHolder extends RecyclerView.ViewHolder {

        TextView tvDoctorName, tvSchedule, tvLabel;
        CardView cvAppointment;
        Button btnReschedule, btnCancel;

        public AppointmentListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDoctorName = itemView.findViewById(R.id.tvAppListDoctorName);
            tvSchedule = itemView.findViewById(R.id.tvAppListSchedule);
            cvAppointment = itemView.findViewById(R.id.cvAppointment);
            btnCancel = itemView.findViewById(R.id.btnAppListCancel);
            btnReschedule = itemView.findViewById(R.id.btnAppListReschedule);
            tvLabel = itemView.findViewById(R.id.tvAppListLabel);
        }
    }
}
