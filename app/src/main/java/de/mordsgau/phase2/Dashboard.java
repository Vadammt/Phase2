package de.mordsgau.phase2;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class Dashboard extends AppCompatActivity {

    /* Fragment indices */
    public static final int RECENT = 0;
    public static final int STATISTICS = 1;
    public static final int SETTINGS = 2;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(mSectionsPagerAdapter);
        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://images.unsplash.com/photo-1467380119941-dc5acf7c6325?dpr=1&auto=compress,format&fit=crop&w=2853&h=&q=80&cs=tinysrgb&crop=");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://images.unsplash.com/reserve/wPCyys8TPCHY3GXm2N2D_ssp_inthewoods_1.jpg?dpr=1&auto=compress,format&fit=crop&w=1650&h=&q=80&cs=tinysrgb&crop=");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://images.unsplash.com/photo-1466428996289-fb355538da1b?dpr=1&auto=compress,format&fit=crop&w=2850&h=&q=80&cs=tinysrgb&crop=");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

    }


    @Override
    protected void onStart() {
        super.onStart();
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(false);
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
            /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/
            final RecyclerView cardContainer = rootView.findViewById(R.id.card_container);
            cardContainer.addItemDecoration(new MaterialViewPagerHeaderDecorator());
            cardContainer.setItemAnimator(new SlideInUpAnimator());
            cardContainer.setHasFixedSize(true);
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            cardContainer.setLayoutManager(layoutManager);
            final RecyclerAdapter adapter = new RecyclerAdapter(RECENT, getContext());
            cardContainer.setAdapter(adapter);
            //populateCards(cardContainer, getArguments().getInt(ARG_SECTION_NUMBER));
            adapter.notifyItemRangeInserted(0, 4);
            return rootView;
        }

        /**
         * Inflate:
         * Generate cards and add them to the layout
         *
         * @param cardContainer layout to add the cards to
         * @param sectionIndex section to generate the cards for
         */
        private void populateCards(RecyclerView cardContainer, int sectionIndex) {
            switch(sectionIndex) {
                case RECENT:
                    final CardView cardView = new CardView(getContext());
                    cardView.setElevation(20F);
                    // Text
                    final TextView textView = new TextView(getContext());
                    textView.setText("Ziele");
                    textView.setTextSize(16F);
                    // Chart
                    final HorizontalBarChart barChart = new HorizontalBarChart(getContext());
                    barChart.setDrawGridBackground(false);
                    barChart.getDescription().setEnabled(false);
                    barChart.setPinchZoom(false);
                    final List<BarEntry> barEntryList = new ArrayList<>();
                    barEntryList.add(new BarEntry(50, new float[]{-10, 10}));
                    final BarDataSet dataSet = new BarDataSet(barEntryList, "Fortschritt");
                    final BarData barData = new BarData();
                    barChart.setData(barData);
                    cardView.addView(textView);
                    cardView.addView(barChart);
                    cardContainer.addView(cardView);
                    break;
                case STATISTICS:
                    break;
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case RECENT:
                    return "Aktuelles";
                case STATISTICS:
                    return "Statistik";
                case SETTINGS:
                    return "Einstellungen";
            }
            return null;
        }
    }
}
