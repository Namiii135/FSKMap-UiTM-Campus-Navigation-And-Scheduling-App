package com.example.myapplicationexample

import android.os.Bundle
import android.widget.ImageView
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import android.graphics.PointF
import android.view.View
import androidx.core.content.ContextCompat
import android.widget.Toast

class MainPageActivity : AppCompatActivity() {

    data class MarkerData(
        val id: String,
        val sourceCoord: PointF,
        val iconRes: Int,
        val roomName: String,
        val type: String
    )

    private lateinit var mapView: SubsamplingScaleImageView
    private lateinit var markersContainer: FrameLayout
    private val dynamicMarkers = mutableListOf<ImageView>()

    // Adjusted marker positions based on your actual app screenshot
    private val markerDataList = listOf(
        // Top row of building blocks - Biology Labs
        MarkerData("bio_lab_1", PointF(480f, 265f), R.drawable.biolab, "Biology Lab 1", "biology"),
        MarkerData("bio_lab_2", PointF(520f, 285f), R.drawable.biolab, "Biology Lab 2", "biology"),
        MarkerData("bio_lab_3", PointF(600f, 315f), R.drawable.biolab, "Biology Lab 3", "biology"),
        MarkerData("bio_lab_4", PointF(650f, 330f), R.drawable.biolab, "Biology Lab 4", "biology"),

        // Second row - Computer Labs
        MarkerData("comp_lab_1", PointF(280f, 690f), R.drawable.makmal, "Computer Lab 1", "computer"),
        MarkerData("comp_lab_2", PointF(340f, 710f), R.drawable.makmal, "Computer Lab 2", "computer"),
        MarkerData("comp_lab_3", PointF(380f, 720f), R.drawable.makmal, "Computer Lab 3", "computer"),
        MarkerData("comp_lab_4", PointF(480f, 750f), R.drawable.makmal, "Computer Lab 4", "computer"),
        MarkerData("comp_lab_5", PointF(520f, 750f), R.drawable.makmal, "Computer Lab 5", "computer"),
        MarkerData("lecturer_1", PointF(570f, 770f), R.drawable.lecturerroom, "Lecturer Room 1", "lecturer"),

        // Third row - Lecturer Rooms
        MarkerData("lecturer_1", PointF(570f, 700f), R.drawable.lecturerroom, "Lecturer Room 1", "lecturer"),
        MarkerData("lecturer_2", PointF(570f, 680f), R.drawable.lecturerroom, "Lecturer Room 2", "lecturer"),
        MarkerData("lecturer_3", PointF(570f, 660f), R.drawable.lecturerroom, "Lecturer Room 3", "lecturer"),
        MarkerData("lecturer_4", PointF(570f, 640f), R.drawable.lecturerroom, "Lecturer Room 4", "lecturer"),
        MarkerData("lecturer_5", PointF(570f, 620f), R.drawable.lecturerroom, "Lecturer Room 5", "lecturer"),

        // Fourth row - Mixed facilities
        MarkerData("toilet_1", PointF(140f, 240f), R.drawable.toilet, "Toilet 1", "toilet"),
        MarkerData("stairs_1", PointF(570f, 305f), R.drawable.stairs, "Stairs 1", "stairs"),
        MarkerData("lecturer_6", PointF(220f, 240f), R.drawable.lecturerroom, "Lecturer Room 6", "lecturer"),
        MarkerData("toilet_2", PointF(260f, 240f), R.drawable.toilet, "Toilet 2", "toilet"),
        MarkerData("stairs_2", PointF(420f, 730f), R.drawable.stairs, "Stairs 2", "stairs"),

        // Bottom row and scattered positions
        MarkerData("bio_lab_5", PointF(160f, 280f), R.drawable.biolab, "Biology Lab 5", "biology"),
        MarkerData("toilet_3", PointF(240f, 280f), R.drawable.toilet, "Toilet 3", "toilet"),
        MarkerData("stairs_3", PointF(280f, 280f), R.drawable.stairs, "Stairs 3", "stairs"),

        // Additional scattered positions in other building blocks
        MarkerData("lecturer_7", PointF(340f, 160f), R.drawable.lecturerroom, "Lecturer Room 7", "lecturer"),
        MarkerData("toilet_4", PointF(340f, 200f), R.drawable.toilet, "Toilet 4", "toilet"),
        MarkerData("bio_lab_6", PointF(120f, 160f), R.drawable.biolab, "Biology Lab 6", "biology"),
        MarkerData("comp_lab_6", PointF(120f, 200f), R.drawable.makmal, "Computer Lab 6", "computer")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        initializeViews()
        setupMapView()
        setupClickListeners()
    }

    private fun initializeViews() {
        mapView = findViewById(R.id.iv_map)
        markersContainer = findViewById(R.id.markers_container)
    }

