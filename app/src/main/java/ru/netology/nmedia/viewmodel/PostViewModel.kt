package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    0,
    "",
    "",
    "",
    false,
    0,
    0,
    0
)

class PostViewModel : ViewModel() {

    //viewModel знает только пор репозиторий. нужен для хранения и управления данными.
    //нужен для хранения данных относящихся к юай и для связи с бизнес логикой.

    //инициализируем репу
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    //иниуиализируем объект, который подписывается на обновления
    val data = repository.getAll()

    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content == text) {
                return
            }
            edited.value = it.copy(content = text)
        }

    }

    //инициализируем функцию, которая меняет значение лайкнутости и
    // и присваивает ожидателю обновлений значение нового поста
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
}