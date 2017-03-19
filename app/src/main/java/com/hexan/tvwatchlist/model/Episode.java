package com.hexan.tvwatchlist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hexan.tvwatchlist.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;

/**
 * Created by james_000 on 3/15/2017.
 */

@Table(database = AppDatabase.class)
public class Episode extends BaseModel implements Parcelable {
    @PrimaryKey
    @Column
    @SerializedName("id")
    private int episodeId;

    @Column
    @SerializedName("name")
    private String name;

    @Column
    @SerializedName("overview")
    private String overview;

    @Column
    @SerializedName("season_number")
    private int seasonNumber;

    @Column
    @SerializedName("still_path")
    private String stillPath;

    @Column
    @SerializedName("vote_average")
    private float voteAverage;

    @Column
    @SerializedName("vote_count")
    private int voteCount;

    @Column
    @SerializedName("air_date")
    private String airDate;

    /*@SerializedName("crew")
    ArrayList<Crew> crew;*/

    @ForeignKey(stubbedRelationship = true)
    private Season season;

    protected Episode() {
    }

    protected Episode(Parcel in) {
        episodeId = in.readInt();
        name = in.readString();
        overview = in.readString();
        seasonNumber = in.readInt();
        stillPath = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        airDate = in.readString();
        season = in.readParcelable(Season.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(episodeId);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeInt(seasonNumber);
        dest.writeString(stillPath);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeString(airDate);
        dest.writeParcelable(season, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
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

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(String stillPath) {
        this.stillPath = stillPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
