package com.example.omdb.network.responce


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


open class Movie() : Parcelable {

    @SerializedName("Title")
    @Expose
    var title: String? = null
    @SerializedName("Year")
    @Expose
    var year: String? = null
    @SerializedName("Rated")
    @Expose
    var rated: String? = null
    @SerializedName("Released")
    @Expose
    var released: String? = null
    @SerializedName("Runtime")
    @Expose
    var runtime: String? = null
    @SerializedName("Genre")
    @Expose
    var genre: String? = null
    @SerializedName("Director")
    @Expose
    var director: String? = null
    @SerializedName("Writer")
    @Expose
    var writer: String? = null
    @SerializedName("Actors")
    @Expose
    var actors: String? = null
    @SerializedName("Plot")
    @Expose
    var plot: String? = null
    @SerializedName("Language")
    @Expose
    var language: String? = null
    @SerializedName("Country")
    @Expose
    var country: String? = null
    @SerializedName("Awards")
    @Expose
    var awards: String? = null
    @SerializedName("Poster")
    @Expose
    var poster: String? = null
    @SerializedName("Ratings")
    @Expose
    var ratings: List<Rating>? = null
    @SerializedName("Metascore")
    @Expose
    var metascore: String? = null
    @SerializedName("imdbRating")
    @Expose
    var imdbRating: String? = null
    @SerializedName("imdbVotes")
    @Expose
    var imdbVotes: String? = null
    @SerializedName("imdbID")
    @Expose
    var imdbID: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("DVD")
    @Expose
    var dVD: String? = null
    @SerializedName("BoxOffice")
    @Expose
    var boxOffice: String? = null
    @SerializedName("Production")
    @Expose
    var production: String? = null
    @SerializedName("Website")
    @Expose
    var website: String? = null
    @SerializedName("Response")
    @Expose
    var response: String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        year = parcel.readString()
        rated = parcel.readString()
        released = parcel.readString()
        runtime = parcel.readString()
        genre = parcel.readString()
        director = parcel.readString()
        writer = parcel.readString()
        actors = parcel.readString()
        plot = parcel.readString()
        language = parcel.readString()
        country = parcel.readString()
        awards = parcel.readString()
        poster = parcel.readString()
        ratings = parcel.createTypedArrayList(Rating)
        metascore = parcel.readString()
        imdbRating = parcel.readString()
        imdbVotes = parcel.readString()
        imdbID = parcel.readString()
        type = parcel.readString()
        dVD = parcel.readString()
        boxOffice = parcel.readString()
        production = parcel.readString()
        website = parcel.readString()
        response = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(rated)
        parcel.writeString(released)
        parcel.writeString(runtime)
        parcel.writeString(genre)
        parcel.writeString(director)
        parcel.writeString(writer)
        parcel.writeString(actors)
        parcel.writeString(plot)
        parcel.writeString(language)
        parcel.writeString(country)
        parcel.writeString(awards)
        parcel.writeString(poster)
        parcel.writeTypedList(ratings)
        parcel.writeString(metascore)
        parcel.writeString(imdbRating)
        parcel.writeString(imdbVotes)
        parcel.writeString(imdbID)
        parcel.writeString(type)
        parcel.writeString(dVD)
        parcel.writeString(boxOffice)
        parcel.writeString(production)
        parcel.writeString(website)
        parcel.writeString(response)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}
