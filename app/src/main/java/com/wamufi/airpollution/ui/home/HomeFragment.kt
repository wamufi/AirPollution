package com.wamufi.airpollution.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.wamufi.airpollution.MainActivity
import com.wamufi.airpollution.databinding.FragmentHomeBinding
import com.wamufi.airpollution.utils.Logger
import com.wamufi.airpollution.viewmodels.DustyViewModel
import com.wamufi.airpollution.viewmodels.StationViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: DustyViewModel by activityViewModels()
    private val stationViewModel: StationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.realTimeInfo.observe(viewLifecycleOwner) {
            binding.dusty = it[0]
        }

        stationViewModel.stationsList.observe(viewLifecycleOwner) {
            binding.station = it[0]
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}