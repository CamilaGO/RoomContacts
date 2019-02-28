package com.example.camila.lab7.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camila.lab7.Data.Contact
import com.example.camila.lab7.R
import kotlinx.android.synthetic.main.activity_add_contact.view.*
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : ListAdapter<Contact, ContactAdapter.ContactHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.name == newItem.name && oldItem.phone == newItem.phone
                        && oldItem.mail == newItem.mail && oldItem.priority == newItem.priority
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val currentContact: Contact = getItem(position)

        holder.textViewName.text = currentContact.name
        holder.textViewPhone.text = currentContact.phone
        holder.textViewMail.text = currentContact.mail
        holder.textViewPriority.text = currentContact.priority.toString()
    }

    fun getContactAt(position: Int): Contact {
        return getItem(position)
    }

    inner class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewName: TextView = itemView.text_view_name
        var textViewPhone: TextView = itemView.text_view_phone
        var textViewMail: TextView = itemView.text_view_mail
        var textViewPriority: TextView = itemView.text_view_priority
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Contact)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

