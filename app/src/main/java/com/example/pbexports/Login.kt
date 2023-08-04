package com.example.pbexports

import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_pwd.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                Signin(v)
                true
            } else false
        }

        // 회원가입 이동
        bt_signup.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

    }

    fun Signin(v : View){
        // 키보드 숨기기
        var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)

        //백엔드로 입력정보 비교하기
        // 입력 값 받아오기
        var id = et_email.text.toString()
        var pwd = et_pwd.text.toString()

        // 쉐어드로 저장된 id, pwd 가져오기
        val sharedPreferences = getSharedPreferences("file name", Context.MODE_PRIVATE)
        val saveId = sharedPreferences.getString("id", "")
        val savePwd = sharedPreferences.getString("pwd", "")

        // 유저 입력 값이랑 비교
        if (id == saveId && pwd == savePwd) {
            dialog("success")
            startActivity(Intent(this, MainActivity::class.java)) //intent 객체를 시작
        } else {
            dialog("fail")
        }

    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String) {
        var dialog = AlertDialog.Builder(this)

        if (type.equals("success")) {
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        } else if (type.equals("fail")) {
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "")
                }
            }
        }

        dialog.setPositiveButton("확인", dialog_listener)
        dialog.show()
    }

}