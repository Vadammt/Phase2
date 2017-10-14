package de.mordsgau.phase2.card;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.widget.LinearLayout;

import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.renderer.XRenderer;
import com.db.chart.renderer.YRenderer;
import com.db.chart.util.Tools;
import com.db.chart.view.HorizontalStackBarChartView;
import com.db.chart.view.LineChartView;

import de.mordsgau.phase2.R;
import de.mordsgau.phase2.RecyclerAdapter;

public class CardFactory {

    private static final String[] sampleLabels = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};

    private static final float[][] sampleValues = {{0f, 2f, 1.4f, 4.f, 3.5f, 4.3f, 2f, 4f, 6.f},
            {1.5f, 2.5f, 1.5f, 5f, 4f, 5f, 4.3f, 2.1f, 1.4f}};

    private static final String[] sampleLabels_batteryState =
            {"Auto"};

    private static final float[][] sampleValues_batteryState =
            {{50f},  // % filled
            { 20f},  // % public used
            { 30f}}; // % not charged / empty

    public static void buildPowerConsumption(RecyclerAdapter.ViewHolder card) {

        card.textView.setText(R.string.card_power_consumption);

        LineChartView chart = new LineChartView(card.context);
        card.chartLayout.addView(chart);
        chart.setLayoutParams(new LinearLayout.LayoutParams(card.chartLayout.getLayoutParams()));

        LineSet dataset = new LineSet(sampleLabels, sampleValues[0]);
        dataset.setColor(Color.parseColor("#53c1bd"))
                .setFill(Color.parseColor("#3d6c73"))
                .setGradientFill(new int[] {Color.parseColor("#364d5a"), Color.parseColor("#3f7178")},
                        null);
        chart.addData(dataset);

        chart.setBorderSpacing(1)
                .setXLabels(AxisRenderer.LabelPosition.NONE)
                .setYLabels(AxisRenderer.LabelPosition.NONE);
        chart.setXAxis(true);
        chart.setYAxis(false);

        chart.show();
    }

    public static void buildBatteryState(RecyclerAdapter.ViewHolder card) {

        card.textView.setText(R.string.card_battery_states);

        HorizontalStackBarChartView chart = new HorizontalStackBarChartView(card.context);
        card.chartLayout.addView(chart);
        chart.setLayoutParams(new LinearLayout.LayoutParams(card.chartLayout.getLayoutParams()));

        BarSet stackBarSet = new BarSet(sampleLabels_batteryState, sampleValues_batteryState[0]);
        stackBarSet.setColor(Color.parseColor("#a1d949"));
        chart.addData(stackBarSet);

        stackBarSet = new BarSet(sampleLabels_batteryState, sampleValues_batteryState[1]);
        stackBarSet.setColor(Color.parseColor("#ffcc6a"));
        chart.addData(stackBarSet);

        stackBarSet = new BarSet(sampleLabels_batteryState, sampleValues_batteryState[2]);
        stackBarSet.setColor(Color.parseColor("#777777"));
        chart.addData(stackBarSet);

        chart.setBarSpacing(Tools.fromDpToPx(15));
        chart.setRoundCorners(Tools.fromDpToPx(1));

        Paint thresPaint = new Paint();
        thresPaint.setColor(Color.parseColor("#dad8d6"));
        thresPaint.setPathEffect(new DashPathEffect(new float[] {10, 20}, 0));
        thresPaint.setStyle(Paint.Style.STROKE);
        thresPaint.setAntiAlias(true);
        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        chart.setXAxis(false)
                .setXLabels(XRenderer.LabelPosition.OUTSIDE)
                .setYAxis(false)
                .setYLabels(YRenderer.LabelPosition.NONE)
                .setValueThreshold(89.f, 89.f, thresPaint);

        chart.show();
    }
}
