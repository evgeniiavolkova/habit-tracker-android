package com.example.habits_tracker.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.habits_tracker.R
import com.example.habits_tracker.databinding.TextInputBinding
import com.example.habits_tracker.databinding.TitleInputViewBinding

class TitleInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{

    init {
        init(context, attrs)
    }

    private lateinit var binding: TitleInputViewBinding

    var title_text: String
        get() = binding.titleInputEditText.text.toString()
        set(text){
            binding.titleInputEditText.setText(text)
        }


    private fun init(context: Context, attrs: AttributeSet?){
        binding = TitleInputViewBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleInputView)
        val hint = attributes.getString(R.styleable.TitleInputView_title_hint)
        binding.titleInputLayout.setHint(hint)
        attributes.recycle()
    }

    fun setError(error: String?){
        binding.titleInputLayout.error = error
    }

    fun addTextChangeListener(watcher: TextWatcher){
        binding.titleInputEditText.addTextChangedListener(watcher)
    }

}