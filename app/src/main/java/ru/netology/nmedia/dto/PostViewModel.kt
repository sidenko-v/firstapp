package ru.netology.nmedia.dto

import androidx.lifecycle.ViewModel

class PostViewModel: ViewModel() {

    //viewModel знает только пор репозиторий. нужен для хранения и управления данными.
    //нужен для хранения данных относящихся к юай и для связи с бизнес логикой.

    //инициализируем репу
    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    //иниуиализируем объект, который подписывается на обновления
    val data = repository.get()

    //инициализируем функцию, которая меняет значение лайкнутости и
    // и присваивает ожидателю обновлений значение нового поста
    fun like() = repository.like()
    fun share() = repository.share()
}