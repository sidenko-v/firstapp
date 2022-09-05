package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData

//репа - это шаблон проектирования, позволяющий взаимодейстсвовать с цем то как с коллекцией обхектов.
//тут храниться бизнеслогика?

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}
