package com.ndngan.glide.gallery;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.ndngan.glide.gallery.data.MediaStoreData;

import java.util.Collections;
import java.util.List;

/**
 * Created by ndngan
 * Created on 10/15/17.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ListViewHolder>
        implements ListPreloader.PreloadSizeProvider<MediaStoreData>,
        ListPreloader.PreloadModelProvider<MediaStoreData> {

    private final List<MediaStoreData> data;
    private final int screenWidth;
    private final GlideRequest<Drawable> requestBuilder;

    private int[] actualDimensions;

    public GalleryAdapter(Context context, List<MediaStoreData> data, GlideRequests glideRequests) {
        this.data = data;
        requestBuilder = glideRequests.asDrawable().fitCenter();
        setHasStableIds(true);

        screenWidth = getScreenWidth(context);
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.gallery_item, parent, false);
        view.getLayoutParams().width = screenWidth;

        if (actualDimensions == null) {
            view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (actualDimensions == null) {
                        actualDimensions = new int[]{view.getWidth(), view.getHeight()};
                    }
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        MediaStoreData current = data.get(position);

        Key signature = new MediaStoreSignature(current.getMimeType(),
                current.getDateModified(), current.getOrientation());

        requestBuilder
                .clone()
                .signature(signature)
                .load(current.getUri())
                .into(holder.image);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getRowId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public List<MediaStoreData> getPreloadItems(int position) {
        return Collections.singletonList(data.get(position));
    }

    @Nullable
    @Override
    public RequestBuilder getPreloadRequestBuilder(MediaStoreData item) {
        MediaStoreSignature signature = new MediaStoreSignature(item.getMimeType(),
                item.getDateModified(), item.getOrientation());
        return requestBuilder
                .clone()
                .signature(signature)
                .load(item.getUri());
    }

    @Override
    public int[] getPreloadSize(MediaStoreData item, int adapterPosition, int perItemPosition) {
        return actualDimensions;
    }

    // Display#getSize(Point)
    @SuppressWarnings("deprecation")
    private static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        public ListViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
