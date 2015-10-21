package apidez.com.android_mvvm_sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class Place implements Parcelable {
    @SerializedName("icon")
    private String icon;

    @SerializedName("place_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("types")
    ArrayList<String> types;

    protected Place(Parcel in) {
        icon = in.readString();
        id = in.readString();
        name = in.readString();
        types = in.createStringArrayList();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeStringList(types);
    }
}
