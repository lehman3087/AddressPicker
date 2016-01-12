package me.hybridtheory.addresspicker;

/**
 * Created by shenf on 2016/1/12.
 */
public interface AddressPickerAdapter {

    String [] getProvinceNames();

    String [] getCityNames(int provinceId);

    String [] getCountyNames(int provinceId, int cityId);

    int getProvinceId(int provinceIndex);

    int getCityId(int provinceId, int cityIndex);

    int getCountyId(int provinceId, int cityId, int countyIndex);
}
