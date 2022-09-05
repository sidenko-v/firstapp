package ru.netology.nmedia.dto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//репа - это шаблон проектирования, позволяющий взаимодейстсвовать с цем то как с коллекцией обхектов.
//бизнес логика хранится тут

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
        published = "21 мая в 18:37",
        likedByMe = false,
        "999",
        "19999",
        "10000"
    )
    private val data = MutableLiveData(post) //объект который подписываетсяя на обновления

    override fun get(): LiveData<Post> = data
    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        if (post.likedByMe) {
            post = post.copy(likes = (post.likes.toLong() + 1).toString())
        } else {
            post = post.copy(likes = (post.likes.toLong() - 1).toString())
        }
        data.value = post
    }

    override fun share(){
        post = post.copy(shares = (post.shares.toLong() + 1).toString())
        data.value = post

    }
}

