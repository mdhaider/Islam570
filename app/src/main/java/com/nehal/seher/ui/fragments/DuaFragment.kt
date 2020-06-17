package com.nehal.seher.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nehal.seher.databinding.DuaFragmentBinding
import com.nehal.seher.model.DuaModel

class DuaFragment : Fragment() {
    private lateinit var binding: DuaFragmentBinding
    private lateinit var dua: DuaModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DuaFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dua = arguments?.let { DuaFragmentArgs.fromBundle(it).item }!!

        binding.name.text = dua.dua_title
        binding.nameAr.text = dua.dua_arabic
        binding.tvTrans.text = dua.dua_transliteration
        binding.tvMeaning.text = dua.english_translation
        binding.tvSource.text = dua.reference
    }
}
