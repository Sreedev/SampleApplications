package com.sample.slothyhacker.recyclerviewwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    data class Rainfall(val newsTitle: String, val shortDesc: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up action bar
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.actionbartitle)

        //Setting up the adapter
        val newsListAdapter = NewsListAdapter(getTheNews())

        //Setting adapter to recycler view
        list_recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list_recycler_view.adapter = newsListAdapter
    }

    private fun getTheNews(): ArrayList<Rainfall> {
        val newsList = ArrayList<Rainfall>()
        newsList.add(
            Rainfall(
                getString(R.string.news_one_title),
                getString(R.string.news_one_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_two_title),
                getString(R.string.news_two_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_three_title),
                getString(R.string.news_three_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_four_title),
                getString(R.string.news_four_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_five_title),
                getString(R.string.news_five_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_six_title),
                getString(R.string.news_six_shortdesc)
            )
        )
        newsList.add(
            Rainfall(
                getString(R.string.news_seven_title),
                getString(R.string.news_seven_shortdesc)
            )
        )
        return newsList
    }
}
