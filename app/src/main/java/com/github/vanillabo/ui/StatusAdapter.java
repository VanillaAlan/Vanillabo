package com.github.vanillabo.ui;

import com.bumptech.glide.Glide;
import com.github.vanillabo.R;
import com.github.vanillabo.model.Status;
import com.github.vanillabo.ui.widget.BoxGridLayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alan on 16/4/18.
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    public static class StatusViewHolder extends RecyclerView.ViewHolder {
        View item;
        @Bind(R.id.civ_avatar) CircleImageView civAvatar;
        @Bind(R.id.tv_text) TextView tvText;
        @Bind(R.id.grid_image) BoxGridLayout picsLayout;
        @Bind(R.id.tv_repost_count) TextView tvRepost;
        @Bind(R.id.tv_comment_count) TextView tvComment;
        @Bind(R.id.tv_thumb_count) TextView tvAttitude;

        public StatusViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    private List<Status> mStatuses;

    private Context mContext;

    public StatusAdapter(Context context, List<Status> statuses) {
        mContext = context;
        mStatuses = statuses;
    }

    @Override
    public StatusAdapter.StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_status_view, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusAdapter.StatusViewHolder holder, int position) {
        Status status = mStatuses.get(position);
        holder.item.setTag(position);
        Glide.with(mContext)
                .load(status.user.avatar_large)
                .crossFade()
                .placeholder(R.drawable.ic_avatar_def)
                .fallback(R.drawable.ic_avatar_def)
                .into(holder.civAvatar);
        holder.tvText.setText(status.text);
        holder.tvRepost.setText(status.reposts_count + "");
        holder.tvComment.setText(status.comments_count + "");
        holder.tvAttitude.setText(status.attitudes_count + "");
    }

    @Override
    public int getItemCount() {
        return mStatuses == null ? 0 : mStatuses.size();
    }
}
