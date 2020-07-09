package com.rossellamorgante.stackexchange.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Items(val items: ArrayList<User> = arrayListOf())


data class User(@SerializedName("account_id")
                val account_id: Int?,
                @SerializedName("reputation")
                val reputation: Int?,
                @SerializedName("creation_date")
                val creation_date: Double?,
                @SerializedName("user_id")
                val user_id: Int?,
                @SerializedName("location")
                val location: String?,
                @SerializedName("profile_image")
                val profile_image: String?,
                @SerializedName("display_name")
                val display_name: String?,
                @SerializedName("badge_counts")
                val badge_counts: Badge?
): Serializable

data class Badge(
    @SerializedName("bronze")
    val bronze: Int?,
    @SerializedName("silver")
    val silver: Int?,
    @SerializedName("gold")
    val gold: Int?
): Serializable{
    fun printBadge() : String{

        var result : String =
            "Bronze: " + bronze + "\nSilver: " + silver + "\nGold: " + gold

        return result

    }
}
