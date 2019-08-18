package until.soulsparc.com.soulsparc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chandan on 18/08/19.
 */

public class FragmentFirst extends android.support.v4.app.Fragment {
    Spinner season, crop, seedSuggestion;
    String seasonValue;
    String[] crops;
    Button save;

    public FragmentFirst() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        season = rootView.findViewById(R.id.spinnerSeason);
        crop = rootView.findViewById(R.id.spinnerCrop);
        seedSuggestion = rootView.findViewById(R.id.spinnerSeed);
        save = rootView.findViewById(R.id.save);

        String[] seasons = new String[]{
                "Select Season",
                "Kharif",
                "Rabi",
                "Zaid"
        };
        List<String> seasonList = new ArrayList<>(Arrays.asList(seasons));
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinneritem, seasonList);
        seasonAdapter.setDropDownViewResource(R.layout.spinnerfront);
        season.setAdapter(seasonAdapter);


        season.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seasonValue = (String) parent.getItemAtPosition(position);
                if (seasonValue.equals("Kharif")) {
                    crops = new String[]{
                            "Select Crops",
                            "Jawar",
                            "Bajra",
                            "Rice",
                            "Cotton",
                            "Jute",
                            "GroundNut",
                            "Tobacco"
                    };
                    BindCrop();
                } else if (seasonValue.equals("Rabi")) {
                    crops = new String[]{
                            "Select Crops",
                            "Wheat",
                            "Barley",
                            "Mustard",
                            "Potatoes"
                    };
                    BindCrop();
                } else if (seasonValue.equals("Zaid")) {
                    crops = new String[]{
                            "Select Crops",
                            "Rice",
                            "OilSeeds"
                    };
                    BindCrop();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        String[] seeds = new String[]{
                "Select Seed",
                "aa",
                "bb",
                "cc"
        };
        List<String> seednList = new ArrayList<>(Arrays.asList(seeds));
        ArrayAdapter<String> seedAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinneritem, seednList);
        seedAdapter.setDropDownViewResource(R.layout.spinnerfront);
        seedSuggestion.setAdapter(seedAdapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ListView1.class);
                startActivity(i);
            }
        });
        return rootView;
    }

    public void BindCrop() {
        List<String> cropList = new ArrayList<>(Arrays.asList(crops));
        ArrayAdapter<String> cropAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinneritem, cropList);
        cropAdapter.setDropDownViewResource(R.layout.spinnerfront);
        crop.setAdapter(cropAdapter);
    }

}
