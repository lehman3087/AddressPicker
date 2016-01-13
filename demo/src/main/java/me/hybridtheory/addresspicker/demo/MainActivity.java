package me.hybridtheory.addresspicker.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.hybridtheory.addresspicker.AddressPicker;
import me.hybridtheory.addresspicker.AddressPickerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddressPicker addressPicker = (AddressPicker) findViewById(R.id.county_picker);
        addressPicker.setAdapter(new CountyPickerAdapterImpl());
        addressPicker.setOnCountyChangeListener(new AddressPicker.OnAddressChangeListener() {
            @Override
            public void onAddressChanged(int province, int city, int county) {
                Log.d(TAG, String.format("%d, %d, %d", province, city, county));
            }
        });
    }

    /**
     *
     */
    private class CountyPickerAdapterImpl implements AddressPickerAdapter {

        @Override
        public String[] getProvinceNames() {
            String[] provinceNames = new String[30];
            for (int i = 0; i < 30; i++) {
                provinceNames[i] = "Province " + i;
            }
            return provinceNames;
        }

        @Override
        public String[] getCityNames(int provinceId) {
            String[] provinceNames = new String[30];
            for (int i = 0; i < 30; i++) {
                provinceNames[i] = "City " + i;
            }
            return provinceNames;
        }

        @Override
        public String[] getCountyNames(int provinceId, int cityId) {
            String[] provinceNames = new String[30];
            for (int i = 0; i < 30; i++) {
                provinceNames[i] = "County " + i;
            }
            return provinceNames;
        }

        @Override
        public int getProvinceId(int provinceIndex) {
            return provinceIndex;
        }

        @Override
        public int getCityId(int provinceId, int cityIndex) {
            return cityIndex;
        }

        @Override
        public int getCountyId(int provinceId, int cityId, int countyIndex) {
            return countyIndex;
        }
    }
}
