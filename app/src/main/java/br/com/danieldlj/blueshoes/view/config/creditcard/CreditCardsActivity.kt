package br.com.danieldlj.blueshoes.view.config.creditcard

import br.com.danieldlj.blueshoes.view.config.ConfigFormActivity
import br.com.danieldlj.blueshoes.view.config.ConfigSectionsAdapter

class CreditCardsActivity : ConfigFormActivity() {

    override fun getSectionsAdapter()
            = ConfigSectionsAdapter(this, supportFragmentManager, CreditCardsListFragment(), FormCreditCardFragment())
}