package be.tim.rijckaert.snaprecyclerview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        final TabLayout tabLayout = (TabLayout) this.findViewById(R.id.tablayout);
        final ViewPager viewPager = (ViewPager) this.findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);

        final MockFragmentAdapter adapter = new MockFragmentAdapter(this.getBaseContext(), this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}