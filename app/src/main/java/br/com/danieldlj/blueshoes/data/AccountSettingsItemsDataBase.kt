package br.com.danieldlj.blueshoes.data

import android.content.Context
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.domain.AccountSettingItem
import br.com.danieldlj.blueshoes.view.ConfigProfileActivity

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
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_address ),
                context.getString( R.string.setting_item_address_desc ),
                ConfigProfileActivity::class.java
            ),
            AccountSettingItem(
                context.getString( R.string.setting_item_credit_cards ),
                context.getString( R.string.setting_item_credit_cards_desc ),
                ConfigProfileActivity::class.java
            )
        )
    }
}