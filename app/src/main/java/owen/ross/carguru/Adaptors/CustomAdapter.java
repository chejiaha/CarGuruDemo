package owen.ross.carguru.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import owen.ross.carguru.Models.Car;
import owen.ross.carguru.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Car> localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCarItemTitle;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvCarItemTitle = (TextView) view.findViewById(R.id.tvCarItemTitle);
        }

        public TextView getTextView() {
            return tvCarItemTitle;
        }
    }


    public CustomAdapter(ArrayList<Car> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_car_item, viewGroup, false);

        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvCarItemTitle = view.findViewById(R.id.tvCarItemTitle);
                Toast.makeText(view.getContext(), tvCarItemTitle.getText() + "HEELLOO" , Toast.LENGTH_SHORT).show();
            }
        });

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet.get(position).getMake() + localDataSet.get(position).getModel() + localDataSet.get(position).getTrim() + localDataSet.get(position).getYear());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}