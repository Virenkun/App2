package com.viren.ithappensiniit

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var currentImageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loadMeme(){

        progressBar.visibility = View.VISIBLE
        

        val queue = Volley.newRequestQueue(this)

        //creating a new request

        currentImageUrl  = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,currentImageUrl , null,
            Response.Listener{ response ->

                      val url = response.getString("url")
                      Glide.with(this).load(url).listener(object: RequestListener<Drawable>

                      {
                          override fun onLoadFailed(
                              e: GlideException?,
                              model: Any?,
                              target: Target<Drawable>?,
                              isFirstResource: Boolean
                          ): Boolean {
                              progressBar.visibility = View.GONE

                              return false
                          }

                          override fun onResourceReady(
                              resource: Drawable?,
                              model: Any?,
                              target: Target<Drawable>?,
                              dataSource: DataSource?,
                              isFirstResource: Boolean
                          ): Boolean {
                              progressBar.visibility = View.GONE

                              return false
                          }

                      }).into(memeImage)
            },
            { error ->
            }
        )

// Access the RequestQueue through your singleton class.
      queue.add(jsonObjectRequest)
    }

    fun nextMeme(view: android.view.View) {

        loadMeme()
    }
    fun shareMeme(view: android.view.View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey I got this meme from Ithappensiniit $currentImageUrl")
        val chooser = Intent.createChooser(intent, "share this...")
        startActivity(chooser)


    }
}