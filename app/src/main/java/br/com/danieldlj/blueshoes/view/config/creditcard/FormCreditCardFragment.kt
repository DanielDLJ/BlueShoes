package br.com.danieldlj.blueshoes.view.config.creditcard

import android.os.Bundle
import android.view.View
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.util.isValidCNPJ
import br.com.danieldlj.blueshoes.util.isValidCPF
import br.com.danieldlj.blueshoes.view.config.ConfigFormFragment
import com.santalu.maskedittext.MaskEditText
import kotlinx.android.synthetic.main.fragment_config_new_credit_card.*

class FormCreditCardFragment : ConfigFormFragment(), View.OnFocusChangeListener {


    override fun getLayoutResourceID() = R.layout.fragment_config_new_credit_card

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        updateFlFormToFullFreeScreen()

        bt_add_credit_card.setOnClickListener{
            /*
             * O método mainAction() é invocado no lugar
             * de callPasswordDialog(), pois aqui não há
             * necessidade de dialog de senha para a
             * adição de cartão de crédito.
             * */
            mainAction()
        }

        et_credit_card_security_code.setOnEditorActionListener( this )
        met_credit_card_owner_reg.setOnFocusChangeListener( this )
    }

    override fun title() = R.string.config_credit_cards_tab_new

    override fun backEndFakeDelay(){
        backEndFakeDelay(false, getString( R.string.invalid_credit_card ))
    }

    override fun blockFields( status: Boolean ){
        met_credit_card_number.isEnabled = !status
        sp_credit_card_enterprise.isEnabled = !status
        et_credit_card_owner_name.isEnabled = !status
        met_credit_card_owner_reg.isEnabled = !status
        sp_credit_card_expiry_month.isEnabled = !status
        et_credit_card_expiry_year.isEnabled = !status
        et_credit_card_security_code.isEnabled = !status
        bt_add_credit_card.isEnabled = !status
    }

    override fun isMainButtonSending( status: Boolean ){
        bt_add_credit_card.text =
            if( status )
                getString( R.string.add_credit_card_going )
            else
                getString( R.string.add_credit_card )
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        var mask = "" /* Sem máscara. */
        val editText = view as MaskEditText

        /*
         * Garantindo que o conteúdo em teste terá apenas
         * números.
         * */
        val pattern = Regex("[^0-9 ]")
        val content = editText
            .text
            .toString()
            .replace( pattern, "" )

        if( !hasFocus ){
            if( content.isValidCPF() ){
                mask = "###.###.###-##" // Máscara CPF.
            }
            else if( content.isValidCNPJ() ){

                mask = "##.###.###/####-##" // Máscara CNPJ.
            }
        }

        /*
         * Aplicando a nova máscara de acordo com o
         * padrão de código presente em campo.
         * */
        editText.mask = mask

        /*
         * Para que a nova máscara seja aplicada é preciso
         * forçar uma atualização no campo.
         * */
        editText.setText( content )
    }
}