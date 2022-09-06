package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.utils.AndroidUtils
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
        val adapter = PostsAdapter(object : OnInterasctionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                group.visibility = View.VISIBLE
            }

            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
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

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
                editTextView.setText(post.content)
            }
        }

        binding.resetEditButton.setOnClickListener {
            content.setText("")
            group.visibility = View.INVISIBLE
            viewModel.save()
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val text = text?.toString()
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Content can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text)
                viewModel.save()
                if (group.isVisible) {
                    group.visibility = View.INVISIBLE
                }
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}

