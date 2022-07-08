package com.example.habits_tracker.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.habits_tracker.R
import com.example.habits_tracker.databinding.DescriptionInputViewBinding
import com.example.habits_tracker.databinding.TextInputBinding

class DescriptionInputView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttrs: Int = 0
) : FrameLayout(context, attrs, defStyleAttrs)
{

    init {
        init(context, attrs)
    }

    private lateinit var binding: DescriptionInputViewBinding

    var description_text: String
        get() = binding.descriptionInputEditText.text.toString()
        set(text){
            binding.descriptionInputEditText.setText(text)
        }


    private fun init(context: Context, attrs: AttributeSet?){
        binding = DescriptionInputViewBinding.inflate(LayoutInflater.from(context), this, true)
        if (attrs != null){
            loadAttributes(attrs)
        }
    }

    private fun loadAttributes(attrs: AttributeSet){
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.DescriptionInputView)
        val hint = attributes.getString(R.styleable.DescriptionInputView_description_hint)
        binding.descriptionInputLayout.setHint(hint)
        attributes.recycle()
    }

    fun setError(error: String?){
        binding.descriptionInputLayout.error = error
    }

    fun addTextChangeListener(watcher: TextWatcher){
        binding.descriptionInputEditText.addTextChangedListener(watcher)
    }

}