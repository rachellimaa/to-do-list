package com.rachellima.todolist.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rachellima.todolist.R
import com.rachellima.todolist.databinding.ItemTaskBinding
import com.rachellima.todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.ViewHolder>(DiffCallback()) {
    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.task.text = item.title
            binding.date.text = "${item.date} ${item.hour}"
            binding.descriptionItem.text = item.description
            binding.ivMore.setOnClickListener {
                showPopUp(item)
            }
        }

        private fun showPopUp(item : Task) {
            val ivMore = binding.ivMore
            val popMenu = PopupMenu(ivMore.context, ivMore)
            popMenu.menuInflater.inflate(R.menu.menu, popMenu.menu)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.id == newItem.id

}