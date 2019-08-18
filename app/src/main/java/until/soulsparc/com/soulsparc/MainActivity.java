package until.soulsparc.com.soulsparc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView financeRadio;
    String[] listItems = {"one", "two", "three", "four", "five"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       /* financeRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose item");

                int checkedItem = 0; //this will checked the item when user open the dialog
                builder.setSingleChoiceItems(listItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Position: " + which + " Value: " + listItems[which], Toast.LENGTH_LONG).show();
                    }
                });

                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });*/
    }

}
