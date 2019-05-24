//package com.example.fishdemo;
//
///**
// * This is a Spinner customized for ClassRoom board management.
// */
//public  class Test extends Spinner {
//
//    private class Adapter extends BaseAdapter {
//        private List<Clazz> mList = new ArrayList<>();
//        private int mSelectedPosition;
//
//        public Adapter(List list) {
//            setData(list);
//        }
//
//
//        public void setData(List<Clazz> list) {
//            if(list != null) {
//                mList.clear();
//                mList.addAll(list);
//            }
//        }
//
//        public void setSelectedPosition(int position) {
//            mSelectedPosition = position;
//        }
//
//        public int getSelectedPosition() {
//            return mSelectedPosition;
//        }
//
//        @Override
//        public int getCount() {
//            return mList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//                convertView = layoutInflater.inflate(R.layout.item_spinner, null);
//            }
//
//            if (convertView != null) {
//                TextView mTextView = convertView.findViewById(R.id.text);
//                ImageView mImageView = convertView.findViewById(R.id.image_view);
//                mTextView.setText(mList.get(position).getName());
//                if (mList.get(position).getSelected()) {
//                    mTextView.setTextColor(0xFF2A8AEE);
//                    mImageView.setImageAlpha(255);
//                } else {
//                    mTextView.setTextColor(0xFF474751);
//                    mImageView.setImageAlpha(0);
//                }
//            }
//            return convertView;
//
//        }
//
//    }
//
//    public TalSpinner(Context context) {
//        super(context);
//        init(context,null);
//    }
//
//    public TalSpinner(Context context, int mode) {
//        super(context, mode);
//        init(context,null);
//    }
//
//    public TalSpinner(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context,attrs);
//    }
//
//    public TalSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context,attrs);
//    }
//
//
//    Adapter mAdapter;
//
//    public void init(Context context, AttributeSet attributeSet) {
//
//        mAdapter = new Adapter(null);
//
//
////        mAdapter = new Adapter(this, mList);
//        //mAdapter.setDropDownViewResource(R.layout.item_spinner);
//        //mAdapter.setDropDownViewResource(R.layout.item_drop);
//
//        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //mImageView.setImageDrawable(getDrawable(R.drawable.ic_launcher_foreground));
//
//                ((Clazz) mAdapter.getItem(position)).setSelected(true);
//
//                if (mAdapter.getSelectedPosition() != position) {
//                    ((Clazz) mAdapter.getItem(mAdapter.getSelectedPosition())).setSelected(false);
//                    mAdapter.setSelectedPosition(position);
//                    // Toast.makeText(MainActivity.this, "okok", Toast.LENGTH_SHORT).show();
//                }
//
//
////                    Toast.makeText(view.getContext(), "okok", Toast.LENGTH_SHORT).show();
//
//                Log.d(TAG,"itemSelected");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                //变下
//                // mImageView.setImageResource(R.mipmap.ic_launcher);
//                Log.d(TAG,"notingSelected");
//            }
//        });
//
//
//        setAdapter(mAdapter);
//
//
//
//
//        // xxxx
//        ListPopupWindow popupWindow = getPopWindow();
//        if(popupWindow != null) {
//            Log.e(TAG,"popupWindow -> " + popupWindow);
//
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    if(mCallback != null) {
//                        mCallback.onHidden();
//                    }
//                }
//            });
//
//
//
//
//
//        }
//
//    }
//
//
//    /**
//     * Listener for pop window visibility.
//     */
//    public interface ICallback {
//
//        /**
//         * Called when shown
//         */
//        void onShown();
//
//        /**
//         * Called when dismissed
//         */
//        void onHidden();
//    }
//
//
//
//    private ICallback mCallback;
//
//    public void setCallback(ICallback callback) {
//        mCallback = callback;
//    }
//
//
//    @Override
//    public boolean performClick() {
//        Log.v(TAG,"performClick");
//        boolean result = super.performClick();
//        if(mCallback != null) {
//            if(isShown()) {
//                mCallback.onShown();
//            }
//        }
//        return result;
//    }
//
//
//
//    public void setData(List<Clazz> data) {
//        mAdapter.setData(data);
//        mAdapter.notifyDataSetChanged();
//    }
//
//
//
//    private ListPopupWindow getPopWindow() {
//        try {
//            Field field = Spinner.class.getDeclaredField("mPopup");
//            field.setAccessible(true);
//            Object filedObj = field.get(this);
//            if(filedObj instanceof ListPopupWindow) {
//                return (ListPopupWindow) filedObj;
//            }
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//}