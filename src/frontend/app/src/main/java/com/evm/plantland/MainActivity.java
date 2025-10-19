package com.evm.plantland;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private ImageButton GalleryBtn, WishlistBtn, AIchatBtn, SettingsBtn;
    private GalleryFragment galleryFragment = new GalleryFragment();
    private WishlistFragment wishlistFragment = new WishlistFragment();
    private AIchatFragment aichatFragment = new AIchatFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        GalleryBtn = findViewById(R.id.GalleryBtn);
        WishlistBtn = findViewById(R.id.WishlistBtn);
        AIchatBtn = findViewById(R.id.AIchatBtn);
        SettingsBtn = findViewById(R.id.SettingsBtn);

        setNewFragment(galleryFragment);

        GalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewFragment(galleryFragment);
            }
        });

        WishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewFragment(wishlistFragment);
            }
        });

        AIchatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewFragment(aichatFragment);
            }
        });

        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewFragment(settingsFragment);
            }
        });
    }

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    public void startNewActivity(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}