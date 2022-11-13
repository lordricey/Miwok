package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.miwok.activities.ColorsActivity;
import com.example.miwok.activities.FamilyActivity;
import com.example.miwok.activities.NumbersActivity;
import com.example.miwok.activities.PhrasesActivity;
import com.example.miwok.utils.SimpleFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = (ViewPager2) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        SimpleFragmentAdapter fragmentAdapter = new SimpleFragmentAdapter(this);

        viewPager.setAdapter(fragmentAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        String[] colors = new String[]{"NUMBERS", "FAMILY", "COLORS", "PHRASES"};

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(colors[position])).attach();
    }
}