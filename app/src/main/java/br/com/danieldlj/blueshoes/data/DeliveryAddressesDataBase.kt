package br.com.danieldlj.blueshoes.data

import br.com.danieldlj.blueshoes.domain.DeliveryAddress

class DeliveryAddressesDataBase {

    companion object{

        fun getItems()
                = mutableListOf(
            DeliveryAddress(
                "Rua Doutor Braguinha",
                15,
                "",
                "18010-120",
                "Centro",
                "Sorocaba",
                24
            ),
            DeliveryAddress(
                "Rua do Ouvidor",
                456,
                "Casa 2",
                "20010-150",
                "Centro",
                "Rio de Janeiro",
                18
            ),
            DeliveryAddress(
                "Rua 31",
                12,
                "Happy Days",
                "74015-070",
                "Setor Central",
                "Goiânia",
                8
            ),
            DeliveryAddress(
                "Rua dos alfeneiros",
                4,
                "Ao lado do Big Ben",
                "85884-000",
                "Little Whinging",
                "Medianeira",
                15
            )
        )
    }
}