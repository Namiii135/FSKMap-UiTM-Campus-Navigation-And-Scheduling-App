// RegisterActivity.kt
package com.example.myapplicationexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.panpf.zoomimage.SketchZoomImageView

class MainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val sketchZoomImageView = findViewById<SketchZoomImageView>(R.id.iv_map_background)
        sketchZoomImageView.setImageResource(R.drawable.map)



    }

}
