package br.com.danieldlj.blueshoes.view

import android.content.Intent
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import br.com.danieldlj.blueshoes.R
import br.com.danieldlj.blueshoes.data.NavMenuItemsDataBase
import br.com.danieldlj.blueshoes.domain.NavMenuItem
import br.com.danieldlj.blueshoes.domain.User
import br.com.danieldlj.blueshoes.util.NavMenuItemDetailsLookup
import br.com.danieldlj.blueshoes.util.NavMenuItemKeyProvider
import br.com.danieldlj.blueshoes.util.NavMenuItemPredicate
import br.com.danieldlj.blueshoes.view.config.AccountSettingsActivity
import br.com.danieldlj.blueshoes.view.shoes.AllShoesListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header_user_logged.*
import kotlinx.android.synthetic.main.nav_header_user_not_logged.*
import kotlinx.android.synthetic.main.nav_menu.*

class MainActivity : AppCompatActivity(){


    companion object {
        const val FRAGMENT_TAG = "frag-tag"
        const val FRAGMENT_ID = "frag-id"
    }


    val user = User("Daniel Leme Junior",R.drawable.user,true)

    lateinit var navMenuItems : List<NavMenuItem>
    lateinit var selectNavMenuItems: SelectionTracker<Long>

    lateinit var navMenuItemsLogged : List<NavMenuItem>
    lateinit var selectNavMenuItemsLogged: SelectionTracker<Long>

