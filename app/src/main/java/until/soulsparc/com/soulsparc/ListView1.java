package until.soulsparc.com.soulsparc;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListView1 extends AppCompatActivity {
    // Array of strings for ListView1 Title
    String[] listviewTitle = new String[]{
            "L & T Finance", "Pnb Housing", "Mahindra Finance", "Muthoot Finance",
            "HDB Finance", "Aditya Birla", "Bajaj Finance", "Cholamandalam",
    };


    int[] listviewImage = new int[]{
            R.drawable.larsen, R.drawable.pnb, R.drawable.mahindra, R.drawable.muthoot_fin,
            R.drawable.hdb, R.drawable.aditya, R.drawable.bajaj, R.drawable.colamandalam,
    };

    String[] listviewShortDescription = new String[]{
            "1%", "2%", "2.5%", "1.5%",
            "0.5%", "1.6%", "2.8%", "1.2%",
    };

    String[] listviewSubsidise = new String[]{
            "1%", "2%", "2.5%", "1.5%",
            "0.5%", "1.6%", "2.8%", "1.2%",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 8; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_subsidise", listviewSubsidise[i]);
            hm.put("listview_discription", listviewShortDescription[i]);

            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_image", "listview_title", "listview_discription","listview_subsidise"};
        int[] to = {R.id.listview_image, R.id.listview_item_title, R.id.listview_item_short_description,R.id.listview_subsidise};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), aList, R.layout.cardview, from, to);
        ListView androidListView =  findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Successfully Request Sent to Respective Company", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), BaseActivity.class);
                startActivity(intent);
            }
        });
    }
}
