package com.dvinc.customchart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dvinc.customchart.pointsgenerator.RandomPointsGenerator
import kotlinx.android.synthetic.main.activity_main.activity_main_chart_view as chartView
import kotlinx.android.synthetic.main.activity_main.activity_main_generate_numbers_button as generateButton
import kotlinx.android.synthetic.main.activity_main.activity_main_dots_checkbox as dotsCheckBox

class MainActivity : AppCompatActivity() {

    private val randomPointsGenerator = RandomPointsGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateButton.setOnClickListener {
            val randomPoints = randomPointsGenerator.getRandomPoints()
            val shouldShowDots = dotsCheckBox.isChecked
            chartView.drawPoints(randomPoints, shouldShowDots)
        }
    }
}
