package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.OnInterasctionListener
import ru.netology.nmedia.PostsAdapter
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //подготовка view
        val binding = ActivityMainBinding.inflate(layoutInflater)

        //определяем пользовательский интерфейс
        setContentView(binding.root)

        //мейнактивити у нас знает только про вью модел
        //by - это делегирование
        val viewModel: PostViewModel by viewModels()

        val editPostActivityLauncher = registerForActivityResult(
            EditPostActivity.Contract
        ) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        //адаптер - класс для предоставления вью посту
        val adapter = PostsAdapter(object : OnInterasctionListener {


            override fun onEdit(post: Post) {

                editPostActivityLauncher.launch(post.content)
                viewModel.edit(post)

            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })
        //??
        binding.list.adapter = adapter

        //??
        viewModel.data.observe(this)
        { posts ->
            adapter.list = posts
        }

        //функция, которая будет вызвана при завершении NewPostActivity
        val activityLauncher = registerForActivityResult(NewPostActivity.Contract) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        //запуск активити при клике по "+" c с возвращением результата
        binding.add.setOnClickListener()
        {
            activityLauncher.launch()
        }
    }
}

