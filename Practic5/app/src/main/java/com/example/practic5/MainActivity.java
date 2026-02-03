package com.example.practic5;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fab = findViewById(R.id.fab);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Iconos en los tabs (EXTRA)
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_form);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_list);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_info);

        fab.setOnClickListener(v -> {
            Snackbar.make(v, "Acción realizada", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER", view1 -> {
                        Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show();
                    })
                    .show();
        });
    }
}
