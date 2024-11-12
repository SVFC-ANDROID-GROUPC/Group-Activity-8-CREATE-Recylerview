package com.jorimpablico.addandfetchfirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jorimpablico.addandfetchfirestore.R;
import com.jorimpablico.addandfetchfirestore.universitypage.University;

import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder>{

    List<University> universityList;

    public UniversityAdapter(List<University> universityList) {
        this.universityList = universityList;
    }

    @NonNull
    @Override
    public UniversityAdapter.UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_university_card, parent, false);
        return new UniversityAdapter.UniversityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityAdapter.UniversityViewHolder holder, int position) {

        University university = universityList.get(position);

        holder.tvName.setText(university.getName());
        holder.tvType.setText(university.getType());
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    public class UniversityViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvType;

        public UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType = itemView.findViewById(R.id.tv_type);
        }
    }
}

