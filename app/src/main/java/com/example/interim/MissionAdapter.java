package com.example.interim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MissionAdapter extends ArrayAdapter<Mission> {
    public MissionAdapter(Context context, List<Mission> missions) {
        super(context, 0, missions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvMissionName = convertView.findViewById(R.id.tvMissionName);
        TextView tvMissionLocation = convertView.findViewById(R.id.tvMissionLocation);
        TextView tvMissionSalary = convertView.findViewById(R.id.tvMissionSalary);
        TextView tvMissionType = convertView.findViewById(R.id.tvMissionType);

        Mission mission = getItem(position);
        tvMissionName.setText(mission.getName());
        tvMissionLocation.setText(mission.getLocation());
        tvMissionSalary.setText(String.format("%s€/heure", mission.getSalary()));
        tvMissionType.setText(mission.getType());

        return convertView;
    }
}
