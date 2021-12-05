package com.example.hodoo.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.hodoo.R;
import com.example.hodoo.view.fragment.ChatListFragment;
import com.example.hodoo.view.fragment.ContactListFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView profImageView;

    private TextView usernameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_layout);

        usernameTextView = findViewById(R.id.usernameonmainactivity);
        usernameTextView.setText("Chris");
        profImageView = findViewById(R.id.profile_image);
        profImageView.setImageResource(R.drawable.user);

        toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ChatListFragment(), "Chats");
        viewPagerAdapter.addFragment(new ContactListFragment(), "Contacts");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {

        ArrayList<Fragment> fragments;
        ArrayList<String> titles;


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

            this.fragments = new ArrayList<>();
            this. titles = new ArrayList<>();

        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment (Fragment fragment, String title) {

            fragments.add(fragment);
            titles.add(title);



        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
