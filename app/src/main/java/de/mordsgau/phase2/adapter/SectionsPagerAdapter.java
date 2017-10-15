package de.mordsgau.phase2.adapter;

/**
 * Created by simonbaier on 15.10.17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import de.mordsgau.phase2.fragments.RecentFragment;

import static de.mordsgau.phase2.Dashboard.RECENT;
import static de.mordsgau.phase2.Dashboard.SETTINGS;
import static de.mordsgau.phase2.Dashboard.STATISTICS;

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
        // Return a RecentFragment (defined as a static inner class below).
        return RecentFragment.newInstance(position);
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