package ru.netology.nmedia

import android.content.Intent
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: OnInterasctionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            likesButton.text = numToAbbreviatedNumber(post.likes)
            shareButton.text = numToAbbreviatedNumber(post.shares)
            views.text = numToAbbreviatedNumber(post.views)
            likesButton.isChecked = post.likedByMe
            likesButton.setOnClickListener {
                listener.onLike(post)
            }
            shareButton.setOnClickListener {
                listener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        Intent().putExtra("content", post.content)
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

    }


    fun numToAbbreviatedNumber(number: Long): String {
        var abbNumber = ""

        when (number) {
            in 0..999 -> abbNumber = number.toString()
            in 1_000..9_999 -> {
                var ost = number % 1000
                if (ost > 99) {
                    var num = number / 100
                    var num2: Float = num.toFloat() / 10
                    abbNumber = num2.toString() + "K"
                } else abbNumber = (number / 1000).toString() + "K"
            }
            in 10_000..999_999 -> {
                var num = number / 1000
                abbNumber = num.toString() + "K"
            }
            in 1_000_000..9_999_999 -> {
                var ost = number % 1000_000
                if (ost > 99_999) {
                    var num = number / 100_000
                    var num2: Float = num.toFloat() / 10
                    abbNumber = num2.toString() + "M"
                } else abbNumber = (number / 1000_000).toString() + "M"
            }
        }
        return abbNumber
    }
}