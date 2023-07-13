package com.ngga_ring.dbmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ngga_ring.dbmovie.api.Resource
import com.ngga_ring.dbmovie.databinding.ActivityMainBinding
import com.ngga_ring.dbmovie.models.Articles
import com.ngga_ring.dbmovie.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var data: List<Articles>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecler()

        binding.business.setOnClickListener {
            getData(1, "business", "")
        }
        binding.entertainment.setOnClickListener {
            getData(1, "entertainment", "")
        }
        binding.general.setOnClickListener {
            getData(1, "general", "")
        }
        binding.health.setOnClickListener {
            getData(1, "health", "")
        }
        binding.sports.setOnClickListener {
            getData(1, "sports", "")
        }
        binding.technology.setOnClickListener {
            getData(1, "technology", "")
        }
        binding.serach.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.keyCode == KeyEvent.KEYCODE_ENTER) {
                getData(
                    1,
                    "",
                    binding.serach.text.toString()
                )
                true
            } else {
                false
            }
        }

        val key = getString(R.string.token)
        viewModel.getNews("technology", key, null, null).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { result ->
                        Toast.makeText(this, "show data", Toast.LENGTH_SHORT)
                            .show()
                        data = result.articles
                        viewData()
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        initScrollListener()
    }


    private var posScrole = 0
    private fun initScrollListener() {
        binding.rvNews.layoutManager = LinearLayoutManager(this)

        binding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == data!!.size - 1) {
                    //bottom of list!
                    if (data!!.size >= 19) {
                        getData(posScrole, "business","")
                    }
                }

            }
        })
    }

    private fun getData(posScrole: Int, s: String, q: String) {
        val key = getString(R.string.token)
        viewModel.getNews(
            s,
            key,
            if (posScrole == 1) null else "100",
            if (posScrole == 1) null else posScrole.toString(),
            q
        ).observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { result ->
                        data = result.articles
                        viewData()
                    }
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun viewData() {
        binding.rvNews.adapter?.let { adapter ->
            if (adapter is NewsAdapter) {
                adapter.setNews(data!!)
            }
        }
    }

    private fun setupRecler() {
        binding.rvNews.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = NewsAdapter(mutableListOf(), this@MainActivity)
        }
    }
}