package com.example.simplenotes.adapter

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.simplenotes.R
import com.example.simplenotes.model.NoteItem


class NotesAdapter(
    private var list: MutableList<NoteItem>,
    private var clickListener: NoteClickListener
)
    : RecyclerView.Adapter<ListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(inflater, parent)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val noteItem = list[position]
        val itemView = holder.itemView
        val TitleText = itemView.findViewById<TextView>(R.id.Rv_Item_Title)
        val MessageText = itemView.findViewById<TextView>(R.id.Rv_Item_Note)

        TitleText.setText(noteItem.title)
        MessageText.setText(noteItem.note)

        itemView.setOnClickListener {
            clickListener.onClick(noteItem)
        }

        //onlongclick
        itemView.setOnLongClickListener {
            val cliptext = noteItem.id.toString()
            val item = ClipData.Item(cliptext)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(cliptext,mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data,dragShadowBuilder,it,0)

            true
        }



    }

    override fun getItemCount(): Int = list.size


}

interface NoteClickListener {
    fun onClick(noteItem: NoteItem)
}
class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.note_item, parent, false)) {
}