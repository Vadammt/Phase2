package de.mordsgau.phase2.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.mordsgau.phase2.R;
import de.mordsgau.phase2.card.CardFactory;
import de.mordsgau.phase2.card.CardViewHolder;

/**
 * Created by simonbaier on 14.10.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<CardViewHolder> {
    final int viewIndex;
    private final Context context;

    public RecyclerAdapter(int viewIndex, Context applicationContext) {
        this.viewIndex = viewIndex;
        this.context = applicationContext;
    }

    @Override
    public void onBindViewHolder(CardViewHolder vh, int position) {
        // card index
        switch (position) {
            case 0:
                CardFactory.buildPowerConsumption(vh);
                break;
            case 1:
                CardFactory.buildIncomeForecast(vh);
                break;
            case 2:
                CardFactory.buildBatteryState(vh);
                break;
            default:
                vh.textView.setText("Test default");
        }
    }

    @Override
    public int getItemCount() {
        // hard code card count for now
        return 8;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView linearLayout = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        final TextView textContent = linearLayout.findViewById(R.id.text_content);
        final LinearLayout chartContainer = linearLayout.findViewById(R.id.chart_container);
        final CardView card = linearLayout.findViewById(R.id.card_view);
        // set the view's size, margins, paddings and layout parameters
        CardViewHolder vh = new CardViewHolder(linearLayout.getRootView(), textContent, chartContainer, card, context);
        return vh;
    }
}
