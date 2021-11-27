package com.example.sampleFirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListViewAdapter(val list: MutableList<Model>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.count() // list.size()와 차이가 뭘까
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.listview_item, parent, false)
        }

        val itemTitleTextView = view?.findViewById<TextView>(R.id.tv_item_title)
        val itemBodyTextView = view?.findViewById<TextView>(R.id.tv_item_body)
        itemTitleTextView?.setText(list[position].title)
        itemBodyTextView?.setText(list[position].body)
        return view!!
    }
}