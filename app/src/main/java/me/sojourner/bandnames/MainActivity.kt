package me.sojourner.bandnames

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    private val bandNames = ArrayList<BandName>()
    private val filename = "bandnames.json"

    private lateinit var listAdapter: BandNameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_1.setOnClickListener {
            addNewBand()
        }
        input.setOnEditorActionListener { _, id, _ ->
            when (id) {
                EditorInfo.IME_ACTION_DONE -> {
                    input.hideKeyboard(); true
                }
                else -> false
            }
        }
        listAdapter = BandNameAdapter(this, bandNames)
        list_view.adapter = listAdapter

        readData()
    }

    private fun readData() {
        val gson = Gson()
        val text = File(applicationContext.filesDir, filename).readText()
        if (!text.isBlank()) {
            val jsonArray = gson.fromJson(text, Array<BandName>::class.java)
            for (band in jsonArray) {
                bandNames.add(band)
            }
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun writeData() {
        val gson = Gson()
        val file = File(applicationContext.filesDir, filename)
        val bandNameJson = gson.toJson(bandNames)
        file.writeText(bandNameJson.toString())
    }

    private fun addNewBand() {
        val text = input.text.toString()
        if (!text.isBlank()) {  //only add a new band if we typed something
            val band = BandName(text, LocalDateTime.now())
            bandNames.add(band)
            listAdapter.notifyDataSetChanged()

            //save new band to file
            writeData()
            //upload to web
            //uploadBand(band)

            //clean up
            input.setText("")
            scrollToBottom()
        }

    }
    private fun scrollToBottom() {
        list_view.post {
            list_view.smoothScrollToPosition(bandNames.size - 1)
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

