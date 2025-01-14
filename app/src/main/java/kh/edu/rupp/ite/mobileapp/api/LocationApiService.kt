package kh.edu.rupp.ite.mobileapp.api

import retrofit2.http.GET

interface LocationApiService {
    @GET("place-about.php")
    suspend fun getLocations(): List<ApiLocation>
}