package com.nehal.seher.ui.fragments

import ai.rever.goonj.GoonjPlayer
import ai.rever.goonj.GoonjPlayerState
import ai.rever.goonj.models.Track
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nehal.seher.R
import com.nehal.seher.databinding.QuraanListenFragmentBinding
import com.nehal.seher.viewmodels.QuraanReadViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class QuraanListenFragment : Fragment(), GoonjPlayer {
    private lateinit var viewModel: QuraanReadViewModel
    private lateinit var binding: QuraanListenFragmentBinding
    private var url: String? = null
    private var title: String? = null
    private var id: Int? = null
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuraanListenFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuraanReadViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.let { QuraanListenFragmentArgs.fromBundle(it).id }
        url = arguments?.let { QuraanListenFragmentArgs.fromBundle(it).url }
        title = arguments?.let { QuraanListenFragmentArgs.fromBundle(it).title }

        setListener()
        customizeNotification()
        setupPlayer()
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable += playerStateFlowable.subscribe {
            binding.audioPlayerPlayPauseToggleBtn.isChecked = it != GoonjPlayerState.PLAYING
//            e("===========>", "$it")
        }

        compositeDisposable += autoplayFlowable.subscribe {
            binding.audioPlayerAutoplaySwitch.isChecked = it
        }

        compositeDisposable += currentTrackFlowable.subscribe(::onPlayingTrackChange)

        compositeDisposable += trackListFlowable.subscribe {
           // binding.tvLoadedTrackCount.text = "Loaded track count: ${it.size}"
        }

    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private var knownTrack: Track = Track()

    private fun onPlayingTrackChange(currentItem: Track) {
        if (knownTrack.id != currentItem.id) {
            currentItem.load { binding.audioPlayerAlbumArtIV.setImageBitmap(it) }
            binding.audioPlayerAlbumTitleTv.text = currentItem.title
           // audioPlayerAlbumArtistTv.text = currentItem.artistName
        }
        binding.audioPlayerCurrentPosition.text = (currentItem.state.position / 1000).toString()
        binding.audioPlayerContentDuration.text = (currentItem.state.duration / 1000).toString()
        binding.audioPlayerProgressBar.progress =
            ((currentItem.state.position.toDouble() / currentItem.state.duration.toDouble()) * 100.0).toInt()
        knownTrack = currentItem
    }

    private fun setListener() {

        binding.audioPlayerPlayPauseToggleBtn?.apply {
            setOnClickListener {
                if (isChecked) {
                    pause()
                } else {
                    resume()
                }
            }
        }

        binding.audioPlayerForward10s.setOnClickListener {
            seekTo(trackPosition + 10000)
        }

        binding.audioPlayerRewind10s.setOnClickListener {
            seekTo(trackPosition - 10000)
        }

        binding.audioPlayerAutoplaySwitch.apply {
            setOnClickListener {
                autoplay = isChecked
            }
        }

        binding.audioPlayerSkipNext.setOnClickListener {
            skipToNext()
        }

        binding.audioPlayerSkipPrev.setOnClickListener {
            skipToPrevious()
        }
    }

    private fun customizeNotification() {
        customizeNotification(
            true, true,
            10000, 5000, R.mipmap.ic_launcher
        )
    }

    private fun setupPlayer() {
        startNewSession()
        addTrack(Track(id.toString(), "$url?raw=true", title!!, "","https://github.com/mdhaider/quraan-sharif/blob/master/img4.webp?raw=true"))
        resume()
    }
}
