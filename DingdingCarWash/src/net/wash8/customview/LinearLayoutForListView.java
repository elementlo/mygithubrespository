package net.wash8.customview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by ncb-user on 2015/3/3.
 */
public class LinearLayoutForListView extends LinearLayout {
    public LinearLayoutForListView(Context context) {
        super(context);
    }
    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;

    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);

            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
        Log.i("countTAG", "" + count);
    }
}
