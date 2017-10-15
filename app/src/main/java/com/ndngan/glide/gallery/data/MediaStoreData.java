package com.ndngan.glide.gallery.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ndngan
 * Created on 10/15/17.
 */

public class MediaStoreData implements Parcelable {

    public enum Type {
        VIDEO,
        IMAGE,
    }

    public static final Creator<MediaStoreData> CREATOR = new Creator<MediaStoreData>() {
        @Override
        public MediaStoreData createFromParcel(Parcel source) {
            return new MediaStoreData(source);
        }

        @Override
        public MediaStoreData[] newArray(int size) {
            return new MediaStoreData[size];
        }
    };

    private final long rowId;
    private final Uri uri;
    private final String mimeType;
    private final long dateModified;
    private final int orientation;
    private final Type type;
    private final long dateTaken;

    public MediaStoreData(long rowId, Uri uri, String mimeType, long dateTaken, long dateModified,
                          int orientation, Type type) {
        this.rowId = rowId;
        this.uri = uri;
        this.dateModified = dateModified;
        this.mimeType = mimeType;
        this.orientation = orientation;
        this.type = type;
        this.dateTaken = dateTaken;
    }

    public long getRowId() {
        return rowId;
    }

    public Uri getUri() {
        return uri;
    }

    public String getMimeType() {
        return mimeType;
    }

    public long getDateModified() {
        return dateModified;
    }

    public int getOrientation() {
        return orientation;
    }

    public Type getType() {
        return type;
    }

    public long getDateTaken() {
        return dateTaken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.rowId);
        dest.writeParcelable(this.uri, flags);
        dest.writeString(this.mimeType);
        dest.writeLong(this.dateModified);
        dest.writeInt(this.orientation);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeLong(this.dateTaken);
    }

    protected MediaStoreData(Parcel in) {
        this.rowId = in.readLong();
        this.uri = in.readParcelable(Uri.class.getClassLoader());
        this.mimeType = in.readString();
        this.dateModified = in.readLong();
        this.orientation = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.dateTaken = in.readLong();
    }
}
