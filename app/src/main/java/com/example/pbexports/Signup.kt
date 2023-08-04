package com.example.pbexports

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_signup.*

class Signup : AppCompatActivity() {

    val TAG: String = "SignUp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

       et_number.setOnEditorActionListener { v, actionId, event ->
           if(actionId == EditorInfo.IME_ACTION_DONE) {
               Signup(v)
               true
           } else false
       }
    }

    fun Signup(v : View) {

        var isExistBlank = false
        var isPWDSame = false

        // 키보드 숨기기
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)

        val name = et_name.text.toString()
        val email = et_email.text.toString()
        val pwd = et_pwd.text.toString()
        val pwdRe = et_pwdRe.text.toString()
        val number = et_number.text.toString()

        // 항목을 다 채우지 않은 경우
        if(name.isEmpty() || email.isEmpty() || pwd.isEmpty() || pwdRe.isEmpty() || number.isEmpty()){
            isExistBlank = true
        } else if(pwd == pwdRe){
            isPWDSame = true
        }

        if(!isExistBlank && isPWDSame){
            // 회원가입 성공 토스트
            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

            // 입력 값을 쉐어드에 저장
            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("name", name).apply()
            editor.putString("email", email).apply()
            editor.putString("pwd", pwd).apply()

            // 로그인 화면 이동
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        } else{
            // 상태에 따라 다른 다이얼로그 띄우기
            if(isExistBlank){
                dialog("blank")
            } else if(!isPWDSame){
                dialog("not same")
            }
        }
    }

    fun dialog(type: String){
        val dialog = AlertDialog.Builder(this)

        // 작성 안한 항목이 있을 경우
        if(type.equals("blank")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        // 입력한 비밀번호가 다를 경우
        else if(type.equals("not same")){
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }

        val dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "다이얼로그")
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }
}