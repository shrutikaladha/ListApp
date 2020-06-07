package co.inmobi.listapp.ui.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.inmobi.listapp.databinding.ItemListBinding;
import co.inmobi.listapp.model.Post;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {
    private List<Post> postsList;

    public void setPosts(List<Post> postsList) {
        this.postsList = postsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ItemListBinding itemBinding =
                ItemListBinding.inflate(layoutInflater, parent, false);
        return new PostListViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder holder, int position) {
        Post post = postsList.get(position);
        holder.itemListBinding.tvTitle.setText(post.getTitle());
        holder.itemListBinding.tvContent.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return postsList != null ? postsList.size() : 0;
    }

    class PostListViewHolder extends RecyclerView.ViewHolder {
        ItemListBinding itemListBinding;

        public PostListViewHolder(ItemListBinding itemBinding) {
            super(itemBinding.getRoot());
            itemListBinding = itemBinding;
        }
    }
}
