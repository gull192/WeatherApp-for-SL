package com.example.weather.data.remote.quest

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Coord (

    @SerializedName("name")
    var name : String? = null,
    @SerializedName("local_names")
    var localNames : LocalNames? = LocalNames(),

    @SerializedName("lat")
   public var lat : Double? = null,

    @SerializedName("lon")
    public var lon  : Double? = null,
    @SerializedName("country")
    var country : String? = null

)