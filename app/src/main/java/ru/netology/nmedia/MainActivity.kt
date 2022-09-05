package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
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

        //адаптер - класс для предоставления вью посту
        val adapter = PostsAdapter({
            viewModel.likeById(it.id)
        }, {
            viewModel.shareById(it.id)
        })


        //??
        binding.list.adapter = adapter

        //??
        viewModel.data.observe(this)
        { posts ->
            adapter.list = posts
        }
    }
}

