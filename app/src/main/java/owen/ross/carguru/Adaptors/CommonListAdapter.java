package owen.ross.carguru.Adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import owen.ross.carguru.R;

public class CommonListAdapter extends RecyclerView.Adapter<CommonListAdapter.ViewHolder> {

    private String[] localDataSet;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvListItem;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvListItem = (TextView) view.findViewById(R.id.tvListItem);
        }

        public TextView getTextView() {
            return tvListItem;
        }
    }


    public CommonListAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_adapter_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and set the text
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}