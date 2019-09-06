package br.com.danieldlj.blueshoes.view.config.deliveryaddress

import android.os.Bundle
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.data.DeliveryAddressesDataBase
import br.com.danieldlj.blueshoes.view.config.ConfigListFragment
import kotlinx.android.synthetic.main.fragment_config_delivery_addresses_list.*

class DeliveryAddressesListFragment : ConfigListFragment() {

    companion object{
        const val TAB_TITLE = R.string.config_delivery_addresses_tab_list
    }

    override fun title() = TAB_TITLE

    override fun backEndFakeDelay() {
        backEndFakeDelay(true, getString( R.string.delivery_address_removed ))
    }

    override fun onActivityCreated( savedInstanceState: Bundle? ) {
        super.onActivityCreated( savedInstanceState )

        tv_empty_list.setText( R.string.delivery_address_list_empty )
    }

    override fun getRecyclerViewAdapter()
            = DeliveryAddressesListAdapter(this, DeliveryAddressesDataBase.getItems())
}