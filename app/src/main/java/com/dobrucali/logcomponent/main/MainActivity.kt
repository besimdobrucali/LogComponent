package com.dobrucali.logcomponent.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dobrucali.logcomponent.di.ILogger
import com.dobrucali.logcomponent.R
import com.dobrucali.logcomponent.di.ShowLogger
import com.dobrucali.logcomponent.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

    private val logger: ILogger by inject()
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        (logger as ShowLogger).initializeView(binding.showLogView)

        viewModel.lastChosenDirection.observe(this, Observer { direction ->
            direction?.let {
                binding.directionTextView.text = when (direction) {
                    MainViewModel.Direction.EAST -> getString(
                        R.string.east
                    )
                    MainViewModel.Direction.WEST -> getString(
                        R.string.west
                    )
                    MainViewModel.Direction.NORTH -> getString(
                        R.string.north
                    )
                    MainViewModel.Direction.SOUTH -> getString(
                        R.string.south
                    )
                }
                logger.e("direction: ${it.name}")
            }
        })

        viewModel.forecast.observe(this, Observer { forecast ->
            forecast?.let {
                val description = it.weather.first().main
                logger.w("description: $description")
            }
        })

        viewModel.status.observe(this, Observer { status ->
            status?.let {
                logger.d("status: $it")
            }
        })

        binding.root
    }

}