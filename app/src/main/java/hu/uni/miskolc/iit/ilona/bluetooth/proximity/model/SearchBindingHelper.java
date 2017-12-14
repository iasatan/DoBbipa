package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

/**
 * Created by iasatan on 2017.12.13..
 */

public class SearchBindingHelper extends BaseObservable {
    public ObservableField<String> searchTerm = new ObservableField<>();

    public SearchBindingHelper() {
    }
}
