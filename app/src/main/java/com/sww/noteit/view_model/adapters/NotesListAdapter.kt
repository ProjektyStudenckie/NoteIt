package com.sww.noteit.view_model.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.sww.noteit.R
import com.sww.noteit.model.Note
import kotlinx.android.synthetic.main.list_item_note.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class NotesListAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    private var notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = notes[position]

        if (holder is MyViewHolder) {
            holder.itemView.note_title.text = note.Title
            if(note.ImageURL==""){
                holder.itemView.imgURL.text="BRAK ZDJECIA"
            } else{
                holder.itemView.imgURL.text=note.ImageURL
            }

            val format = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.GERMAN)
            holder.itemView.note_date.text = LocalDate.parse(note.DateDate,format).toString()



            holder.itemView.setOnClickListener {
                onClickListener?.onClick(position, note)
            }
        }
    }

    override fun getItemCount() = notes.size


    internal fun setNotes(notes: MutableList<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    internal fun getNotes(): MutableList<Note> {
        return notes
    }

    fun removeAt(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setOnClickListener(listener: OnClickListener) {
        onClickListener = listener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Note)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}