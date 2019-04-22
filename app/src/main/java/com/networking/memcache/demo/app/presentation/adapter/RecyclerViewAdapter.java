package com.networking.memcache.demo.app.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.networking.memcache.demo.R;
import com.networking.memcache.demo.app.presentation.imagedetail.ImageDetailActivity;
import com.networking.memcache.demo.data.repository.unsplash.UnsplashEndpointImpl;
import com.networking.memcache.demo.domain.model.Photo;
import com.networking.memcache.demo.domain.model.UnsplashUser;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserDataHolder> {

    private List<UnsplashUser> userList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<UnsplashUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        return new UserDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserDataHolder holder, int position) {
        UnsplashUser user = userList.get(position);

        holder.tvFullName.setText(user.getFirstName() + " " + user.getLastName());
        holder.tvUserName.setText("Username: " + user.getUsername());
        holder.tvTotalLikes.setText("Total Likes: " + user.getTotalLikes());
        holder.tvTotalPhotos.setText("Total Photos: " + user.getPhotos().size());
        final UnsplashEndpointImpl endpoint = new UnsplashEndpointImpl();
        Observable.just(endpoint.loadImageAsBytes(user.getProfileImage().getLarge()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bytes -> Glide.with(context).load(bytes).into(holder.imvProfile));

        final ArrayList<String> images = new ArrayList<>();
        for (Photo photo : user.getPhotos()) {
            images.add(photo.getUrls().getRegular());
        }

        holder.imvProfile.setOnClickListener(
                v -> context.startActivity(ImageDetailActivity.startIntent(context, images, 0, true,
                        R.drawable.img_holder, true)));
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public class UserDataHolder extends RecyclerView.ViewHolder {

        CircleImageView imvProfile;
        TextView tvFullName;
        TextView tvUserName;
        TextView tvTotalLikes;
        TextView tvTotalPhotos;

        public UserDataHolder(View itemView) {
            super(itemView);
            imvProfile = itemView.findViewById(R.id.profile_image);
            tvFullName = itemView.findViewById(R.id.user_first_last_name);
            tvUserName = itemView.findViewById(R.id.username);
            tvTotalLikes = itemView.findViewById(R.id.total_likes);
            tvTotalPhotos = itemView.findViewById(R.id.total_photos);
        }
    }
}
