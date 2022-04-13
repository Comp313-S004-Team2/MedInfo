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

import com.amplifyframework.datastore.generated.model.RecordMetadata;
import com.example.medrecroomdb.activity.ViewDetailedRecord;

import java.util.ArrayList;

public class RVMedRecListAdapter extends RecyclerView.Adapter<RVMedRecListAdapter.MedicalRecordViewHolder>{
    private ArrayList<RecordMetadata> recordMetadatas;
    private Context context;
    SharedPreferences sharedPreferences;

    public RVMedRecListAdapter(Context context, ArrayList<RecordMetadata> recordMetadatas) {
        this.context = context;
        this.recordMetadatas = recordMetadatas;
    }

    @NonNull
    @Override
    public MedicalRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_record_card_view, parent, false);
        return new MedicalRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalRecordViewHolder holder, int position) {
        holder.tvTitle.setText(recordMetadatas.get(position).getTitle());
        holder.tvDescription.setText(recordMetadatas.get(position).getDescription());
        holder.tvUploadDate.setText(recordMetadatas.get(position).getCreatedOn());
        holder.tvMedRecId.setText(recordMetadatas.get(position).getId());
        holder.cvMedicalRecord.setOnClickListener(view -> {
            Intent detailedRecordIntent = new Intent(context, ViewDetailedRecord.class);
            sharedPreferences = context.getSharedPreferences("viewMedicalRecord", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("recordToView", recordMetadatas.get(position).getId());
            editor.commit();
            detailedRecordIntent.putExtra("recordId", recordMetadatas.get(position).getId());
            context.startActivity(detailedRecordIntent);
        });
    }

    @Override
    public int getItemCount() {
        return recordMetadatas.size();
    }

    public class MedicalRecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDescription, tvUploadDate, tvMedRecId;
        CardView cvMedicalRecord;

        public MedicalRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMedRecTitle);
            tvDescription = itemView.findViewById(R.id.tvMedRecDescription);
            tvUploadDate = itemView.findViewById(R.id.tvMedRecUploadDate);
            tvMedRecId = itemView.findViewById(R.id.tvMedRecId);
            cvMedicalRecord = itemView.findViewById(R.id.cvMedicalRecord);
        }
    }
}
