package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:37",
            likedByMe = false,
            999,
            19999,
            10000
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            postContent.text = post.content
            likes.text = numToAbbreviatedNumber(post.likes)
            shares.text = numToAbbreviatedNumber(post.shares)
            views.text = numToAbbreviatedNumber(post.views)
            if (post.likedByMe) {
                likesButton?.setImageResource(R.drawable.ic_baseline_favorite_24)
            }

            likesButton?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                likesButton.setImageResource(
                    if (post.likedByMe) {
                        R.drawable.ic_baseline_favorite_24
                    } else {
                        R.drawable.ic_baseline_favorite_border_24
                    }
                )
                if (post.likedByMe) {
                    post.likes = (post.likes + 1)
                    likes.text = numToAbbreviatedNumber(post.likes)
                } else {
                    post.likes = (post.likes - 1)
                    likes.text = numToAbbreviatedNumber(post.likes)
                }
            }

            shareButton?.setOnClickListener {
                post.shares = (post.shares + 1)
                shares.text = numToAbbreviatedNumber(post.shares)

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

