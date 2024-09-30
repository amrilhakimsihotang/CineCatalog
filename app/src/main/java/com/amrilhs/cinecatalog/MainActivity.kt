package com.amrilhs.cinecatalog

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private val carouselItems = ArrayList<CarouselItem>()
    private lateinit var carouselRecyclerview: RecyclerView
    private lateinit var recyclerViewReligi: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //recyclerview
        carouselRecyclerview = findViewById(R.id.carousel_recycler_view)
        carouselRecyclerview.setHasFixedSize(true)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Set Layout Manager and Adapter for Carousel RecyclerView
        carouselRecyclerview.layoutManager = CarouselLayoutManager()

        val snapHelper = CarouselSnapHelper()
        snapHelper.attachToRecyclerView(carouselRecyclerview)

        val items = listOf(
            CarouselItem(R.drawable.aqsa),
            CarouselItem(R.drawable.masjidcairo),
            CarouselItem(R.drawable.casablanca),
            CarouselItem(R.drawable.masjidbandaaceh),
            CarouselItem(R.drawable.sultanqaboos),
            CarouselItem(R.drawable.b_masjidilharam),
            CarouselItem(R.drawable.a_masjidnabawi),
            CarouselItem(R.drawable.c_masjdidsultanahmed),
            CarouselItem(R.drawable.d_masjid_al_azhar),
            CarouselItem(R.drawable.e_masjidqubamadinah),
            CarouselItem(R.drawable.f_masjidcordoba),
            CarouselItem(R.drawable.g_masjidagunghassanii),

        )
        carouselItems.addAll(items)

        val carouselAdapter = CarouselAdapter(carouselItems)
        carouselRecyclerview.adapter = carouselAdapter


        // Handler for auto-scrolling
        val autoScrollHandler = Handler(Looper.getMainLooper())
        val scrollRunnable = object : Runnable {
            var currentPosition = 0
            override fun run() {
                if (carouselRecyclerview.adapter != null) {
                    currentPosition = (currentPosition + 1) % carouselAdapter.itemCount
                    carouselRecyclerview.smoothScrollToPosition(currentPosition)
                    // Schedule the next scroll after 3 seconds (3000ms)
                    autoScrollHandler.postDelayed(this, 3000)
                }
            }
        }

// Start the auto-scrolling
        autoScrollHandler.postDelayed(scrollRunnable, 3000)

        // RecyclerView for Religi
        recyclerViewReligi = findViewById(R.id.id_rv_religi)
        recyclerViewReligi.setLayoutManager(GridLayoutManager(this, 1))


        val inputStream = resources.openRawResource(R.raw.religi)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        // Read JSON file as string
        val jsonString = bufferedReader.use { it.readText() }
        val gson = Gson()
        val listReligiType = object : TypeToken<List<Religi>>() {}.type
        val religiList: ArrayList<Religi> = gson.fromJson(jsonString, listReligiType)

        recyclerViewReligi.adapter = ReligiAdapter(religiList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_page -> {
                startActivity(Intent(this@MainActivity, AboutPageActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
