package com.nehal.seher.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nehal.seher.R
import com.nehal.seher.SeherApplication
import com.nehal.seher.databinding.PosterDetailFragmentBinding
import com.nehal.seher.model.Poster
import kotlinx.android.synthetic.main.layout_urdu_poster_list_item.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.roundToInt


class PosterDetailFragment : Fragment() {
    private lateinit var binding: PosterDetailFragmentBinding
    private lateinit var poster: Poster

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PosterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        poster = arguments?.let { PosterDetailFragmentArgs.fromBundle(it).item }!!

        binding.posterItem.text= poster.description
        binding.posterItem.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.4f))
        Glide.with(this)
            .load("${poster.imgUrl}?raw=true")
            .fitCenter()
            .into(binding.imgPosterDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_poster_details, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share -> shareImage()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun getLocalBitmapUri(imageView: ImageView): Uri? {
        // Get Bitmap from ImageView drawable
        val drawable: Drawable = imageView.drawable
        val bitmap: Bitmap
        bitmap = if (drawable is BitmapDrawable) {
            (imageView.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }
        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {
            val file = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()

            bmpUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(
                    SeherApplication.instance,
                    SeherApplication.instance.packageName+ ".provider",
                    file
                )
            } else {
                Uri.fromFile(file)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    private fun shareImage() {
        val bmpUri = getLocalBitmapUri(binding.imgPosterDetail)
        val sharingIntent = Intent(Intent.ACTION_SEND)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        sharingIntent.type = "image/.*";
        sharingIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        sharingIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
    }


    fun getColorWithAlpha(color: Int, ratio: Float): Int {
        var newColor = 0
        val alpha = (Color.alpha(color) * ratio).roundToInt()
        val r: Int = Color.red(color)
        val g: Int = Color.green(color)
        val b: Int = Color.blue(color)
        newColor = Color.argb(alpha, r, g, b)
        return newColor
    }
}
