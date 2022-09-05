package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//репа - это шаблон проектирования, позволяющий взаимодейстсвовать с цем то как с коллекцией обхектов.
//бизнес логика хранится тут

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:37",
            likedByMe = false,
            "999",
            "19999",
            "10000"
        ), Post(
            id = 2,
            author = "2Нетология. Университет интернет-профессий будущего",
            content = "2Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "22 мая в 18:37",
            likedByMe = false,
            "1999",
            "29999",
            "20000"
        )
    )
    private val data = MutableLiveData(posts) //объект который подписываетсяя на обновления

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it
            else {
                it.copy(likedByMe = !it.likedByMe)
                if (it.likedByMe) {
                    it.copy(likes = (it.likes.toLong() + 1).toString())
                } else {
                    it.copy(likes = (it.likes.toLong() - 1).toString())
                }
            }
        }
        data.value = posts
    }

    override fun shareById(id: Long) {

        posts = posts.map {
            if (it.id != id) it
            else {
                it.copy(shares = (it.shares.toLong() + 1).toString())
            }
        }
        data.value = posts
    }

}


