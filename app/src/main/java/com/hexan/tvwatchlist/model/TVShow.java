package com.hexan.tvwatchlist.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.hexan.tvwatchlist.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by james_000 on 3/15/2017.
 */

@Table(database = AppDatabase.class)
public class TVShow extends BaseModel implements Parcelable {
    @PrimaryKey
    @Column
    @SerializedName("id")
    private int tvShowId;

    @Column
    @SerializedName("name")
    private String name;

    @Column
    @SerializedName("overview")
    private String overview;

    @Column
    @SerializedName("last_air_date")
    private String lastAirDate;

    @Column
    @SerializedName("first_air_date")
    private String firstAirDate;

    @Column
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @Column
    @SerializedName("number_of_episodes")
    private int numberOfEpisodes;

    @Column
    @SerializedName("poster_path")
    private String posterPath;

    @Column
    @SerializedName("backdrop_path")
    private String backdropPath;

    @Column
    @SerializedName("status")
    private String status;

    @Column
    @SerializedName("type")
    private String type;

    @Column
    @SerializedName("vote_average")
    private float voteAverage;

    @Column
    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("genres")
    List<Genre> genres;

    @SerializedName("seasons")
    List<Season> seasons;

    @Column
    private boolean isFollowing = false;

    public TVShow(){
    }

    protected TVShow(Parcel in) {
        tvShowId = in.readInt();
        name = in.readString();
        overview = in.readString();
        lastAirDate = in.readString();
        firstAirDate = in.readString();
        numberOfSeasons = in.readInt();
        numberOfEpisodes = in.readInt();
        posterPath = in.readString();
        backdropPath = in.readString();
        status = in.readString();
        type = in.readString();
        voteAverage = in.readFloat();
        voteCount = in.readInt();
        seasons = in.createTypedArrayList(Season.CREATOR);
        isFollowing = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvShowId);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(lastAirDate);
        dest.writeString(firstAirDate);
        dest.writeInt(numberOfSeasons);
        dest.writeInt(numberOfEpisodes);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
        dest.writeTypedList(seasons);
        dest.writeByte((byte) (isFollowing ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
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

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.LOAD, OneToMany.Method.DELETE}, variableName = "genres")
    public List<Genre> getGenres() {
        if (genres == null) {
            List<Genre_TVShow> list = SQLite.select().from(Genre_TVShow.class)
                    .where(Genre_TVShow_Table.tVShow_tvShowId.eq(tvShowId))
                    .queryList();
            genres = new ArrayList<>();
            for (Genre_TVShow item: list) {
                genres.add(item.getGenre());
            }
        }
        return genres;
    }

    public void setGenres(List<Genre> genres){
        this.genres = genres;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "seasons")
    public List<Season> getSeasons() {
        if (seasons == null || seasons.isEmpty()) {
            seasons = SQLite.select()
                    .from(Season.class)
                    .where(Season_Table.tvShow_tvShowId.eq(tvShowId))
                    .queryList();
        }
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    @Override
    public boolean save() {
        if (genres != null && !genres.isEmpty())
            for (Genre genre : genres)
            {
                genre.save();
                Genre_TVShow tvshowGenre = new Genre_TVShow();
                tvshowGenre.setGenre(genre);
                tvshowGenre.setTVShow(this);
                tvshowGenre.save();
            }

        return super.save();
    }

    @Override
    public boolean update() {
        if (genres != null && !genres.isEmpty())
            for (Genre genre : genres)
            {
                Genre_TVShow tvshowGenre = new Genre_TVShow();
                tvshowGenre.setGenre(genre);
                tvshowGenre.setTVShow(this);
                tvshowGenre.save();
            }
        return super.update();
    }
}
