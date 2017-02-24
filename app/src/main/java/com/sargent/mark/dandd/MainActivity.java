package com.sargent.mark.dandd;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.HashMap;
import java.util.Random;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    public static final int D4 = 0;
    public static final int D6 = 1;
    public static final int D8 = 2;
    public static final int D10 = 3;
    public static final int D12 = 4;
    public static final int D20 = 5;

    //A map to get the correct dievalue - image map for a particular die
    protected final HashMap<Integer, HashMap<Integer, Integer>> dieMaps = new HashMap<>();

    //Individual maps for each die, they map the value of the roll with the right image
    protected final HashMap<Integer, Integer> d4Map = new HashMap<>();
    protected final HashMap<Integer, Integer> d6Map = new HashMap<>();
    protected final HashMap<Integer, Integer> d8Map = new HashMap<>();
    protected final HashMap<Integer, Integer> d10Map = new HashMap<>();
    protected final HashMap<Integer, Integer> d12Map = new HashMap<>();
    protected final HashMap<Integer, Integer> d20Map = new HashMap<>();

    Handler handler = new Handler();
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupMaps();

        final ImageButton d4button = (ImageButton)findViewById(R.id.d4);
        d4button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switchImage(4, D4, d4button);
            }});

        final ImageButton d6button = (ImageButton)findViewById(R.id.d6);
        d6button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchImage(6, D6, d6button);
            }
        });

        final ImageButton d8button = (ImageButton)findViewById(R.id.d8);
        d8button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchImage(8, D8, d8button);
            }
        });

        final ImageButton d10button = (ImageButton)findViewById(R.id.d10);
        d10button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switchImage(9, D10, d10button);
            }
        });

        final ImageButton d12button = (ImageButton)findViewById(R.id.d12);
        d12button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchImage(12, D12, d12button);
            }
        });

        final ImageButton d20button = (ImageButton)findViewById(R.id.d20);
        d20button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchImage(20, D20, d20button);
            }
        });
    }

    //Our Methods ////////////////////

    protected void switchImage(final int max, final int type, final ImageButton button){
        final int value = random.nextInt(max) + 1;

        for(int i = 1; i < value; i++) {
            Thread thread = new MyThread(i, type, button);
            handler.postDelayed(thread, 200 * i);
        }

        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                button.setImageResource(dieMaps.get(type).get(value));
            }
        }, 100 * value);

    }

    protected void setupMaps(){
        //d4map
        for(int i = 1; i < 5; i++){
            String url = "drawable/fourperspective"+i+"c";
            d4Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
        }

        //d6map
        for(int i = 1; i < 7; i++){
            String url = "drawable/sixnum"+i;
            d6Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
        }

        //d8map
        for(int i = 1; i < 9; i++){
            String url = "drawable/eight_side"+i;
            d8Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
        }

        //d10map --- for this die 10 == 0
        for(int i = 1; i < 10; i++){
            String url = "drawable/tenxone"+i;
            d10Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
            d10Map.put(10, R.drawable.tenxone0);
        }

        //d12map
        for(int i = 1; i < 13; i++){
            String url = "drawable/twelvesided"+i;
            d12Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
        }

        //d20Map
        for(int i = 1; i < 21; i++){
            String url = "drawable/twentysided"+i;
            d20Map.put(i, getResources().getIdentifier(url, "drawable", getPackageName()));
        }

        //Place above maps into dieMap
        dieMaps.put(D4, d4Map);
        dieMaps.put(D6, d6Map);
        dieMaps.put(D8, d8Map);
        dieMaps.put(D10, d10Map);
        dieMaps.put(D12, d12Map);
        dieMaps.put(D20, d20Map);

    }


    ///////////////////////////////////

    class MyThread extends Thread{
        int value;
        ImageButton button;
        int type;
        public MyThread(int value, int type, ImageButton button){
            this.value = value;
            this.button = button;
            this.type = type;
        }

        @Override
        public void run() {
            button.setImageResource(dieMaps.get(type).get(value));
        }
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
}
