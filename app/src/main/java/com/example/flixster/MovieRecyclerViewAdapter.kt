package com.example.flixster

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixster.R.id

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"

class MovieRecyclerViewAdapter (private val context: Context, private val movies: List<Movies>,
                                private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)

    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val mMovieTitle: TextView = itemView.findViewById<View>(id.movie_title) as TextView
        val mMovieDescription: TextView = itemView.findViewById<View>(id.movie_description) as TextView
        val mMovieImage: ImageView = itemView.findViewById<View>(id.movie_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = movies[adapterPosition]
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailMovie::class.java)
            //intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500" + movie.movieImageUrl)
            .centerInside()
            .into(holder.mMovieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}