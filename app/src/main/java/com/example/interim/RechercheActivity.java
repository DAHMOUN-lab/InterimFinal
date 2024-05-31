package com.example.interim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RechercheActivity extends AppCompatActivity implements RecycleViewOnItemClick {

    private ImageButton menuButton;
    private EditText searchField;
    private ImageButton filterButton;
    private ImageButton searchButton;
    private RecyclerView missionList;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private MissionAdapter missionAdapter;
    private ArrayList<Mission> missions;
    private ArrayList<Mission> filteredMissions;
    private String type, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche_mission);

        // Initialiser les éléments de l'interface utilisateur
        menuButton = findViewById(R.id.menuButton);
        searchField = findViewById(R.id.searchField);
        filterButton = findViewById(R.id.filter);
        searchButton = findViewById(R.id.search);
        missionList = findViewById(R.id.missionList);
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        // Initialiser Firebase
        mDatabase = FirebaseDatabase.getInstance("https://interim-38ceb-default-rtdb.europe-west1.firebasedatabase.app/");
        mDatabaseRef = mDatabase.getReference("Missions");
        // Initialiser la liste et l'adapter
        missions = new ArrayList<>();
        filteredMissions = new ArrayList<>();

        // Récupérer les missions de Firebase
        fetchMissionsFromFirebase();

        missionAdapter = new MissionAdapter(filteredMissions, this);

        // Configurer le RecyclerView
        missionList.setAdapter(missionAdapter);
        missionList.setLayoutManager(new LinearLayoutManager(this));

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
                Toast.makeText(RechercheActivity.this, "Filtrer", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action pour le bouton de recherche
                String query = searchField.getText().toString().trim();
                if (!query.isEmpty()) {
                    performSearch(query);
                } else {
                    Toast.makeText(RechercheActivity.this, "Veuillez entrer des mots clés pour rechercher", Toast.LENGTH_SHORT).show();
                }
            }
        });

        menuButton.setOnClickListener(view -> {
            Intent intent = new Intent(RechercheActivity.this, menu.class);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            startActivity(intent);
        });

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.item_spacing);
        missionList.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        // Ajouter un TextWatcher pour la barre de recherche
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMissions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void fetchMissionsFromFirebase() {
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                missions.clear();
                for (DataSnapshot missionSnapshot : dataSnapshot.getChildren()) {
                    Mission mission = missionSnapshot.getValue(Mission.class);
                    missions.add(mission);
                }
                filteredMissions.clear();
                filteredMissions.addAll(missions);
                missionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Gérer les erreurs éventuelles
                Toast.makeText(RechercheActivity.this, "Failed to load missions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterMissions(String query) {
        filteredMissions.clear();
        for (Mission mission : missions) {
            if (mission.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    mission.getPosition().toLowerCase().contains(query.toLowerCase()) ||
                    mission.getType().toLowerCase().contains(query.toLowerCase())) {
                filteredMissions.add(mission);
            }
        }
        missionAdapter.notifyDataSetChanged();
    }

    private void performSearch(String query) {
        // Vous pouvez ajouter des fonctionnalités de recherche supplémentaires ici si nécessaire
        filterMissions(query);
        hideKeyboard();
        Toast.makeText(RechercheActivity.this, "Recherche effectuée", Toast.LENGTH_SHORT).show();
    }

    public void onItemClick(int position) {
        Intent intent = new Intent(RechercheActivity.this, descriptionOffre.class);
        // Passez les informations nécessaires à la nouvelle activité ici
        intent.putExtra("missionId", filteredMissions.get(position).getId());
        intent.putExtra("id", id);
        startActivity(intent);
    }



    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filter, null);
        builder.setView(dialogView);

        CheckBox checkBoxCdi = dialogView.findViewById(R.id.checkbox_cdi);
        CheckBox checkBoxCdd = dialogView.findViewById(R.id.checkbox_cdd);
        CheckBox checkBoxInterim = dialogView.findViewById(R.id.checkbox_interim);
        EditText editTextVille = dialogView.findViewById(R.id.edittext_ville);

        builder.setPositiveButton("Appliquer", (dialog, which) -> {
            boolean cdiSelected = checkBoxCdi.isChecked();
            boolean cddSelected = checkBoxCdd.isChecked();
            boolean interimSelected = checkBoxInterim.isChecked();
            String ville = editTextVille.getText().toString().trim();

            // Appliquer les filtres
            applyFilters(cdiSelected, cddSelected, interimSelected, ville);
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void applyFilters(boolean cdiSelected, boolean cddSelected, boolean interimSelected, String ville) {
        ArrayList<Mission> filteredMissions = new ArrayList<>();
        for (Mission mission : missions) {
            boolean matchesType = (cdiSelected && "CDI".equals(mission.getType())) ||
                    (cddSelected && "CDD".equals(mission.getType())) ||
                    (interimSelected && "Intérim".equals(mission.getType()));
            boolean matchesVille = ville.isEmpty() || mission.getPosition().equalsIgnoreCase(ville);

            if (matchesType && matchesVille) {
                filteredMissions.add(mission);
            }
        }
        missionAdapter.updateMissions(filteredMissions);
    }

}
