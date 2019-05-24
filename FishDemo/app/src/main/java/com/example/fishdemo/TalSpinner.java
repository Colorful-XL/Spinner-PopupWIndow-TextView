package com.example.fishdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TalSpinner extends FrameLayout {

    static final String TAG = TalSpinner.class.getSimpleName();

    public TalSpinner(Context context) {
        super(context);
        init(context, null);
    }

    public TalSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TalSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    ViewHolder mViewHolder;
    InternalAdapter mAdapter;

    protected void init(Context context, AttributeSet attrs) {
        //data from xml
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TalSpinner);
        CharSequence[] shape = ta.getTextArray(R.styleable.TalSpinner_arrays);
        ta.recycle();


        LayoutInflater.from(context).inflate(R.layout.widget_talspinner, this);

        mViewHolder = new ViewHolder(this);
        mViewHolder.root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        mViewHolder.icon.setVisibility(VISIBLE);
        mViewHolder.root.setBackgroundResource(R.drawable.widget_talspinner_title_bg);

        mAdapter = new InternalAdapter();
        mPopWindow = new InternalPopWindow(context, mAdapter, this);

        // update view
        if (shape != null) {
            List list = new ArrayList();
            for (int i = 0; i < shape.length; i++) {
                TalSpinner.ItemBean bean = new TalSpinner.ItemBean();
                bean.name = shape[i].toString();
                list.add(bean);
            }
            setData(list);
        }


        setListener(new TalSpinner.IVisibilityListener() {
            @Override
            public void onShown() {
                mViewHolder.icon.setImageResource(R.drawable.ic_selecting);
            }

            @Override
            public void onHidden() {
                mViewHolder.icon.setImageResource(R.drawable.ic_down);
            }
        });

    }


    class ViewHolder {
        View root;
        TextView textView;
        ImageView icon;


        public ViewHolder(View view) {
            root = view;
            textView = view.findViewById(R.id.text);
            icon = view.findViewById(R.id.image_view);

        }

        public void updateView(Object data, int position) {
            Log.v(TAG, "updateView -> " + data + " position -> " + position);
            if (data != null) {
                if (textView.getParent().getParent() instanceof FrameLayout) {
                    textView.setText(((ItemBean) data).name);
                } else {
                    textView.setText(((ItemBean) data).name);
                    if (mData.getCurrentPosition() == position) {
                        textView.setTextColor(0xFF2A8AEE);

                    } else {
                        textView.setTextColor(0xFF474751);

                    }
                }
            }
        }

    }


    private InternalPopWindow mPopWindow;

    public void showPopWindow() {
        mPopWindow.show();
    }


    private class InternalAdapter extends BaseAdapter {

        public InternalAdapter() {

        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                convertView = layoutInflater.inflate(R.layout.widget_talspinner, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.updateView(mData.get(position), position);
            return convertView;
        }
    }

    private class InternalPopWindow extends PopupWindow {

        private ListView mListView;
        private View mAnchorView;


        public InternalPopWindow(Context context, InternalAdapter adapter, View anchorView) {
            super(LayoutInflater.from(context).inflate(R.layout.widget_talspinner_popwindow, null), ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            View view = getContentView();
            mListView = view.findViewById(R.id.listview);
            mListView.setAdapter(adapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mData.setCurrentPosition(position);
                    mAdapter.notifyDataSetChanged();
                    mPopWindow.dismiss();
                }
            });


            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {

                    // update above
                    mViewHolder.updateView(mData.get(mData.getCurrentPosition()), mData.getCurrentPosition());

                    if (mListener != null) {
                        mListener.onHidden();
                    }
                }
            });

            mAnchorView = anchorView;

            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setOutsideTouchable(true);
            setTouchable(true);
        }


        public void show() {
            if (isShowing()) {
                return;
            }
            showAsDropDown(mAnchorView, dp2px(getContext(), 6) * -1, dp2px(getContext(), 2));
            if (mListener != null) {
                mListener.onShown();
            }
        }
    }


    private static class InternalList extends ArrayList {

        private int mCurrentPosition;


        public void setCurrentPosition(int position) {
            mCurrentPosition = position;
        }


        public int getCurrentPosition() {
            return mCurrentPosition;
        }


        public Object getCurrentPositionData() {
            if (getCurrentPosition() < size()) {
                return get(getCurrentPosition());
            }
            return null;
        }
    }

    /**
     * Our data for showing
     */
    public InternalList mData = new InternalList();


    /**
     * Updating your data ??
     *
     * @param data
     */
    public void setData(List<?> data) {
        Log.v(TAG, "setData -> " + data);
        if (data == null) {
            return;
        }
        mData.clear();
        mData.addAll(data);
        mData.setCurrentPosition(0);

        //update
        // update title
        mViewHolder.updateView(mData.getCurrentPositionData(), mData.getCurrentPosition());

        // update popwindow
        mAdapter.notifyDataSetChanged();
    }


    public static class ItemBean {

        public String name;

    }


    /**
     * dp 转 px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, int dp) {
        return (int) (context.getResources().getDisplayMetrics().density * dp + .5f);
    }


    public interface IVisibilityListener {

        void onShown();

        void onHidden();
    }

    private IVisibilityListener mListener;

    public void setListener(IVisibilityListener listener) {
        mListener = listener;

        // trigger
        if (mPopWindow != null && mListener != null) {
            if (mPopWindow.isShowing()) {
                mListener.onShown();
            } else {
                mListener.onHidden();
            }
        }
    }

}
