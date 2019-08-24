package br.com.danieldlj.blueshoes.domain

import br.com.danieldlj.blueshoes.view.FormActivity

class AccountSettingItem(
    val label: String,
    val description: String,
    val activityClass: Class<out FormActivity>
)