package kr.co.yeeun.lee.demoi.searchmovieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import kr.co.yeeun.lee.demoi.searchmovieapp.R

class MainActivity : AppCompatActivity() {
    private val viewmodel: MovieViewModel by viewModels()
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}