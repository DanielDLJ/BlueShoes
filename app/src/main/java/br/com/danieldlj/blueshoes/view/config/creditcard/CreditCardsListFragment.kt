package br.com.danieldlj.blueshoes.view.config.creditcard

import android.os.Bundle
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.data.CreditCardsDataBase
import br.com.danieldlj.blueshoes.view.config.ConfigListFragment
import kotlinx.android.synthetic.main.fragment_config_credit_cards_list.*

class CreditCardsListFragment : ConfigListFragment() {

    override fun title() = R.string.config_credit_cards_tab_list

    override fun backEndFakeDelay() {
        backEndFakeDelay(true, getString( R.string.credit_card_removed ))
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        tv_empty_list.setText( R.string.credit_card_list_empty )
    }

    override fun getRecyclerViewAdapter()
            = CreditCardsListAdapter(this, CreditCardsDataBase.getItems())

}