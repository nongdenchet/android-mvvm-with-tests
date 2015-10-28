package apidez.com.android_mvvm_sample.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.model.entity.Place;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class PlacesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Place> mPlaces;

    public PlacesAdapter(Context context) {
        mContext = context;
        mPlaces = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_place, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PlaceViewHolder holder = (PlaceViewHolder) viewHolder;
        holder.itemView.setOnClickListener(v -> {});

        Place place = mPlaces.get(position);
        holder.name.setText(place.getName());
        Picasso.with(mContext)
                .load(place.getIcon())
                .placeholder(R.drawable.ic_place_36dp)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    // this view holder hold the view of one particular card
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.icon)
        ImageView icon;

        public PlaceViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /**
     * Update the list item
     */
    public void updatePlaces(List<Place> places) {
        mPlaces = places;
        notifyDataSetChanged();
    }
}
