package com.baidu.kwgames;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public class LineRelativeLayout extends RelativeLayout {
    private final HashMap<Pair<View, View>, Integer> mLinesColor;
    private final Paint mPaint;

    public LineRelativeLayout(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mLinesColor = new HashMap<>();
    }

    public LineRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mLinesColor = new HashMap<>();
    }

    public LineRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.mLinesColor = new HashMap<>();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (Map.Entry<Pair<View, View>, Integer> entry : this.mLinesColor.entrySet()) {
            this.mPaint.setColor(entry.getValue().intValue());
            canvas.drawLine(((View) entry.getKey().first).getX() + (((View) entry.getKey().first).getWidth() / 2), ((View) entry.getKey().first).getY() + (((View) entry.getKey().first).getHeight() / 2), ((View) entry.getKey().second).getX() + (((View) entry.getKey().second).getWidth() / 2), ((View) entry.getKey().second).getY() + (((View) entry.getKey().second).getHeight() / 2), this.mPaint);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth(2.0f);
    }

    public void setLineColor(int i) {
        this.mPaint.setColor(i);
    }

    public void addLine(View view, View view2) {
        addLine(view, view2, -1);
    }

    public void addLine(View view, View view2, int i) {
        if (view == null || view2 == null) {
            return;
        }
        this.mLinesColor.put(new Pair<>(view, view2), Integer.valueOf(i));
        invalidate();
    }

    public void delLine(View view, View view2) {
        this.mLinesColor.remove(new Pair(view, view2));
        invalidate();
    }

    public void delLineByNode(View view) {
        Iterator<Map.Entry<Pair<View, View>, Integer>> it = this.mLinesColor.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Pair<View, View>, Integer> next = it.next();
            if (next.getKey().first == view || next.getKey().second == view) {
                it.remove();
            }
        }
    }

    public void delLineByMacroNode(View view) {
        KeyInfo keyInfo = (KeyInfo) view.getTag(R.id.tag_key_info);
        Iterator<Map.Entry<Pair<View, View>, Integer>> it = this.mLinesColor.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Pair<View, View>, Integer> next = it.next();
            KeyInfo keyInfo2 = (KeyInfo) ((View) next.getKey().second).getTag(R.id.tag_key_info);
            if (((KeyInfo) ((View) next.getKey().first).getTag(R.id.tag_key_info)).same_macro(keyInfo) || keyInfo2.same_macro(keyInfo)) {
                it.remove();
            }
        }
    }

    public void changeLineColorByMacroNode(View view, int i, List<Pair<View, View>> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        for (Map.Entry<Pair<View, View>, Integer> entry : this.mLinesColor.entrySet()) {
            if (!list.contains(entry.getKey())) {
                if (entry.getKey().first == view) {
                    entry.setValue(Integer.valueOf(i));
                    list.add(entry.getKey());
                    changeLineColorByMacroNode((View) entry.getKey().second, i, list);
                } else if (entry.getKey().second == view) {
                    entry.setValue(Integer.valueOf(i));
                    list.add(entry.getKey());
                    changeLineColorByMacroNode((View) entry.getKey().first, i, list);
                }
            }
        }
    }

    public void changeAllLines(int i) {
        for (Map.Entry<Pair<View, View>, Integer> entry : this.mLinesColor.entrySet()) {
            entry.setValue(Integer.valueOf(i));
        }
    }

    public void delete_all_lines() {
        this.mLinesColor.clear();
    }
}
