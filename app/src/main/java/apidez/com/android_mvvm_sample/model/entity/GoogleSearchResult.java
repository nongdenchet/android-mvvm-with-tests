package apidez.com.android_mvvm_sample.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class GoogleSearchResult {
    @SerializedName("status")
    public String status;

    @SerializedName("results")
    public List<Place> results;
}
