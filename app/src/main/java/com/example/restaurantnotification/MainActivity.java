package com.example.restaurantnotification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.LinkedList;
import java.util.List;

//This application shows the information associated with the beacon closer

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private Toolbar mToolbar;

    private RecyclerView recyclerView;
    private MenuAdapter cAdapter;
    private List<Item> productsList ;

    private BeaconManager b;
    //private Button stopSearchingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.restaurant_menu_toolbar);

        /*
        stopSearchingButton = (Button) findViewById(R.id.button_stop_searching);
        stopSearchingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = new BeaconManager(getApplicationContext());

                b.connect(new BeaconManager.ServiceReadyCallback() {
                    @Override
                    public void onServiceReady() {
                        b.stopMonitoring(new Region("monitored region",
                                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 17957, 56571));
                    }
                });

                Toast.makeText(MainActivity.this, "Research was stopped", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    private boolean seen=false;

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();

        int upToDate = getIntent().getIntExtra("notify", 0);
        Log.i(TAG, "upTodDate = " + upToDate);

        if(upToDate==1 && seen==false){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            // set title
            alertDialogBuilder.setTitle("Restaurant AL VIALETTO");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Welcome in our restaurant. Read the daily menu to find out what " +
                            "fantastic dishes you can taste here. Thank you for downloading the app, " +
                            "at the time of payment notify the promotional code -PervasiveSystems- to " +
                            "get a discount of 10%.")
                    .setCancelable(false)
                    .setPositiveButton("Go", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateUI();
                        }
                    })

                    .setNegativeButton("I don't care", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //setContentView(R.layout.activity_main);
                            onBackPressed();
                            seen=true;
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Metodi ausiliari -----------------------------------------------------------------------------

    private void updateUI(){
        Log.i(TAG, "updateUI");
        setContentView(R.layout.dishes_of_the_day);

        mToolbar = (Toolbar) findViewById(R.id.restaurant_menu_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Dishes of the day");

        recyclerView = (RecyclerView) findViewById(R.id.restaurant_menu_recyclerview);

        //aggiunta divisore a recycler view
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        productsList = new LinkedList<Item>();

        productsList.add(new Item(R.drawable.antipasto,"Antipasto","8 €"));
        productsList.add(new Item(R.drawable.fritti,"Fritti","6 €"));
        productsList.add(new Item(R.drawable.pasta,"Pasta","6 €"));
        productsList.add(new Item(R.drawable.lasagna,"Lasagna","7 €"));
        productsList.add(new Item(R.drawable.pizza,"Pizza","7 €"));
        productsList.add(new Item(R.drawable.panino,"Panino","7 €"));
        productsList.add(new Item(R.drawable.bistecca,"Bistecca","18 €"));
        productsList.add(new Item(R.drawable.pesce,"Pesce","15 €"));
        productsList.add(new Item(R.drawable.verdure,"Verdure","8 €"));
        productsList.add(new Item(R.drawable.insalata,"Insalata","5 €"));
        productsList.add(new Item(R.drawable.gelato,"Gelato","5 €"));
        productsList.add(new Item(R.drawable.tiramisu,"Tiramisu","5 €"));

        cAdapter = new MenuAdapter(productsList,MainActivity.this);

        recyclerView.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }
}
