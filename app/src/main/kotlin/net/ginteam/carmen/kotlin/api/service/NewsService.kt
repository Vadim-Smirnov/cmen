package net.ginteam.carmen.kotlin.api.service

import net.ginteam.carmen.kotlin.api.ApiSettings
import net.ginteam.carmen.kotlin.manager.ApiManager
import net.ginteam.carmen.kotlin.model.NewsModel
import net.ginteam.carmen.kotlin.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by vadik on 09.03.17.
 */
interface NewsService {

    @GET(ApiSettings.News.ARTICLES)
    fun fetchCompaniesCountWithParameters(
            @Query(ApiSettings.News.Params.PAGE) page: Int
    ): Observable<ResponseModel<MutableList <NewsModel>>>

    companion object {
        fun create(): NewsService = ApiManager.retrofit.create(NewsService::class.java)
    }

}