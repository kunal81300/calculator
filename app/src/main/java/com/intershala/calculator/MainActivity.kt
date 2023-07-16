package com.intershala.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.intershala.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var lastnumeric=false
    var lastdot=false
    var stateerror=false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)
    }
    fun onallclearclick(view: View){
        binding.dataTv.text=""
        binding.resultTv.text=""
        lastnumeric=false
        stateerror=false
        lastdot=false
        binding.resultTv.visibility=View.GONE
    }
    fun onclearclick(view: View){
        binding.dataTv.text=""
        lastnumeric=false
    }
    fun ondigitclick(view: View){
        if(stateerror){
            binding.dataTv.text=(view as Button).text
            stateerror=false
        }else{
            binding.dataTv.append((view as Button).text)

        }
        lastnumeric=true
        onequal()
    }
    fun onequalclick(view: View){
        onequal()
        binding.dataTv.text=binding.resultTv.text.toString().drop(1)
    }
    fun onbackclick(view: View){
        binding.dataTv.text=binding.dataTv.text.toString().dropLast(1)
        try {
            val lastchar=binding.dataTv.text.toString().last()
            if(lastchar.isDigit()){
                onequal()
            }
        }catch (e:Exception){
            binding.resultTv.text=""
            binding.resultTv.visibility=View.GONE
            Log.e("last char error",e.toString())
        }

    }
    fun onoperatorclick(view: View){
        if(!stateerror && lastnumeric){
            binding.dataTv.append((view as Button).text)
            lastdot=false
            lastnumeric=false
            onequal()
        }
    }
    fun onequal(){
        if(lastnumeric && !stateerror){
            val txt=binding.dataTv.text.toString()
            expression=ExpressionBuilder(txt).build()
            try {
                val result=expression.evaluate()
                binding.resultTv.visibility=View.VISIBLE
                binding.resultTv.text="="+result.toString()
            }catch (ex:ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.resultTv.text="error"
                stateerror=true
                lastnumeric=false
            }
        }
    }



}