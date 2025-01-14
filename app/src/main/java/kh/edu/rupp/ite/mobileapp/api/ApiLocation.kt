package kh.edu.rupp.ite.mobileapp.api // Correct package!

import com.google.gson.annotations.SerializedName

data class ApiLocation(
    @SerializedName("id") val id: Int,       // Assuming 'id' is always present and is an Int
    @SerializedName("name") val name: String // Assuming 'name' is always present
)