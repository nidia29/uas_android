package com.uas.nidiya.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.uas.nidiya.R
import com.uas.nidiya.adapters.NewsAdapter
import com.uas.nidiya.models.News
import com.uas.nidiya.networks.BaseListResponse
import com.uas.nidiya.services.ApiUtils
import kotlinx.android.synthetic.main.fragment_news.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : Fragment() {
    private val apiService = ApiUtils.getApiService()
    private var news = mutableListOf<News>()
    private var request : Call<BaseListResponse<News>>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_news.layoutManager = LinearLayoutManager(activity)
    }

    private fun loadData(){
        view!!.loading?.visibility = View.VISIBLE
        request = apiService.all()
        request!!.enqueue(object : Callback<BaseListResponse<News>> {
            override fun onFailure(call: Call<BaseListResponse<News>>, t: Throwable) {
                view!!.loading?.visibility = View.INVISIBLE
                println("mortal kombaaaat : "+t.message)
                Toast.makeText(activity, "Cannot connect to server", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<BaseListResponse<News>>, response: Response<BaseListResponse<News>>) {
                if(response.isSuccessful){
                    view!!.loading?.visibility = View.INVISIBLE
                    val body = response.body()
                    if (body?.data != null){
                        news.clear()
                        news = body.data as MutableList<News>
                        view!!.rv_news.adapter = NewsAdapter(news, activity!!)
                    }
                }else{
                    view!!.loading?.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Failed to get response", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        news.clear()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        request?.cancel()
    }
}