package com.example.projectiot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.iotsystemparking.adapter.AdapterBuatanSaya
import com.example.projectiot.R
import com.example.projectiot.config.Config2
import com.example.projectiot.databinding.ActivityBmkgBinding
import com.example.projectiot.model.ModelListGempa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BmkgActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmkgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmkgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val _listview = findViewById<ListView>(R.id.list_gempa)

        Config2().getService()
            .getgempa()
            .enqueue(object : Callback<ModelListGempa> {
                override fun onResponse(
                    call: Call<ModelListGempa>,
                    response: Response<ModelListGempa>
                ) {
                    Log.d("rachmafadhillah", "data json: " + response.body())
                    _listview.adapter = AdapterBuatanSaya(response.body(), this@BmkgActivity,
                        response.body()?.infogempa?.gempa!!
                    )
                }

                override fun onFailure(call: Call<ModelListGempa>, t: Throwable) {
                    Log.d("rachmafadhillah", "error: " + t.message.toString())
                }
            })
    }
}