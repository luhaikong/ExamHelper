package com.lhk.exam.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhk.exam.R;
import com.lhk.exam.sqlite.Constant;

/**
 * 使用方法：
 * MyCursorAdapter myCursorAdapter = new MyCursorAdapter(this,cursor,CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
 * listView.setAdapter(myCursorAdapter);
 */
public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public MyCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * 表示创建适配器控件中每个item对应的view对象
     * @param context 上下文
     * @param cursor 数据源cursor对象
     * @param viewGroup 当前item的父布局
     * @return 每一项item的view对象
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,null);
        return view;
    }

    /**
     * 通过newView()方法确定了每个item展示view对象，在bindView()中对布局中的控件进行填充
     * @param view 由newView（）返回的每项view对象
     * @param context 上下文
     * @param cursor 数据源cursor对象
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv_id = view.findViewById(R.id.tv_id);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_age = view.findViewById(R.id.tv_age);

        tv_id.setText(cursor.getInt(cursor.getColumnIndex(Constant.TableUser._ID))+"");
        tv_name.setText(cursor.getString(cursor.getColumnIndex(Constant.TableUser.NAME)));
        tv_age.setText(cursor.getInt(cursor.getColumnIndex(Constant.TableUser.AGE))+"");
    }
}
