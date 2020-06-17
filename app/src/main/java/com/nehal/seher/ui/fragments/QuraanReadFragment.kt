package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.nehal.seher.utils.FileUtils
import com.nehal.seher.databinding.QuraanReadFragmentBinding
import com.nehal.seher.viewmodels.QuraanReadViewModel
import java.io.File

class QuraanReadFragment : Fragment() {
    private lateinit var viewModel: QuraanReadViewModel
    private lateinit var binding: QuraanReadFragmentBinding
    private var url: String? = null
    private var title: String? = null
    private var id: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = QuraanReadFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QuraanReadViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.let { QuraanReadFragmentArgs.fromBundle(it).id}
        url = arguments?.let { QuraanReadFragmentArgs.fromBundle(it).url}
        title = arguments?.let { QuraanReadFragmentArgs.fromBundle(it).title }

        binding.progressBar.visibility = View.VISIBLE
        val fileName = "${title}-${id}-text.pdf"
        downloadPdfFromInternet(
            url?.let { FileUtils.getPdfUrl(it) },
            FileUtils.getRootDirPath(requireContext()),
            fileName
        )
    }

    private fun downloadPdfFromInternet(url: String?, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    val downloadedFile = File(dirPath, fileName)
                    binding.progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        requireContext(),
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(true)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    requireContext(),
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }
}
