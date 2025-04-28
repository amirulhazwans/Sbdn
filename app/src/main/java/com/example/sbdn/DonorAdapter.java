package com.example.sbdn;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.DonorViewHolder> {

    private List<Donor> donorList;

    public DonorAdapter(List<Donor> donorList) {
        this.donorList = donorList;
    }

    @Override
    public DonorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_item, parent, false);
        return new DonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DonorViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.nameTextView.setText(donor.getName());
        holder.bloodTypeTextView.setText(donor.getBloodType());
        holder.locationTextView.setText(donor.getLocation());
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public void updateList(List<Donor> newDonorList) {
        donorList = newDonorList;
        notifyDataSetChanged();
    }

    public static class DonorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, bloodTypeTextView, locationTextView;

        public DonorViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            bloodTypeTextView = itemView.findViewById(R.id.bloodTypeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}
