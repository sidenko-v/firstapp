package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding соединяет с UI, с конкретной активити
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //фокус на поле ввода
        binding.edit.requestFocus()
        binding.edit.setText(Intent().getStringExtra("content"))

        //поведение при клике по "+"
        binding.ok.setOnClickListener {

            //intent (намерение) используется для активации компонентов
            val intent = Intent()

            //проверяем если в поле ввода пусто (нет текста)
            if (binding.edit.text.isBlank()) {

                //Действие отменяется, возврат на активити мейн
                setResult(Activity.RESULT_CANCELED, intent)

            } else {

                //иначе, кладем в переменную текст из поля
                val content = binding.edit.text.toString()

                //кладем этот текст в параметр интента с ключем extra_text
                intent.putExtra(Intent.EXTRA_TEXT, content)

                setResult(Activity.RESULT_OK, intent)
            }
            //завершаем действия при клике "+"
            finish()
        }
    }

    //класс для созданияи интента и обработки возвращаемого результата
    class Contract(content: String?) : ActivityResultContract<Unit, String?>() {

        val content = content

        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, EditPostActivity::class.java).apply {
                putExtra("content", content)
            }


        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            }
    }

}