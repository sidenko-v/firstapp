package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.post_card_layout.*
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import ru.netology.nmedia.dto.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostCardLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //активити у нас знает только про вью модел
        val viewModel: PostViewModel by viewModels()

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                postContent.text = post.content
                likes.text = numToAbbreviatedNumber(post.likes.toLong())
                shares.text = numToAbbreviatedNumber(post.shares.toLong())
                views.text = numToAbbreviatedNumber(post.views.toLong())
                if (post.likedByMe) {
                    likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                }
            }

            binding.likesButton.setOnClickListener {
                viewModel.like()
                if (viewModel.data.value?.likedByMe == true) {
                    likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    likesButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            binding.shareButton.setOnClickListener { viewModel.share() }

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

