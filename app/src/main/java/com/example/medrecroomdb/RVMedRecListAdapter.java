package com.example.medrecroomdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.RecordMetadata;

import java.util.ArrayList;

public class RVMedRecListAdapter extends RecyclerView.Adapter<RVMedRecListAdapter.MedicalRecordViewHolder>{
    private ArrayList<RecordMetadata> recordMetadatas;
    private String numberOfNotes;
    private Context context;

    public RVMedRecListAdapter(Context context, ArrayList<RecordMetadata> recordMetadatas, String numberOfNotes) {
        this.context = context;
        this.recordMetadatas = recordMetadatas;
        this.numberOfNotes = numberOfNotes;
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
        holder.tvUploader.setText(recordMetadatas.get(position).getUploader());
        holder.tvUploadDate.setText(recordMetadatas.get(position).getCreatedAt().toDate().toString());
        holder.tvMedRecId.setText(recordMetadatas.get(position).getId());
        holder.tvNotes.setText(numberOfNotes);
    }

    @Override
    public int getItemCount() {
        return recordMetadatas.size();
    }

    public class MedicalRecordViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDescription, tvUploader, tvUploadDate, tvMedRecId, tvNotes;


        public MedicalRecordViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvMedRecTitle);
            tvDescription = itemView.findViewById(R.id.tvMedRecDescription);
            tvUploader = itemView.findViewById(R.id.tvMedRecUploader);
            tvUploadDate = itemView.findViewById(R.id.tvMedRecUploadDate);
            tvMedRecId = itemView.findViewById(R.id.tvMedRecId);
            tvNotes = itemView.findViewById(R.id.tvMedRecNumberOfNotes);
        }
    }
}
