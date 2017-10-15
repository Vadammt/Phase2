package de.mordsgau.phase2.fragments;

/**
 * Created by simonbaier on 15.10.17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import de.mordsgau.phase2.R;
import de.mordsgau.phase2.adapter.RecyclerAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static de.mordsgau.phase2.Dashboard.RECENT;

/**
 * A fragment containing recent, interesting events.
 */
public class RecentFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public RecentFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RecentFragment newInstance(int sectionNumber) {
        RecentFragment fragment = new RecentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final RecyclerView cardContainer = rootView.findViewById(R.id.card_container);
        cardContainer.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        cardContainer.setItemAnimator(new SlideInUpAnimator());
        cardContainer.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        cardContainer.setLayoutManager(layoutManager);
        final RecyclerAdapter adapter = new RecyclerAdapter(RECENT, getActivity().getApplicationContext());
        cardContainer.setAdapter(adapter);
        //populateCards(cardContainer, getArguments().getInt(ARG_SECTION_NUMBER));
        adapter.notifyItemRangeInserted(0, 4);
        return rootView;
    }

}