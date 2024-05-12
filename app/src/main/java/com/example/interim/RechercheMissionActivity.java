package com.example.interim;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interim.Mission;
import com.example.interim.MissionAdapter;
import com.example.interim.R;

import java.util.ArrayList;
import java.util.List;

public class RechercheMissionActivity extends AppCompatActivity {
    private ListView missionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_mission);

        missionList = findViewById(R.id.missionList);
        List<Mission> missions = getMissions();

        // Initialiser l'adapter avec les missions récupérées
        MissionAdapter adapter = new MissionAdapter(this, missions);
        missionList.setAdapter(adapter);
    }

    private List<Mission> getMissions() {
        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("Mission 1", "Paris", "50", "Interim, 35H"));
        missions.add(new Mission("Mission 2", "Lyon", "45", "CDI, 40H"));
        // Ajoutez d'autres missions ici
        return missions;
    }
}

