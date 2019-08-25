package br.com.danieldlj.blueshoes.data

import android.content.Context
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.domain.AccountSettingItem
import br.com.danieldlj.blueshoes.view.ConfigProfileActivity
import br.com.danieldlj.blueshoes.view.config.connectiondata.ConfigConnectionDataActivity
import br.com.danieldlj.blueshoes.view.config.creditcard.ConfigCreditCardsActivity
import br.com.danieldlj.blueshoes.view.config.deliveryaddress.ConfigDeliveryAddressesActivity

class AccountSettingsItemsDataBase {

    companion object{

        fun getItems( context: Context) = listOf(
            AccountSettingItem(
                context.getString( R.string.setting_item_profile ),
                context.getString( R.string.setting_item_profile_desc ),
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_login ),
                context.getString( R.string.setting_item_login_desc ),
                ConfigConnectionDataActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_address ),
                context.getString( R.string.setting_item_address_desc ),
                ConfigDeliveryAddressesActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_credit_cards ),
                context.getString( R.string.setting_item_credit_cards_desc ),
                ConfigCreditCardsActivity::class.java
            )
        )
    }
}