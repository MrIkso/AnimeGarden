package com.xiaoyv.anime.garden.api

import com.xiaoyv.anime.garden.api.request.AnimeGalleryParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistGraphqlParam
import com.xiaoyv.anime.garden.api.response.AnimeDetailEntity
import com.xiaoyv.anime.garden.api.response.AnimeGallery
import com.xiaoyv.anime.garden.api.response.AnimeSourceEntity
import com.xiaoyv.anime.garden.api.response.DetectCharacterEntity
import com.xiaoyv.anime.garden.api.response.MagnetListEntity
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.api.response.anilist.AnilistResponse
import com.xiaoyv.anime.garden.api.response.base.BaseListResponse
import com.xiaoyv.anime.garden.api.response.base.BaseResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

/**
 * AnimeApi
 *
 * @author why
 * @since 11/18/23
 */
interface AnimeApi {

    @GET("/api/anime/list")
    suspend fun queryAnimeList(@QueryMap query: Map<String, String>): BaseListResponse<MagnetListEntity>

    @GET("/api/anime/detail")
    suspend fun queryAnimeDetail(@Query("id") id: String): BaseResponse<AnimeDetailEntity>

    @Multipart
    @POST("https://api.trace.moe/search")
    suspend fun queryAnimeByImage(@Part file: MultipartBody.Part): AnimeSourceEntity

    @POST
    suspend fun queryAnimeGallery(
        @Url url: String,
        @Body exclude: AnimeGalleryParam = AnimeGalleryParam()
    ): AnimeGallery

    /**
     * 识别动漫图片人物
     *
     * @param model 模型
     * @param forceOne 是否仅识别单个，0：仅识别单个人物；1：识别多个人物
     * @param aiDetect 是否识别 AI-GC
     */
    @Multipart
    @POST("https://aiapiv2.animedb.cn/ai/api/detect")
    suspend fun queryAnimeCharacter(
        @Query("model") model: String,
        @Query("force_one") forceOne: Int,
        @Query("ai_detect") aiDetect: Int,
        @Part file: MultipartBody.Part
    ): BaseListResponse<DetectCharacterEntity>


    @POST("https://graphql.anilist.co")
    suspend fun queryAnimeGraphql(@Body param: AnilistGraphqlParam): AnilistResponse<AnilistMediaEntity>
}