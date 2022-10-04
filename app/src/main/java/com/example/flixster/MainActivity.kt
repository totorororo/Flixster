package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster.R.id
import com.example.flixster.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import okhttp3.Headers

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

class MainActivity : AppCompatActivity() {

    private val movies = mutableListOf<Movies>()

    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    private val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleAdapter = MovieRecyclerViewAdapter(this, movies)
        movieRecyclerView.adapter = articleAdapter

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        movieRecyclerView = findViewById(R.id.movie)

        movieRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            movieRecyclerView.addItemDecoration(dividerItemDecoration)
        }


        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1",
                params,
                object : JsonHttpResponseHandler()
                { //connect these callbacks to your API call

                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JSON
                    ) {

                        //TODO - Parse JSON into Models
                        // TODO: Create the parsedJSON
                        val parsedJson = createJson().decodeFromString(
                            SearchNewsResponse.serializer(),
                            json.jsonObject.toString()
                        )


                        // TODO: Do something with the returned json (contains article information)
                        parsedJson.response?.docs?.let { list ->
                            movies.addAll(list)
                        }
                        // TODO: Save the articles and reload the screen
                        parsedJson.response?.docs?.let { list ->
                            movies.addAll(list)

                            // Reload the screen
                            articleAdapter.notifyDataSetChanged()
                        }

                        // Look for this in Logcat:
                        Log.d("MoviesFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("MoviesFragment", errorResponse)
                        }
                    }
                }]
    }
}