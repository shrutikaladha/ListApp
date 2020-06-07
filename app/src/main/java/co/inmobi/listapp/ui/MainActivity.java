package co.inmobi.listapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import co.inmobi.listapp.databinding.ActivityMainBinding;
import co.inmobi.listapp.ui.list.ListActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        initViews();
    }

    private void initViews() {
        activityMainBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startListActivity();
            }
        });
    }

    private void startListActivity() {
        Log.d(TAG, "startListActivity()");
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
