package me.hybridtheory.addresspicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

/**
 * CountyPicker
 * <p/>
 * Created by shenf on 2016/1/12.
 */
public class AddressPicker extends LinearLayout {

    public interface OnAddressChangeListener {
        void onAddressChanged(int province, int city, int county);
    }

    /**
     * The resource id for the default layout.
     */
    private static final int DEFAULT_LAYOUT_RESOURCE_ID = R.layout.address_picker;

    private AddressPickerAdapter mAdapter;

    private NumberPicker mProvincePicker;
    private NumberPicker mCityPicker;
    private NumberPicker mCountyPicker;

    private OnProvinceChangeListener mOnProvinceChangeListener = new OnProvinceChangeListener();
    private OnCityChangeListener mOnCityChangeListener = new OnCityChangeListener();
    private OnCountyChangeListener mOnCountyChangeListener = new OnCountyChangeListener();

    private OnAddressChangeListener mOnAddressChangeListener;

    public AddressPicker(Context context) {
        this(context, null);
    }

    public AddressPicker(Context context, AttributeSet attrs) {
//        this(context, attrs, R.attr.countyPickerStyle);
        this(context, attrs, 0);
    }

    public AddressPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AddressPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // process style attributes
        final TypedArray attributesArray = context.obtainStyledAttributes(
                attrs, R.styleable.AddressPicker, defStyleAttr, defStyleRes);
        final int layoutResId = attributesArray.getResourceId(
                R.styleable.AddressPicker_internalLayout, DEFAULT_LAYOUT_RESOURCE_ID);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutResId, this, true);

        mProvincePicker = (NumberPicker) findViewById(R.id.province_picker_);
        mCityPicker = (NumberPicker) findViewById(R.id.city_picker_);
        mCountyPicker = (NumberPicker) findViewById(R.id.county_picker_);

        mProvincePicker.setOnValueChangedListener(mOnProvinceChangeListener);
        mCityPicker.setOnValueChangedListener(mOnCityChangeListener);
        mCountyPicker.setOnValueChangedListener(mOnCountyChangeListener);

        attributesArray.recycle();
    }

    public void setAdapter(AddressPickerAdapter adapter) {
        mAdapter = adapter;

        String[] displayedValues = mAdapter.getProvinceNames();
        mProvincePicker.setMinValue(0);
        mProvincePicker.setMaxValue(displayedValues.length - 1);
        mProvincePicker.setDisplayedValues(displayedValues);
        mProvincePicker.setValue(0);
        mOnProvinceChangeListener.onValueChange(mProvincePicker, 0, 0);
    }

    public void setOnCountyChangeListener(OnAddressChangeListener listener) {
        mOnAddressChangeListener = listener;
    }

    public int getSelectedProvince() {
        return mAdapter.getProvinceId(mProvincePicker.getValue());
    }

    public int getSelectedCity() {
        int province = getSelectedProvince();
        return mAdapter.getCityId(province, mCityPicker.getValue());
    }

    public int getSelectedCounty() {
        int province = getSelectedProvince();
        int city = getSelectedCity();
        return mAdapter.getCountyId(province, city, mCountyPicker.getValue());
    }

    /**
     *
     */
    private class OnProvinceChangeListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            int province = getSelectedProvince();
            String[] displayedValues = mAdapter.getCityNames(province);
            mCityPicker.setMinValue(0);
            mCityPicker.setMaxValue(displayedValues.length - 1);
            mCityPicker.setDisplayedValues(displayedValues);
            mCityPicker.setValue(0);
            mOnCityChangeListener.onValueChange(mCityPicker, 0, 0);
        }
    }

    /**
     *
     */
    private class OnCityChangeListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            int province = getSelectedProvince();
            int city = getSelectedCity();
            String[] displayedValues = mAdapter.getCountyNames(province, city);
            mCountyPicker.setMinValue(0);
            mCountyPicker.setMaxValue(displayedValues.length - 1);
            mCountyPicker.setDisplayedValues(displayedValues);
            mCountyPicker.setValue(0);
            mOnCountyChangeListener.onValueChange(mCountyPicker, 0, 0);
        }
    }

    /**
     *
     */
    private class OnCountyChangeListener implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (null != mOnAddressChangeListener) {
                mOnAddressChangeListener.onAddressChanged(getSelectedProvince(), getSelectedCity(), getSelectedCounty());
            }
        }
    }
}
