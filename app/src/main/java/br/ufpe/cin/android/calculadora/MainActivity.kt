package br.ufpe.cin.android.calculadora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //Texts Components
    private lateinit var text_info: TextView
    private lateinit var text_calc: EditText

    //Numbers Buttons
    private lateinit var btn_0: Button
    private lateinit var btn_1: Button
    private lateinit var btn_2: Button
    private lateinit var btn_3: Button
    private lateinit var btn_4: Button
    private lateinit var btn_5: Button
    private lateinit var btn_6: Button
    private lateinit var btn_7: Button
    private lateinit var btn_8: Button
    private lateinit var btn_9: Button

    //Operation Buttons
    private lateinit var btn_Divide: Button
    private lateinit var btn_Multiply: Button
    private lateinit var btn_Subtract: Button
    private lateinit var btn_Add: Button
    private lateinit var btn_Clear: Button
    private lateinit var btn_Equal: Button
    private lateinit var btn_Dot: Button
    private lateinit var btn_LParen: Button
    private lateinit var btn_RParen: Button
    private lateinit var btn_Power: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        text_info = findViewById(R.id.text_info)
        text_calc = findViewById(R.id.text_calc)

        btn_0 = findViewById(R.id.btn_0)
        btn_1 = findViewById(R.id.btn_1)
        btn_2 = findViewById(R.id.btn_2)
        btn_3 = findViewById(R.id.btn_3)
        btn_4 = findViewById(R.id.btn_4)
        btn_5 = findViewById(R.id.btn_5)
        btn_6 = findViewById(R.id.btn_6)
        btn_7 = findViewById(R.id.btn_7)
        btn_8 = findViewById(R.id.btn_8)
        btn_9 = findViewById(R.id.btn_9)

        btn_Divide = findViewById(R.id.btn_Divide)
        btn_Multiply = findViewById(R.id.btn_Multiply)
        btn_Subtract = findViewById(R.id.btn_Subtract)
        btn_Add = findViewById(R.id.btn_Add)
        btn_Clear = findViewById(R.id.btn_Clear)
        btn_Equal = findViewById(R.id.btn_Equal)
        btn_Dot = findViewById(R.id.btn_Dot)
        btn_LParen = findViewById(R.id.btn_LParen)
        btn_RParen = findViewById(R.id.btn_RParen)
        btn_Power = findViewById(R.id.btn_Power)

        //Listeners Numbers
        btn_0.setOnClickListener { text_calc.append(btn_0.text) }
        btn_1.setOnClickListener { text_calc.append(btn_1.text) }
        btn_2.setOnClickListener { text_calc.append(btn_2.text) }
        btn_3.setOnClickListener { text_calc.append(btn_3.text) }
        btn_4.setOnClickListener { text_calc.append(btn_4.text) }
        btn_5.setOnClickListener { text_calc.append(btn_5.text) }
        btn_6.setOnClickListener { text_calc.append(btn_6.text) }
        btn_7.setOnClickListener { text_calc.append(btn_7.text) }
        btn_8.setOnClickListener { text_calc.append(btn_8.text) }
        btn_9.setOnClickListener { text_calc.append(btn_9.text) }

        //Listerners Operations
        btn_Divide.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Divide.text.toString()}")}
        btn_Multiply.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Multiply.text.toString()}")}
        btn_Subtract.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Subtract.text.toString()}") }
        btn_Add.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Add.text.toString()}") }
        btn_Dot.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Dot.text.toString()}") }
        btn_LParen.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_LParen.text.toString()}") }
        btn_RParen.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_RParen.text.toString()}") }
        btn_Power.setOnClickListener { text_calc.setText("${text_calc.text.toString()}${btn_Power.text.toString()}")}
        btn_Clear.setOnClickListener {
            text_calc.text.clear()
            if(text_info.text.isNotEmpty()){
                text_info.text = "resultado"

        }}

        //Ao clicar no botão =, é feita a avaliação da expressão, armazenada no widget TextView com id text_info ("resultado");
        btn_Equal.setOnClickListener {
            try{
                if(!text_calc.text.isNullOrEmpty()){
                    var result = eval(text_calc.text.toString())
                    text_info.text = result.toString()
                }
                else{
                    Toast.makeText(applicationContext, "Digite alguma operação", Toast.LENGTH_SHORT).show()
                    text_calc.text.clear()
            }

            }
            catch (e : Exception){
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }


        }


    }

}


//Como usar a função:
    // eval("2+2") == 4.0
    // eval("2+3*4") = 14.0
    // eval("(2+3)*4") = 20.0
    //Fonte: https://stackoverflow.com/a/26227947

    fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch: Char = ' '
            fun nextChar() {
                val size = str.length
                ch = if ((++pos < size)) str.get(pos) else (-1).toChar()
            }

            fun eat(charToEat: Char): Boolean {
                while (ch == ' ') nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Caractere inesperado: " + ch)
                return x
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor
            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'))
                        x += parseTerm() // adição
                    else if (eat('-'))
                        x -= parseTerm() // subtração
                    else
                        return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'))
                        x *= parseFactor() // multiplicação
                    else if (eat('/'))
                        x /= parseFactor() // divisão
                    else
                        return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+')) return parseFactor() // + unário
                if (eat('-')) return -parseFactor() // - unário
                var x: Double
                val startPos = this.pos
                if (eat('(')) { // parênteses
                    x = parseExpression()
                    eat(')')
                } else if ((ch in '0'..'9') || ch == '.') { // números
                    while ((ch in '0'..'9') || ch == '.') nextChar()
                    x = java.lang.Double.parseDouble(str.substring(startPos, this.pos))
                } else if (ch in 'a'..'z') { // funções
                    while (ch in 'a'..'z') nextChar()
                    val func = str.substring(startPos, this.pos)
                    x = parseFactor()
                    if (func == "sqrt")
                        x = Math.sqrt(x)
                    else if (func == "sin")
                        x = Math.sin(Math.toRadians(x))
                    else if (func == "cos")
                        x = Math.cos(Math.toRadians(x))
                    else if (func == "tan")
                        x = Math.tan(Math.toRadians(x))
                    else
                        throw RuntimeException("Função desconhecida: " + func)
                } else {
                    throw RuntimeException("Caractere inesperado: " + ch.toChar())
                }
                if (eat('^')) x = Math.pow(x, parseFactor()) // potência
                return x
            }
        }.parse()
    }




