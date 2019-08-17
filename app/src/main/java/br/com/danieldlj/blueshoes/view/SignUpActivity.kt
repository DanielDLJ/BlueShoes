package br.com.danieldlj.blueshoes.view

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.util.isValidEmail
import br.com.danieldlj.blueshoes.util.isValidPassword
import br.com.danieldlj.blueshoes.util.validate
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.android.synthetic.main.content_form.*
import kotlinx.android.synthetic.main.content_sign_up.*
import kotlinx.android.synthetic.main.text_view_privacy_policy_login.*


class SignUpActivity : FormActivity(), KeyboardUtils.OnSoftInputChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        View.inflate(this, R.layout.content_sign_up, fl_form)

        /*
         * Colocando configuração de validação de campo de email
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_email.validate({
                it.isValidEmail() }, getString( R.string.invalid_email ))

        /*
         * Colocando configuração de validação de campo de senha
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_password.validate({
            it.isValidPassword() }, getString( R.string.invalid_password ))

        /*
         * Colocando configuração de validação de campo de email
         * para enquanto o usuário informa o conteúdo deste campo.
         * */
        et_confirm_password.validate(
            {
                /*
                 * O toString() em et_password.text.toString() é
                 * necessário, caso contrário a validação falha
                 * mesmo quando é para ser ok.
                 * */
                (et_password.text.isNotEmpty() && it.equals( et_password.text.toString() )) || et_password.text.isEmpty()
            },
            getString( R.string.invalid_confirmed_password )
        )

        et_confirm_password.setOnEditorActionListener( this )

        /*
         * Com a API KeyboardUtils conseguimos de maneira
         * simples obter o status atual do teclado virtual (aberto /
         * fechado) e assim prosseguir com algoritmos de ajuste de
         * layout.
         * */
        KeyboardUtils.registerSoftInputChangedListener( this, this )
    }


    /*
    * Caso o usuário toque no botão "Done" do teclado virtual
    * ao invés de tocar no botão "Entrar". Mesmo assim temos
    * de processar o formulário.
    * */
    override fun onEditorAction(view: TextView, actionId: Int, event: KeyEvent? ): Boolean {

        mainAction()
        return false
    }

    override fun onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(this)
        super.onDestroy()
    }

    override fun onSoftInputChanged( height: Int ) {
        changePrivacyPolicyConstraints(KeyboardUtils.isSoftInputVisible( this ))
    }


    //Cadastro
    override fun mainAction( view: View? ){
        blockFields( true )
        isMainButtonSending( true )
        showProxy( true )
        backEndFakeDelay(false, getString( R.string.invalid_sign_up_email ))
    }

    override fun blockFields( status: Boolean ){
        et_email.isEnabled = !status
        et_password.isEnabled = !status
        et_confirm_password.isEnabled = !status
        bt_sign_up.isEnabled = !status
    }

    /*
     * Muda o rótulo do botão de login de acordo com o status
     * do envio de dados de login.
     * */
    override fun isMainButtonSending( status: Boolean ){
        bt_sign_up.text =
            if( status )
                getString( R.string.sign_up_going )
            else
                getString( R.string.sign_up )
    }

    private fun changePrivacyPolicyConstraints(isKeyBoardOpened: Boolean){

        val privacyId = tv_privacy_policy.id
        val parent = tv_privacy_policy.parent as ConstraintLayout
        val constraintSet = ConstraintSet()

        /*
         * Definindo a largura e a altura da View em
         * mudança de constraints, caso contrário ela
         * fica com largura e altura em 0dp.
         * */
        constraintSet.constrainWidth(privacyId, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        constraintSet.constrainHeight(privacyId, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        /*
         * Centralizando a View horizontalmente no
         * ConstraintLayout.
         * */
        constraintSet.centerHorizontally(privacyId, ConstraintLayout.LayoutParams.PARENT_ID)

        if( isKeyBoardOpened || ScreenUtils.isLandscape()){
            /*
             * Se o teclado virtual estiver aberto, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada a
             * View acima dela (tv_sign_up).
             * */
            constraintSet.connect(privacyId, ConstraintLayout.LayoutParams.TOP, bt_sign_up.id, ConstraintLayout.LayoutParams.BOTTOM,
                (12 * ScreenUtils.getScreenDensity()).toInt())
        }
        else{
            /*
             * Se o teclado virtual estiver fechado, então
             * mude a configuração da View alvo
             * (tv_privacy_policy) para ficar vinculada ao
             * fundo do ConstraintLayout ancestral.
             * */
            constraintSet.connect(privacyId, ConstraintLayout.LayoutParams.BOTTOM, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintLayout.LayoutParams.BOTTOM)
        }

        constraintSet.applyTo( parent )
    }

    fun callPrivacyPolicyFragment( view: View ){
        val intent = Intent(this, MainActivity::class.java)

        /*
         * Para saber qual fragmento abrir quando a
         * MainActivity voltar ao foreground.
         * */
        intent.putExtra(MainActivity.FRAGMENT_ID, R.id.item_privacy_policy)

        /*
         * Removendo da pilha de atividades a primeira
         * MainActivity aberta (e a LoginActivity), para
         * deixar somente a nova MainActivity com uma nova
         * configuração de fragmento aberto.
         * */
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity( intent )
    }

    fun callLoginActivity( view: View ){
        finish()
    }


}
