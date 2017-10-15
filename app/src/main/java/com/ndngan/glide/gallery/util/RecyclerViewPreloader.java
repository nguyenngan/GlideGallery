package com.ndngan.glide.gallery.util;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.ListPreloader.PreloadModelProvider;
import com.bumptech.glide.ListPreloader.PreloadSizeProvider;
import com.bumptech.glide.RequestManager;

/**
 * Created by ndngan
 * Created on 10/15/17.
 */

public final class RecyclerViewPreloader<T> extends RecyclerView.OnScrollListener {
    private final RecyclerToListViewScrollListener recyclerScrollListener;

    public RecyclerViewPreloader(Activity activity,
                                 ListPreloader.PreloadModelProvider<T> preloadModelProvider,
                                 ListPreloader.PreloadSizeProvider<T> preloadDimensionProvider,
                                 int maxPreload) {
        this(Glide.with(activity), preloadModelProvider, preloadDimensionProvider, maxPreload);
    }

    public RecyclerViewPreloader(FragmentActivity fragmentActivity,
                                 PreloadModelProvider<T> preloadModelProvider,
                                 PreloadSizeProvider<T> preloadDimensionProvider,
                                 int maxPreload) {
        this(Glide.with(fragmentActivity), preloadModelProvider, preloadDimensionProvider, maxPreload);
    }

    public RecyclerViewPreloader(Fragment fragment,
                                 PreloadModelProvider<T> preloadModelProvider,
                                 PreloadSizeProvider<T> preloadDimensionProvider,
                                 int maxPreload) {
        this(Glide.with(fragment), preloadModelProvider, preloadDimensionProvider, maxPreload);
    }

    public RecyclerViewPreloader(android.support.v4.app.Fragment fragment,
                                 PreloadModelProvider<T> preloadModelProvider,
                                 PreloadSizeProvider<T> preloadDimensionProvider,
                                 int maxPreload) {
        this(Glide.with(fragment), preloadModelProvider, preloadDimensionProvider, maxPreload);
    }

    public RecyclerViewPreloader(RequestManager requestManager,
                                 PreloadModelProvider<T> preloadModelProvider,
                                 PreloadSizeProvider<T> preloadDimensionProvider,
                                 int maxPreload) {

        ListPreloader<T> listPreloader = new ListPreloader<>(requestManager, preloadModelProvider,
                preloadDimensionProvider, maxPreload);
        recyclerScrollListener = new RecyclerToListViewScrollListener(listPreloader);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        recyclerScrollListener.onScrolled(recyclerView, dx, dy);
    }
}
