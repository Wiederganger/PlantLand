package com.evm.plantland;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LogInActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager;
    ViewPagerFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tabLayout = findViewById(R.id.tabLayout);
        pager = findViewById(R.id.pager);


        adapter = new ViewPagerFragmentAdapter(this, tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        new TabLayoutMediator(tabLayout, pager, ((tab, position) -> {
            if (position == 0) {
                tab.setText("Авторизация");
            } else if (position == 1) {
                tab.setText("Регистрация");
            }
        })).attach();
    }

    public static class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        int size;

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, int size) {
            super(fragmentActivity);
            this.size = size;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new LogInFragment();
                case 1:
                    return new SignUpFragment();
            }
            return new LogInFragment();
        }

        @Override
        public int getItemCount() {
            return size;
        }
    }
}