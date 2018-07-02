package me.sojourner.bandnames

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {

    private val bandNames = ArrayList<BandName>()
    private lateinit var listAdapter: BandNameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_1.setOnClickListener(addNewBand())

        listAdapter = BandNameAdapter(this, bandNames)
        list_view.adapter = listAdapter
    }

    private fun addNewBand() = View.OnClickListener {
        val band = BandName(input.text.toString(), LocalDateTime.now())
        bandNames.add(band)
        listAdapter.notifyDataSetChanged()
        // input.clearFocus()
        input.setText("")
        input.hideKeyboard()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

