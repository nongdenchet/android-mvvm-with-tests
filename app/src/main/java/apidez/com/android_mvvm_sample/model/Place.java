package apidez.com.android_mvvm_sample.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

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
    List<String> types;

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

    private Place(String icon, String id, String name, List<String> types) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.types = types;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTypes() {
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

    public static class Builder {
        private String id = UUID.randomUUID().toString();
        private String icon;
        private String name;
        List<String> types;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public Builder types(List<String> types) {
            this.types = types;
            return this;
        }

        public Place build() {
            return new Place(icon, id, name, types);
        }
    }
}
