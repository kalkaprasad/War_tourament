package com.app.wartournament;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    private Bundle bundle;
    private FloatingActionButton matchBt;
    private ViewPagerAdapter pagerAdapter;
    /* access modifiers changed from: private */
    public String strId;
    /* access modifiers changed from: private */
    public String strTitle;
    /* access modifiers changed from: private */
    public String strURL;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);

        UpcomingGameFragment liveFragment = new UpcomingGameFragment();

        OnGoingFragment playFragment = new OnGoingFragment();

        ResultFragment resultFragment = new ResultFragment();

        this.pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.pagerAdapter.addFragments(playFragment);
        this.pagerAdapter.addFragments(liveFragment);
        this.pagerAdapter.addFragments(resultFragment);
        this.viewPager.setAdapter(this.pagerAdapter);
        this.viewPager.setCurrentItem(1);
        this.tabLayout.setupWithViewPager(this.viewPager);

        for (int i = 0; i < this.tabLayout.getTabCount(); i++) {
            this.tabLayout.getTabAt(0).setText((CharSequence) "ONGOING");
            this.tabLayout.getTabAt(1).setText((CharSequence) "UPCOMING");
            this.tabLayout.getTabAt(2).setText((CharSequence) "RESULTS");
        }

    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragments = new ArrayList<>();

        public void addFragments(Fragment fragments2) {
            this.fragments.add(fragments2);
        }

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            return (Fragment) this.fragments.get(position);
        }

        public int getCount() {
            return this.fragments.size();
        }
    }
}
