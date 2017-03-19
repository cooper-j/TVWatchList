package com.hexan.tvwatchlist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hexan.tvwatchlist.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by james_000 on 3/15/2017.
 */

@Table(database = AppDatabase.class)
public class Season extends BaseModel implements Parcelable {
    @PrimaryKey
    @Column
    @SerializedName("id")
    private int seasonId;

    @Column
    @SerializedName("season_number")
    private int seasonNumber;

    @Column
    @SerializedName("name")
    private String name;

    @Column
    @SerializedName("overview")
    private String overview;

    @Column
    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("episodes")
    List<Episode> episodes;

    @ForeignKey(stubbedRelationship = true)
    private TVShow tvShow;

    protected Season() {
    }

    protected Season(Parcel in) {
        seasonId = in.readInt();
        seasonNumber = in.readInt();
        name = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        episodes = in.createTypedArrayList(Episode.CREATOR);
        tvShow = in.readParcelable(TVShow.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(seasonId);
        dest.writeInt(seasonNumber);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeTypedList(episodes);
        dest.writeParcelable(tvShow, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel in) {
            return new Season(in);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "episodes")
    public List<Episode> getEpisodes() {
        if (episodes == null || episodes.isEmpty()) {
            episodes = SQLite.select()
                    .from(Episode.class)
                    .where(Episode_Table.season_seasonId.eq(seasonId))
                    .queryList();
        }
        return episodes;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    public TVShow getTvShow() {
        return tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }
}
