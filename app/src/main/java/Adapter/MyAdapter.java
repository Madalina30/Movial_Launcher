package Adapter;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.movial.launcher.News;
import com.movial.launcher.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import Model.ListItem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //declarations
    private static Context context;
    private List<ListItem> listItem;
    static JSONObject obj;

    //constructor
    public MyAdapter(Context context, List listItem, JSONObject obj) {
        this.context = context;
        this.listItem = listItem;
        this.obj = obj;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create a view that gets its "parent" view and inflates the layout with the item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        //returning the new object constructor
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        //to see the items from the data source - combine resources
        ListItem item = listItem.get(position);
        holder.title.setText(item.getTitle());
        if (item.getDescription() == null)
            holder.description.setText("No description available.");
        holder.description.setText(item.getDescription());
        if (item.getImage() != null){
            Picasso.get().load(item.getImage()).into(holder.image);
        } else {
            Picasso.get().load(item.getImage()).resize(0,0).into(holder.image);
        }


    }

    @Override
    public int getItemCount() { //count on the view size
        return listItem.size();
    }

    //the item view - on click
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //declarations
        public TextView title;
        public TextView description;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            Log.d("news", "NU INTRA");
            //instantiate
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);


        }

        @Override
        public void onClick(View v) {
            String url = null;
            try {
                url = MyAdapter.obj.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            MyAdapter.context.startActivity(intent);

        }
    }
}
