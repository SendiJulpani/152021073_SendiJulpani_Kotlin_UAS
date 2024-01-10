package com.example.projectiot

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.db.williamchart.view.BarChartView
import com.example.projectiot.config.Config
import com.example.projectiot.databinding.FragmentHomeBinding
import com.example.projectiot.model.ModelAverage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val barSet = mutableListOf<Pair<String, Float>>()

    private lateinit var barChartView: BarChartView

    private var param1: String? = null
    private var param2: String? = null

    private val animationDuration = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCharts()
        fetchDataFromApi()
    }

    private fun setupCharts() {
        binding.apply {
            barChart.animation.duration = animationDuration
            barChart.animate(barSet)
        }
    }

    private fun parseData(modelAverage: ModelAverage?) {
        modelAverage?.let {
            it.data?.let { data ->
                val senin = data.senin ?: 0
                val selasa = data.selasa ?: 0
                val rabu = data.rabu ?: 0
                val kamis = data.kamis ?: 0
                val jumat = data.jumat ?: 0
                val sabtu = data.sabtu ?: 0
                val minggu = data.minggu ?: 0

                barSet.apply {
                    clear()
                    add("Senin" to senin.toFloat())
                    add("Selasa" to selasa.toFloat())
                    add("Rabu" to rabu.toFloat())
                    add("Kamis" to kamis.toFloat())
                    add("Jumat" to jumat.toFloat())
                    add("Sabtu" to sabtu.toFloat())
                    add("Minggu" to minggu.toFloat())
                }
            }
        }
    }

    private fun updateCharts() {
        binding.apply {
            // Add new data
            barChart.animate(barSet)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchDataFromApi() {
        // Assuming you have a Retrofit service for fetching earthquake data
        // Make sure to replace ApiService and getDataGempa with your actual Retrofit service
        Config().getAverageService()
            .getAverageData()
            .enqueue(object : Callback<ModelAverage> {
                override fun onResponse(
                    call: Call<ModelAverage>,
                    response: Response<ModelAverage>
                ) {
                    if (response.isSuccessful) {
                        parseData(response.body())
                        updateCharts()
                    }
                }

                override fun onFailure(call: Call<ModelAverage>, t: Throwable) {

                }

            })
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}


