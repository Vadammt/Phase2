package de.mordsgau.phase2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by simonbaier on 14.10.17.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    final int viewIndex;

    public RecyclerAdapter(int viewIndex) {
        this.viewIndex = viewIndex;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Contains a text view and a layout containing charts
        final CardView container;
        final TextView textView;
        final LinearLayout chartLayout;

        public ViewHolder(View itemView, TextView textView, LinearLayout chartLayout, CardView container) {
            super(itemView);
            this.textView = textView;
            this.chartLayout = chartLayout;
            this.container = container;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        // card index
        vh.container.setElevation(30F);
        switch (position) {
            case 0:
                vh.textView.setText("Test 1");
                //TODO chart
                break;
            case 1:
                vh.textView.setText("Test 2");

                break;
            case 2:
                vh.textView.setText("Test 3");
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
        ViewHolder vh = new ViewHolder(linearLayout.getRootView(), textContent, chartContainer, card);
        return vh;
    }
}
