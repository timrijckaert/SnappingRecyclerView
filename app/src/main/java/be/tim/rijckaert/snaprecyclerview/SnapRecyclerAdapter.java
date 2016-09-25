package be.tim.rijckaert.snaprecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import be.tim.rijckaert.snaprecyclerview.SnapRecyclerAdapter.RecyclerViewHolder;

public class SnapRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Article> items;

    public SnapRecyclerAdapter(Context context, List<Article> items) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.article_item, parent, false);

        return new RecyclerViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final Article article = items.get(position);

        Glide.with(context).load(article.getImage()).into(holder.image);
        holder.articleTitle.setText(article.getTitle());
        holder.itemView.setTag(article);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView articleTitle;

        private RecyclerViewHolder(final View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.article_image);
            articleTitle = (TextView) v.findViewById(R.id.article_title);
        }
    }
}