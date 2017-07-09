package com.example.asus.restaurantapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//Include:
// A simple Form
// A Fancier Form
// Making Our List Be Fancy

public class MainActivity extends Activity {
    EditText edtName;
    RadioGroup types;
    EditText edtAddress;
    Restaurant r = new Restaurant();

    Button buttonSave;

    ListView list;

    public List<Restaurant> model = new ArrayList<Restaurant>();
    ArrayAdapter<Restaurant> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSave = (Button) findViewById(R.id.button_save);
        buttonSave.setOnClickListener(onSave);

        list = (ListView) findViewById(R.id.restaurants);

        adapter = new RestaurantAdapter();
        list.setAdapter(adapter);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            edtName = (EditText) findViewById(R.id.name);
            edtAddress = (EditText) findViewById(R.id.address);

            r.setName(edtName.getText().toString());
            Log.i("getName: ", r.getName().toString());
            r.setAddress(edtAddress.getText().toString());
            Log.i("getAddress: ", r.getAddress().toString());
            types = (RadioGroup) findViewById(R.id.types);
            switch (types.getCheckedRadioButtonId()) {
                case R.id.sit_down:
                    r.setType("Sit_down");

                    break;
                case R.id.delivery:
                    r.setType("Delivery");
                    break;
                case R.id.take_out:
                    r.setType("Take_out");
                    break;
                default:
                    break;
            }
            Log.i("getType", r.getType().toString());
            adapter.add(r);
        }
    };

    class RestaurantAdapter extends ArrayAdapter<Restaurant> {

        RestaurantAdapter() {
            super(MainActivity.this, R.layout.row, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            RestaurantHolder holder = null;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();

                row = inflater.inflate(R.layout.row, parent, false);
                holder = new RestaurantHolder(row);
                row.setTag(holder);
            } else {
                holder = (RestaurantHolder) row.getTag();
            }
            holder.populateFrom(model.get(position));
            return (row);
        }
    }

    static class RestaurantHolder {
        private TextView name = null;
        private TextView address = null;
        private ImageView icon = null;

        public RestaurantHolder(View row) {
            name = (TextView) row.findViewById(R.id.text_title);
            address = (TextView) row.findViewById(R.id.text_address);
            icon = (ImageView) row.findViewById(R.id.icon);
        }

        public void populateFrom(Restaurant restaurant) {
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());

            if (restaurant.getType().equals("Sit_down")) {
                icon.setImageResource(R.drawable.h1);
            } else if (restaurant.getType().equals("Take_out")) {
                icon.setImageResource(R.drawable.h2);
            } else {
                icon.setImageResource(R.drawable.h);
            }
        }
    }
}