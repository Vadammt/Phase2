package de.mordsgau.phase2.card;

import android.graphics.Color;
import android.widget.LinearLayout;

import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.view.LineChartView;

import de.mordsgau.phase2.R;
import de.mordsgau.phase2.RecyclerAdapter;

public class CardFactory {

    private LineChartView mChart;
    private static final String[] mLabels = {"", "", "", "", "", "", "", "", ""};

    private static final float[][] mValues = {{0f, 2f, 1.4f, 4.f, 3.5f, 4.3f, 2f, 4f, 6.f},
            {1.5f, 2.5f, 1.5f, 5f, 4f, 5f, 4.3f, 2.1f, 1.4f}};

    public static void buildPowerConsumption(RecyclerAdapter.ViewHolder card) {
        LineChartView chart = new LineChartView(card.context);

        card.chartLayout.addView(chart);
        chart.setLayoutParams(new LinearLayout.LayoutParams(card.chartLayout.getLayoutParams()));
        //final LineChartView chart = (LineChartView) card.chartLayout.findViewById(R.id.chart_test);
        card.textView.setText(R.string.card_power_consumption);

        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.parseColor("#53c1bd"))
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(new int[] {Color.parseColor("#364d5a"), Color.parseColor("#3f7178")},
                        null);
        chart.addData(dataset);

        chart.setBorderSpacing(1)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE);

        //Animation anim = new Animation().setEndAction(action);

        chart.show();

        // Add chart to card;
    }

    private static void build(RecyclerAdapter.ViewHolder parentView) {
        // TODO implement me...
    }
}
