package br.com.danieldlj.blueshoes.view

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.util.isValidEmail
import br.com.danieldlj.blueshoes.util.validate
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.content_forgot_password.*
import kotlinx.android.synthetic.main.info_block.*


class ForgotPasswordActivity : FormActivity(), KeyboardUtils.OnSoftInputChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * Colocando configuração de validação de campo de email
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_email.validate({
                it.isValidEmail() }, getString( R.string.invalid_email ))

        et_email.setOnEditorActionListener( this )
        tv_info_block.text = getString( R.string.forgot_password_info )

    }

    override fun getLayoutResourceID() = R.layout.content_forgot_password

    /*
    * Caso o usuário toque no botão "Done" do teclado virtual
    * ao invés de tocar no botão "Entrar". Mesmo assim temos
    * de processar o formulário.
    * */
    override fun onEditorAction(view: TextView, actionId: Int, event: KeyEvent? ): Boolean {

        mainAction()
        return false
    }

    override fun backEndFakeDelay(){
        backEndFakeDelay(
            false,
            getString( R.string.invalid_login_email )
        )
    }

    override fun blockFields( status: Boolean ){
        et_email.isEnabled = !status
        bt_recover_password.isEnabled = !status
    }

    /*
     * Muda o rótulo do botão de login de acordo com o status
     * do envio de dados de login.
     * */
    override fun isMainButtonSending( status: Boolean ){
        bt_recover_password.text = if( status )
            getString( R.string.recover_password_going  )
        else
            getString( R.string.recover_password )
    }

    override fun onSoftInputChanged(height: Int) {}

}
