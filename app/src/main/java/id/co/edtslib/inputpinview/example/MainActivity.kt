package id.co.edtslib.inputpinview.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.co.edtslib.inputpinview.InputPINDelegate
import id.co.edtslib.inputpinview.InputPINView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<InputPINView>(R.id.inputPinView).delegate = object : InputPINDelegate {
            override fun send(pin: String) {
                Toast.makeText(this@MainActivity, pin, Toast.LENGTH_SHORT).show()
            }
        }
    }
}