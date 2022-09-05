package ru.netology.nmedia.dto

import androidx.recyclerview.widget.RecyclerView

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post){
        binding.apply{
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setImageResource(
                if(post.likedByMe){
                    R.drawable.ic
                }
            )
            like.setOnClickListener{
                onLikeListener(post)
            }
        }
    }

}