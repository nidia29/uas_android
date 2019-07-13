package com.uas.nidiya.models

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id") var id : Int?,
    @SerializedName("title") var title : String?,
    @SerializedName("content") var content : String?,
    @SerializedName("description") var description : String?,
    @SerializedName("image") var image : String?

){
 constructor() : this (null, null, null, null, null)
}
