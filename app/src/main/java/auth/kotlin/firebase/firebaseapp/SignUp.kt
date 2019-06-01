package auth.kotlin.firebase.firebaseapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.w3c.dom.Text

class SignUp : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()

        textViewLogin.setOnClickListener {
    val intent = Intent(this,LoginActivity::class.java)
    startActivity(intent)
}
        btnRegister.setOnClickListener {
register()
        }

    }
    private fun register(){
        var email = edEmail.text.toString()
        var password = edPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            progressbar.visibility =View.VISIBLE
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        sendEmailVerfication()

                        Toast.makeText(applicationContext, "Please Verfiy Your Email Address", Toast.LENGTH_LONG).show()
                        progressbar.visibility =View.GONE
                    } else
                        Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
                }

        } else {
            Toast.makeText(applicationContext, "Please Enter Your email and password", Toast.LENGTH_LONG).show()
        }
    }
    private  fun sendEmailVerfication(){
        val user =mAuth?.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener {
            if(it.isSuccessful){

                val LoginIntent =Intent(this,LoginActivity::class.java)
                startActivity(LoginIntent)

            }else{
                Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
