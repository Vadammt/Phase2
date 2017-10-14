package de.mordsgau.phase2;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.mordsgau.phase2.card.CardFactory;

/**
 * Created by simonbaier on 14.10.17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    final int viewIndex;
    private final Context context;

    public RecyclerAdapter(int viewIndex, Context applicationContext) {
        this.viewIndex = viewIndex;
        this.context = applicationContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Contains a text view and a layout containing charts
        final CardView container;
        public final TextView textView;
        public final LinearLayout chartLayout;
        public final Context context;

        public ViewHolder(View itemView, TextView textView, LinearLayout chartLayout, CardView container, Context context) {
            super(itemView);
            this.textView = textView;
            this.chartLayout = chartLayout;
            this.container = container;
            this.context = context;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
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
        return 4;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        final TextView textContent = linearLayout.findViewById(R.id.text_content);
        final LinearLayout chartContainer = linearLayout.findViewById(R.id.chart_container);
        final CardView card = linearLayout.findViewById(R.id.card_view);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(linearLayout.getRootView(), textContent, chartContainer, card, context);
        return vh;
    }
}
