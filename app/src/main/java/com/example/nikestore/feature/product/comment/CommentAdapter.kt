package com.example.nikestore.feature.product.comment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.dataclass.Comment

class CommentAdapter(val showAll: Boolean = false) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var comments = ArrayList<Comment>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv = itemView.findViewById<TextView>(R.id.commentTitleTv)
        val dateTv = itemView.findViewById<TextView>(R.id.commentDateTv)
        val autherTv = itemView.findViewById<TextView>(R.id.commentAuthorTv)
        val contentTv = itemView.findViewById<TextView>(R.id.commentContentTv)

        fun bindComment(comment: Comment) {
            titleTv.text = comment.title
            dateTv.text = comment.date
            autherTv.text = comment.author.email
            contentTv.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindComment(comments[position])
    }

    override fun getItemCount(): Int {
        return if (comments.size > 3 && !showAll) 3 else comments.size
    }
}