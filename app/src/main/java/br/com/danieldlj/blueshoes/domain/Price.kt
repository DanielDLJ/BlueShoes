package br.com.danieldlj.blueshoes.domain

import android.content.Context
import br.com.danieldlj.blueshoes.R
import java.util.*

class Price(
    private val normal: Float, // Preço normal.
    private val parcels: Int,
    val hasDiscount: Boolean,
    private val withDiscount: Float // Preço com o desconto já aplicado.
) {

    /*
     * Locale.GERMAN está sendo utilizado para que na
     * separação das casas decimais seja utilizada a
     * vírgula ao invés de ponto.
     * */

    fun getNormalLabel( context: Context) = String.format(
        Locale.GERMAN,
        "%s %.2f",
        context.getString( R.string.money_sign ),
        normal
    )

    fun getWithDiscountLabel( context: Context ) = String.format(
        Locale.GERMAN, "%s %.2f", context.getString( R.string.money_sign ), withDiscount)

    fun getPercentDiscountLabel() : String {
        val percent = ((normal - withDiscount) / normal) * 100

        /*
         * Para apresentar o caractere de percentagem, %,
         * como parte do texto é preciso utilizar dois dele
         * como no String.format() abaixo.
         * */
        return String.format("-%d%%", percent.toInt())
    }

    fun getParcelsLabel( context: Context ) : String {
        val priceParcel = if( hasDiscount )
            withDiscount / parcels
        else
            normal / parcels

        return String.format(
            Locale.GERMAN,
            "%s %dx %s %s %.2f",
            context.getString( R.string.in_until ),
            parcels,
            context.getString( R.string.of ),
            context.getString( R.string.money_sign ),
            priceParcel)
    }
}