package com.example.myapplication.jetpack.datastore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentDatastoreBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/24 11:09.
 */
class DataStoreFragment : Fragment() {

    private lateinit var binding: FragmentDatastoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDatastoreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val viewmodelCount = JetpackDataStore.getViewModelCount().first()
            val livedataCount = JetpackDataStore.getLiveDataCount().first()

            binding.etViewmodelCount.setText(viewmodelCount.toString())
            binding.etLivedataCount.setText(livedataCount.toString())
        }

        binding.btnModifyViewModelCount.setOnClickListener {
            JetpackDataStore.setViewModelCount(binding.etViewmodelCount.text.toString().toInt())
        }

        binding.btnModifyLivedataCount.setOnClickListener {
            JetpackDataStore.setLiveDataCount(binding.etLivedataCount.text.toString().toInt())
        }
    }

}