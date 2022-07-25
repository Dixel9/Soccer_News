package me.dio.soccernews.ui.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.dio.soccernews.databinding.NewsItemBinding;
import me.dio.soccernews.domain.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final View.OnClickListener favoriteListener;
    private List<News> news;

    public NewsAdapter(List<News> news, View.OnClickListener favoriteListener) {
        this.news =news;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = this.news.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvDescription.setText(news.description);
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbnail);
        holder.binding.btOpenLink.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            holder.itemView.getContext().startActivity(i);
        });
        //implementação da funcionalidade de compartilhar.
        holder.binding.ivShare.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i .putExtra(android.content.Intent.EXTRA_SUBJECT, news.title);
            i.putExtra(Intent.EXTRA_TEXT,news.link);
            holder.itemView.getContext().startActivity(Intent.createChooser(i, "Share via"));
        });
        //implementação da funcionalidade de compartilhar. (o evento será instanciado pelo fragmento)
        holder.binding.ivFavorite.setOnClickListener(this.favoriteListener);
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
