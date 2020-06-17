package com.nehal.seher

import ai.rever.goonj.Goonj
import ai.rever.goonj.models.Track
import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nehal.seher.utils.AppPreferences
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber


open class SeherApplication : Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
          //  FirebaseFirestore.setLoggingEnabled(true)
            Timber.plant(Timber.DebugTree())
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        AppPreferences.init(this)
        instance = this

     //   val builder = VmPolicy.Builder()
       // StrictMode.setVmPolicy(builder.build())

    }

    companion object {
        lateinit var instance: SeherApplication
    }

    private lateinit var disposable: Disposable

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onAppCreate() {

        RxJavaPlugins.setErrorHandler {
            Log.e("============>", it.localizedMessage ?: "empty")
        }

        Goonj.register<MainActivity>(this)

        Goonj.imageLoader = ::imageLoader

        Goonj.trackPreFetcher = ::trackFetcher

        Goonj.preFetchDistanceWithAutoplay = 2
        Goonj.preFetchDistanceWithoutAutoplay = 1

        disposable = Goonj.trackCompletionObservable.subscribe(::onTrackComplete)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onAppDestroy() {
        Log.e("=============>", "destroy")

        Goonj.unregister()
        disposable.dispose()
    }

    private fun imageLoader(track: Track, callback: (Bitmap?) -> Unit) {
        Glide.with(this).asBitmap().load(track.imageUrl)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap,
                                             transition: Transition<in Bitmap>?) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    callback(placeholder?.toBitmap())
                }
            })

    }

    private fun trackFetcher(callback: (List<Track>) -> Unit) {
        val fetched = arrayListOf<Track>()
        callback(fetched)
    }

    private fun onTrackComplete(track: Track) {
        /**
         * Could be used for
         *   i)   updating completed track to server or some database
         *   ii)  adding additional track to playlist after calculating left track count to play
         */
//        Log.e("===========>", track.title)
    }

}