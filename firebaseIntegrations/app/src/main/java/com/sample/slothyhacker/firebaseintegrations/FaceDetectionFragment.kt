package com.sample.slothyhacker.firebaseintegrations

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import kotlinx.android.synthetic.main.fragment_face_detection.*

class FaceDetectionFragment : Fragment() {

    private val requestImageCapture = 1
    private var cameraImage: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_face_detection, container, false)
    }

    /** Callback for the take picture button */
    fun takePicture(view: View) {
        // Take an image using an existing camera app
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, requestImageCapture)
                happiness.text = ""
                graphicOverlay.clear()
            }
        }
    }

    /** Callback for the detect face button */
    fun detectFace(view: View) {
        // Build the options for face detector SDK
        if (cameraImage != null) {
            val image = FirebaseVisionImage.fromBitmap(cameraImage as Bitmap)
            val builder = FirebaseVisionFaceDetectorOptions.Builder()
            builder.setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            builder.setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            val options = builder.build()

            // Send our image to be detected by the SDK
            val detector = FirebaseVision.getInstance().getVisionFaceDetector(options)
            detector.detectInImage(image).addOnSuccessListener { faces ->
                displayImage(faces)
            }
        }
    }

    /** Draw a graphic overlay on top of our image */
    private fun displayImage(faces: List<FirebaseVisionFace>) {
        graphicOverlay.clear()
        if (faces.isNotEmpty()) {
            // We will only draw an overlay on the first face
            val face = faces[0]
            val faceGraphic = FaceContourGraphic(graphicOverlay, face)
            graphicOverlay.add(faceGraphic)
            happiness.text = "Smile Probability: " + (face.smilingProbability * 100) + "%"
        } else {
            happiness.text = "No face detected"
        }
    }

    /** Receive the result from the camera app */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == requestImageCapture && resultCode == RESULT_OK && data != null && data.extras != null) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap

            // Instead of creating a new file in the user's device to get a full scale image
            // resize our smaller imageBitMap to fit the screen
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = width / imageBitmap.width * imageBitmap.height
            cameraImage = Bitmap.createScaledBitmap(imageBitmap, width, height, false)

            // Display the image and enable our ML facial detection button
            imageView.setImageBitmap(cameraImage)
            detectFace.isEnabled = true
        }
    }

}
