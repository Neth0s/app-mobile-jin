package com.example.myapplication

import android.os.AsyncTask
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.appli_mobile_clemence_pierre.test.Event
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AsyncBitmapDL(var event: Event) : AsyncTask<ImageView?, Void?, Bitmap?>() {
    protected override fun doInBackground(vararg p0: ImageView?): Bitmap? {
        val url: URL
        var urlConnection: HttpURLConnection? = null
        var bm: Bitmap? = null
        try {
            url = URL(event.faceUrl)
            urlConnection = url.openConnection() as HttpURLConnection // Open
            val `in`: InputStream = BufferedInputStream(urlConnection.inputStream) // Stream
            bm = BitmapFactory.decodeStream(`in`)
            `in`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect()
        }
        event.image = bm
        p0[0]?.setImageBitmap(event.image)
        return bm
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        Log.i("CIO", "Image received !")
    }
}