    private fun setupMapView() {
        mapView.apply {
            setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE)
            maxScale = 4f
            setDoubleTapZoomStyle(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER)
            setImage(ImageSource.resource(R.drawable.map))
        }

        mapView.setOnImageEventListener(object : SubsamplingScaleImageView.DefaultOnImageEventListener() {
            override fun onReady() {
                createDynamicMarkers()
                updateAllMarkerPositions()
            }
        })

        mapView.setOnStateChangedListener(object : SubsamplingScaleImageView.OnStateChangedListener {
            override fun onScaleChanged(newScale: Float, origin: Int) {
                updateAllMarkerPositions()
            }

            override fun onCenterChanged(newCenter: PointF?, origin: Int) {
                updateAllMarkerPositions()
            }
        })
    }

    private fun createDynamicMarkers() {
        markersContainer.removeAllViews()
        dynamicMarkers.clear()

        markerDataList.forEach { markerData ->
            val marker = ImageView(this).apply {
                layoutParams = FrameLayout.LayoutParams(24, 24)
                setImageResource(markerData.iconRes)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
                isClickable = true
                isFocusable = true
                contentDescription = markerData.roomName
                tag = markerData.id

                setOnClickListener {
                    onMarkerClick(markerData)
                }
            }

            markersContainer.addView(marker)
            dynamicMarkers.add(marker)
        }
    }

    private fun updateAllMarkerPositions() {
        if (dynamicMarkers.size != markerDataList.size) return

        dynamicMarkers.forEachIndexed { index, marker ->
            val markerData = markerDataList[index]
            val viewCoord = mapView.sourceToViewCoord(markerData.sourceCoord)

            viewCoord?.let {
                marker.x = it.x - marker.width / 2
                marker.y = it.y - marker.height / 2
                marker.visibility = View.VISIBLE
            }
        }
    }

    private fun onMarkerClick(markerData: MarkerData) {
        Toast.makeText(this, "Clicked: ${markerData.roomName}", Toast.LENGTH_SHORT).show()

        // Highlight corresponding room in suggested section if applicable
        when (markerData.id) {
            "bio_lab_1" -> highlightRoom(R.id.biology_lab_1)
            "bio_lab_2" -> highlightRoom(R.id.biology_lab_2)
            "bio_lab_3" -> highlightRoom(R.id.biology_lab_3)
            "bio_lab_4" -> highlightRoom(R.id.biology_lab_4)
            "comp_lab_1" -> highlightRoom(R.id.computer_lab_1)
            "comp_lab_2" -> highlightRoom(R.id.computer_lab_2)
            "comp_lab_3" -> highlightRoom(R.id.computer_lab_3)
            "comp_lab_4" -> highlightRoom(R.id.computer_lab_4)
        }

        animateMarker(markerData.id)
    }

    private fun setupClickListeners() {
        // Biology Lab clicks
        findViewById<LinearLayout>(R.id.biology_lab_1).setOnClickListener {
            focusOnMarker("bio_lab_1")
        }
        findViewById<LinearLayout>(R.id.biology_lab_2).setOnClickListener {
            focusOnMarker("bio_lab_2")
        }
        findViewById<LinearLayout>(R.id.biology_lab_3).setOnClickListener {
            focusOnMarker("bio_lab_3")
        }
        findViewById<LinearLayout>(R.id.biology_lab_4).setOnClickListener {
            focusOnMarker("bio_lab_4")
        }

        // Computer Lab clicks
        findViewById<LinearLayout>(R.id.computer_lab_1).setOnClickListener {
            focusOnMarker("comp_lab_1")
        }
        findViewById<LinearLayout>(R.id.computer_lab_2).setOnClickListener {
            focusOnMarker("comp_lab_2")
        }
        findViewById<LinearLayout>(R.id.computer_lab_3).setOnClickListener {
            focusOnMarker("comp_lab_3")
        }
        findViewById<LinearLayout>(R.id.computer_lab_4).setOnClickListener {
            focusOnMarker("comp_lab_4")
        }
    }

    private fun focusOnMarker(markerId: String) {
        val markerData = markerDataList.find { it.id == markerId }
        markerData?.let {
            mapView.animateScaleAndCenter(2.5f, it.sourceCoord)?.start()
            animateMarker(markerId)
        }
    }

    private fun animateMarker(markerId: String) {
        val marker = dynamicMarkers.find { it.tag == markerId }
        marker?.let {
            it.animate()
                .scaleX(1.5f)
                .scaleY(1.5f)
                .setDuration(200)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200)
                        .start()
                }
                .start()
        }
    }

    private fun highlightRoom(roomLayoutId: Int) {
        val roomLayout = findViewById<LinearLayout>(roomLayoutId)
        roomLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light))
        roomLayout.postDelayed({
            roomLayout.background = ContextCompat.getDrawable(this, android.R.attr.selectableItemBackground)
        }, 1000)
    }
}