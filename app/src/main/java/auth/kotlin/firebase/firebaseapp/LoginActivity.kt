package auth.kotlin.firebase.firebaseapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mAuth = FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener {
            var intentToSignUp = Intent(this, SignUp::class.java)
            startActivity(intentToSignUp)


        }
        btnLogin.setOnClickListener {

         login()

     }
        btnReset.setOnClickListener {
            var intentToReset = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intentToReset)
        }

    }

    private fun login(){
        val email = edLoginEmail.text.toString()
        val password = edLoginPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()){
            progressbarLogin.visibility = View.VISIBLE
            mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {
                if (it.isSuccessful) {
                  verfiyEmailAddress()
                    progressbarLogin.visibility =View.GONE
                }else{
                    Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }


        }else{
            Toast.makeText(applicationContext,"Empty",Toast.LENGTH_LONG).show()

        }
    }
    private fun verfiyEmailAddress(){
       val user = mAuth?.currentUser
        if(user!!.isEmailVerified){
            Toast.makeText(applicationContext, "Successful Login", Toast.LENGTH_LONG).show()

            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)


        }else{
            Toast.makeText(applicationContext,"Please Verfiy Your Account",Toast.LENGTH_LONG).show()
        }

    }

}