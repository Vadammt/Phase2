package de.mordsgau.phase2.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by simonbaier on 15.10.17.
 */
public class CardViewHolder extends RecyclerView.ViewHolder {
    // Contains a text view and a layout containing charts
    final CardView container;
    public final TextView textView;
    public final LinearLayout chartLayout;
    public final Context context;

    public CardViewHolder(View itemView, TextView textView, LinearLayout chartLayout, CardView container, Context context) {
        super(itemView);
        this.textView = textView;
        this.chartLayout = chartLayout;
        this.container = container;
        this.context = context;
    }
}