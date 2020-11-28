package com.batuhankoklu.vigames.helpers

import com.batuhankoklu.vigames.model.VideoGame


class StaticVariables {

    companion object{

        var baseUrl : String? = "https://api.coingecko.com/api/v3/"
        var masterPageSelection : Int? = null
        var isEnteredBefore = 0

        var unitTypes = arrayListOf("₿","₺","$")
        var selectedUnit = 2

        var coinId = ""

        var videoGame : VideoGame? = null

    }
}