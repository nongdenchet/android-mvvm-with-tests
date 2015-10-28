package apidez.com.android_mvvm_sample.model.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class Purchase {

    @SerializedName("creditCard")
    private String creditCard;

    @SerializedName("email")
    private String email;

    public Purchase() {
    }

    public Purchase(String creditCard, String email) {
        this.creditCard = creditCard;
        this.email = email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
