package br.com.danieldlj.blueshoes.view.config.connectiondata

import br.com.danieldlj.blueshoes.view.config.ConfigFormActivity
import br.com.danieldlj.blueshoes.view.config.ConfigSectionsAdapter

class ConnectDataActivity : ConfigFormActivity() {

    override fun getSectionsAdapter()
            = ConfigSectionsAdapter(this, supportFragmentManager, FormEmailFragment(), FormPasswordFragment())

}