    lateinit var navMenu: NavMenuItemsDataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main )
        setSupportActionBar( toolbar )

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener( toggle )
        toggle.syncState()

        initNavMenu( savedInstanceState )
        initFragment()

    }

    fun updateToolbarTitleInFragment( titleStringId: Int ){
        toolbar.title = getString( titleStringId )
    }

    /*
     * Método responsável por esconder itens do menu gaveta de
     * acordo com o status do usuário (conectado ou não).
     * */
    private fun showHideNavMenuViews(){
        if(user.status){
            rl_header_user_not_logged.visibility = View.GONE
            fillUserHeaderNavMenu()
        }else{
            rl_header_user_logged.visibility = View.GONE
            v_nav_vertical_line.visibility = View.GONE
            rv_menu_items_logged.visibility = View.GONE
        }
    }

    private fun fillUserHeaderNavMenu(){
        if( user.status ) {
            iv_user.setImageResource(user.image)
            tv_user.text = user.name
        }
    }


    /*
    * Método de inicialização do menu gaveta, responsável por
    * apresentar o cabeçalho e itens de menu de acordo com o
    * status do usuário (logado ou não).
    * */
    private fun initNavMenu( savedInstanceState: Bundle? ){

        navMenu = NavMenuItemsDataBase( this )
        navMenuItems = navMenu.items
        navMenuItemsLogged = navMenu.itemsLogged

        showHideNavMenuViews()

        initNavMenuItems()
        initNavMenuItemsLogged()

        if( savedInstanceState != null ){
            selectNavMenuItems.onRestoreInstanceState( savedInstanceState )
            selectNavMenuItemsLogged.onRestoreInstanceState( savedInstanceState )
        }
        else{
            /*
             * Verificando se há algum item ID em intent. Caso não,
             * utilize o ID do primeiro item.
             * */
            var fragId = intent?.getIntExtra( FRAGMENT_ID, 0 )
            if(fragId == 0){
                fragId = R.id.item_all_shoes
            }

            /*
             * O primeiro item do menu gaveta deve estar selecionado
             * caso não seja uma reinicialização de tela / atividade
             * ou o envio de um ID especifico de fragmento a ser aberto.
             * O primeiro item aqui é o de ID R.id.item_all_shoes.
             * */
            selectNavMenuItems.select( fragId!!.toLong() )


        }
    }



    private fun initNavMenuItems(){
        rv_menu_items.setHasFixedSize( false )
        rv_menu_items.layoutManager = LinearLayoutManager(this)
        rv_menu_items.adapter = NavMenuItemsAdapter( navMenuItems )

        initNavMenuItemsSelection()
    }

    /*
    * Método responsável por inicializar o objeto de seleção
    * de itens de menu gaveta, seleção dos itens que aparecem
    * para usuário conectado ou não.
    * */
    private fun initNavMenuItemsSelection(){

        selectNavMenuItems = SelectionTracker.Builder<Long>(
            "id-selected-item",
            rv_menu_items,
            NavMenuItemKeyProvider( navMenuItems ),
            NavMenuItemDetailsLookup( rv_menu_items ),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate( NavMenuItemPredicate( this ) )
            .build()

        selectNavMenuItems.addObserver(SelectObserverNavMenuItems {
            selectNavMenuItemsLogged.selection.filter {
                    selectNavMenuItemsLogged.deselect( it )
                }
            }
        )

        (rv_menu_items.adapter as NavMenuItemsAdapter).selectionTracker = selectNavMenuItems
    }

    /*
     * Método responsável por inicializar o objeto de seleção
     * de itens de menu gaveta, seleção dos itens que aparecem
     * somente quando o usuário está conectado ao app.
     * */
    private fun initNavMenuItemsLogged(){
        rv_menu_items_logged.setHasFixedSize( true )
        rv_menu_items_logged.layoutManager = LinearLayoutManager(this)
        rv_menu_items_logged.adapter = NavMenuItemsAdapter( NavMenuItemsDataBase( this ).itemsLogged )

        initNavMenuItemsLoggedSelection()
    }

    /*
     * Método responsável por inicializar o objeto de seleção
     * de itens de menu gaveta, seleção dos itens que aparecem
     * somente quando o usuário está conectado ao app.
     * */
    private fun initNavMenuItemsLoggedSelection(){

        selectNavMenuItemsLogged = SelectionTracker.Builder<Long>(
            "id-selected-item-logged",
            rv_menu_items_logged,
            NavMenuItemKeyProvider( navMenuItemsLogged ),
            NavMenuItemDetailsLookup( rv_menu_items_logged ),
            StorageStrategy.createLongStorage()
        )
            .withSelectionPredicate( NavMenuItemPredicate( this ) )
            .build()

        selectNavMenuItemsLogged.addObserver(
            SelectObserverNavMenuItems {
                selectNavMenuItems.selection.filter {
                    selectNavMenuItems.deselect( it )
                }
            }
        )

        (rv_menu_items_logged.adapter as NavMenuItemsAdapter).selectionTracker = selectNavMenuItemsLogged
    }

    override fun onSaveInstanceState( outState: Bundle ) {
        super.onSaveInstanceState( outState )

        /*
         * Para manter o item de menu gaveta selecionado caso
         * haja reconstrução de atividade.
         * */
        selectNavMenuItems.onSaveInstanceState( outState )
        selectNavMenuItemsLogged.onSaveInstanceState( outState )
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }







    inner class SelectObserverNavMenuItems(
        val callbackRemoveSelection: ()->Unit) : SelectionTracker.SelectionObserver<Long>(){

        /*
         * Método responsável por permitir que seja possível
         * disparar alguma ação de acordo com a mudança de
         * status de algum item em algum dos objetos de seleção
         * de itens de menu gaveta. Aqui vamos proceder com
         * alguma ação somente em caso de item obtendo seleção,
         * para item perdendo seleção não haverá processamento,
         * pois este status não importa na lógica de negócio
         * deste método.
         * */
        override fun onItemStateChanged(
            key: Long,
            selected: Boolean ) {
            super.onItemStateChanged( key, selected )

            /*
             * Padrão Cláusula de Guarda para não seguirmos
             * com o processamento em caso de item perdendo
             * seleção. O processamento posterior ao condicional
             * abaixo é somente para itens obtendo a seleção,
             * selected = true.
             * */
            if( !selected ){
                return
            }

            if( isActivityCallInMenu( key ) ) {
                itemCallActivity( key, callbackRemoveSelection )
            }
            else {
                itemCallFragment( key, callbackRemoveSelection )
            }
        }
    }


    private fun initFragment(){
        val supFrag = supportFragmentManager
        var fragment = supFrag.findFragmentByTag( FRAGMENT_TAG )

        if( fragment == null ){

            /*
             * Caso haja algum ID de fragmento em intent, então
             * é este fragmento que deve ser acionado. Caso
             * contrário, abra o fragmento comum de início.
             * */
            var fragId = intent?.getIntExtra( FRAGMENT_ID, 0 )
            if( fragId == 0 ){
                fragId = R.id.item_all_shoes
            }


            fragment = getFragment( fragId!!.toLong() )
        }

        replaceFragment( fragment )
    }

    private fun getFragment( fragmentId: Long ): Fragment {
        return when (fragmentId) {
            R.id.item_about.toLong() -> AboutFragment()
            R.id.item_contact.toLong() -> ContactFragment()
            R.id.item_privacy_policy.toLong() -> PrivacyPolicyFragment()
            else -> AllShoesListFragment()
        }
    }

    private fun replaceFragment( fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_fragment_container, fragment, FRAGMENT_TAG)
            .commit()
    }

    /*
     * Alguns itens do menu gaveta de usuário conectado acionam
     * a abertura de uma atividade e não a abertura de um novo
     * fragmento, dessa forma o método abaixo será útil em
     * lógicas de negócio para informar quais são os itens que
     * acionam atividades.
     * */
    fun isActivityCallInMenu( key: Long ) = when( key ){
        R.id.item_settings.toLong() -> true
        else -> false
    }


    private fun itemCallActivity(key: Long, callbackRemoveSelection: ()->Unit){
        callbackRemoveSelection()

        lateinit var intent : Intent

        when( key ){
            R.id.item_settings.toLong() -> {
                intent = Intent(this, AccountSettingsActivity::class.java)
                intent.putExtra( User.KEY, user )
            }
        }

        navMenu.saveIsActivityItemFired( this, true )
        startActivity( intent )
    }


    private fun itemCallFragment(key: Long, callbackRemoveSelection: ()->Unit){
        /*
         * Para garantir que somente um item de lista se
         * manterá selecionado, é preciso acessar o objeto
         * de seleção da lista de itens de usuário conectado
         * para então remover qualquer possível seleção
         * ainda presente nela. Sempre haverá somente um
         * item selecionado, mas infelizmente o método
         * clearSelection() não estava respondendo como
         * esperado, por isso a estratégia a seguir.
         * */
        callbackRemoveSelection()

        navMenu.saveLastSelectedItemFragmentID( this, key )

        if( !navMenu.wasActivityItemFired( this ) ){
            /*
             * Somente permiti a real seleção de um fragmento e o
             * fechamento do menu gaveta se o item de menu anterior
             * selecionado não tiver sido um que aciona uma atividade.
             * Caso contrário o fragmento já em tela deve continuar
             * e o menu gaveta deve permanecer aberto.
             * */

            val fragment = getFragment( key )
            replaceFragment( fragment )

            //Fechando o menu gaveta.
            drawer_layout.closeDrawer( GravityCompat.START )
        }
        else{
            navMenu.saveIsActivityItemFired( this, false )
        }
    }


    fun callLoginActivity( view: View ){
        val intent = Intent( this, LoginActivity::class.java )
        startActivity( intent )
    }

    fun callSignUpActivity( view: View ){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity( intent )
    }


    override fun onResume() {
        super.onResume()

        /*
         * Se o último item de menu gaveta selecionado foi um
         * que aciona uma atividade, então temos de colocar a
         * seleção de item correta em menu gaveta, item que
         * estava selecionado antes do acionamento do item que
         * invoca uma atividade.
         * */
        if( navMenu.wasActivityItemFired( this ) ){
            selectNavMenuItems.select(navMenu.getLastSelectedItemFragmentID( this ))
        }
    }





}
