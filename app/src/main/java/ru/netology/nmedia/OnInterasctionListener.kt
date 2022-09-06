package ru.netology.nmedia

import ru.netology.nmedia.dto.Post

interface OnInterasctionListener {
    fun onLike(post: Post){}
    fun onEdit(post: Post){}
    fun onRemove(post: Post){}
    fun onShare(post: Post){}

}