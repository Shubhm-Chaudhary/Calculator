package com.example.calculator
import net.objecthunter.exp4j.Expression
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigitClick(view: View) {
        if (stateError){
            binding.inputText.text = (view as Button).text
            stateError = false
        }
        else{
          binding.inputText.append((view as Button).text)
        }
        lastNumeric = true
        onEqual()
    }


    fun onAllClearClick(view: View) {
        binding.inputText.text = ""
        binding.resultop.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultop.visibility = View.GONE
    }


    fun onEqualClick(view: View) {
        onEqual()
        binding.inputText.text = ""
    }


    fun onOperatorClick(view: View) {
        if(!stateError && lastNumeric){
            binding.inputText.append((view as Button).text)
            lastDot=false
            lastNumeric=false
            onEqual()
        }
    }


    fun onBackClick(view: View) {
        binding.inputText.text = binding.inputText.text.dropLast(1)
        try {
            val lastChar = binding.inputText.text.lastOrNull()
            if (lastChar != null && lastChar.isDigit()) {
                onEqual()
            }
        } catch (e: Exception) {
            binding.resultop.text = ""
            binding.resultop.visibility = View.GONE
            Log.e("last char error", e.toString())
        }
    }


    fun onClearClick(view: View) {
        binding.inputText.text = ""
        lastNumeric = false
    }
    fun onEqual(){
        if(lastNumeric && !stateError){
            val txt = binding.inputText.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()

                binding.resultop.visibility = View.VISIBLE
                binding.resultop.text = "=$result"

            }catch (ex: ArithmeticException){
                Log.e("evaluation error", ex.toString())
                binding.resultop.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}