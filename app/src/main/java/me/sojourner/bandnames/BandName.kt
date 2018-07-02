package me.sojourner.bandnames

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.TextView
import java.time.LocalDateTime

data class BandName(val name: String, val date: LocalDateTime)

class BandNameAdapter(context: Context, private var bandList: ArrayList<BandName>) : BaseAdapter() {

    inner class ListRowHolder(row: View?) {
        val label: TextView = row?.findViewById(R.id.label) as TextView
    }

    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ListRowHolder

        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.list_layout, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = bandList[position].name
        return view
    }

    override fun getItem(position: Int): Any {
        return bandList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return bandList.size
    }

}