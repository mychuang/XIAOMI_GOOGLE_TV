package com.miller.tv.launcher

import android.app.Activity
import android.os.Bundle
import com.miller.tv.launcher.databinding.ActivityMainBinding

/** Stage 1 bootstrap screen, not the MVP home implementation. */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
