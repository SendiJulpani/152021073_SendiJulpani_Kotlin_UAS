package com.example.projectiot

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.projectiot.config.Config
import com.example.projectiot.model.ModelSlot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var slot1: TextView
    private lateinit var slot2: TextView

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
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        // Initialize your TextViews and other views
        slot1 = rootView.findViewById(R.id.mobil_isi)
        slot2 = rootView.findViewById(R.id.motor_isi)

        // ... (repeat for other views)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Your code remains mostly the same
        Config().getSlot1Service()
            .getSlot1Data()
            .enqueue(object : Callback<ModelSlot> {
                override fun onResponse(call: Call<ModelSlot>, response: Response<ModelSlot>) {
                    Log.d("sendi", "data json: " + response.body())

                    slot1.text = response.body()?.totalKendaraan.toString()

                }

                override fun onFailure(call: Call<ModelSlot>, t: Throwable) {
                    Log.d("sendi", "error: " + t.message.toString())
                }


            })

        Config().getSlot2Service()
            .getSlot2Data()
            .enqueue(object : Callback<ModelSlot> {
                override fun onResponse(call: Call<ModelSlot>, response: Response<ModelSlot>) {
                    Log.d("sendi", "data json: " + response.body())

                    slot2.text = response.body()?.totalKendaraan.toString()

                }

                override fun onFailure(call: Call<ModelSlot>, t: Throwable) {
                    Log.d("sendi", "error: " + t.message.toString())
                }
            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}