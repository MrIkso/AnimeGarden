@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.xiaoyv.anime.garden.kts.formatDHM
import com.xiaoyv.widget.kts.orEmpty
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * Class: [AnilistMediaEntity]
 *
 * @author why
 * @since 11/22/23
 */
@Keep
@Parcelize
data class AnilistMediaEntity(
    @SerializedName("averageScore")
    var averageScore: Int = 0,
    @SerializedName("bannerImage")
    var bannerImage: String? = null,
    @SerializedName("chapters")
    var chapters: Int = 0,
    @SerializedName("coverImage")
    var coverImage: AnilistImageEntity? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("duration")
    var duration: Int = 0,
    @SerializedName("endDate")
    var endDate: DateInfo? = null,
    @SerializedName("episodes")
    var episodes: Int = 0,
    @SerializedName("format")
    var format: String? = null,
    @SerializedName("genres")
    var genres: List<String?>? = null,
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("isAdult")
    var isAdult: Boolean = false,
    @SerializedName("mediaListEntry")
    var mediaListEntry: MediaListEntry? = null,
    @SerializedName("nextAiringEpisode")
    var nextAiringEpisode: NextAiringEpisode? = null,
    @SerializedName("popularity")
    var popularity: Int = 0,
    @SerializedName("season")
    var season: String? = null,
    @SerializedName("seasonYear")
    var seasonYear: Int = 0,
    @SerializedName("startDate")
    var startDate: DateInfo? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("studios")
    var studios: AnilistStudiosEntity? = null,
    @SerializedName("title")
    var title: AnilistNameEntity? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("volumes")
    var volumes: Int = 0,
    @SerializedName("characterPreview")
    var characterPreview: AnilistCharacterEntity? = null,
    @SerializedName("countryOfOrigin")
    var countryOfOrigin: String? = null,
    @SerializedName("externalLinks")
    var externalLinks: List<AnilistExternalLinkEntity>? = null,
    @SerializedName("favourites")
    var favourites: Int = 0,
    @SerializedName("hashtag")
    var hashtag: String? = null,
    @SerializedName("isFavourite")
    var isFavourite: Boolean = false,
    @SerializedName("isFavouriteBlocked")
    var isFavouriteBlocked: Boolean = false,
    @SerializedName("isLicensed")
    var isLicensed: Boolean = false,
    @SerializedName("isLocked")
    var isLocked: Boolean = false,
    @SerializedName("isRecommendationBlocked")
    var isRecommendationBlocked: Boolean = false,
    @SerializedName("isReviewBlocked")
    var isReviewBlocked: Boolean = false,
    @SerializedName("meanScore")
    var meanScore: Int = 0,
    @SerializedName("rankings")
    var rankings: List<Ranking>? = null,
    @SerializedName("recommendations")
    var recommendations: AnilistRecommendationsEntity? = null,
    @SerializedName("relations")
    var relations: AnilistRelationsEntity? = null,
    @SerializedName("source")
    var source: String? = null,
    @SerializedName("staffPreview")
    var staffPreview: AnilistStaffEntity? = null,
    @SerializedName("stats")
    var stats: AnilistStatsEntity? = null,
    @SerializedName("streamingEpisodes")
    var streamingEpisodes: List<StreamingEpisode>? = null,
    @SerializedName("synonyms")
    var synonyms: List<String>? = null,
    @SerializedName("tags")
    var tags: List<Tag>? = null
) : Parcelable {
    val isFinished: Boolean
        get() = nextAiringEpisode == null

    val coverImageUrl: String?
        get() = coverImage?.avaivableUrl ?: bannerImage

    val bannerImageUrl: String?
        get() = bannerImage ?: coverImage?.avaivableUrl

    /**
     * 最新的当前话
     */
    val currentEpisode: Int
        get() = (nextAiringEpisode?.episode.orEmpty() - 1).coerceAtLeast(0)

    /**
     * AiringEpisode 描述
     */
    fun nextAiringEpisodeDesc(): String {
        val airingEpisode = nextAiringEpisode ?: return "FINISHED"
        val episode = airingEpisode.episode
        val timeUntilAiring = airingEpisode.timeUntilAiring * 1000
        return String.format("Episode %d: %s", episode, timeUntilAiring.formatDHM())
    }

    @Keep
    @Parcelize
    data class NextAiringEpisode(
        @SerializedName("airingAt")
        var airingAt: Long = 0,
        @SerializedName("episode")
        var episode: Int = 0,
        @SerializedName("timeUntilAiring")
        var timeUntilAiring: Long = 0
    ) : Parcelable

    @Keep
    @Parcelize
    data class MediaListEntry(
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("status")
        var status: String? = null
    ) : Parcelable


    @Keep
    @Parcelize
    data class DateInfo(
        @SerializedName("day")
        var day: Int = 0,
        @SerializedName("month")
        var month: Int = 0,
        @SerializedName("year")
        var year: Int = 0
    ) : Parcelable {

        fun formatLocalDate(): String {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month - 1)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.timeZone = TimeZone.getDefault()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }


    @Keep
    @Parcelize
    data class Ranking(
        @SerializedName("allTime")
        var allTime: Boolean = false,
        @SerializedName("context")
        var context: String? = null,
        @SerializedName("format")
        var format: String? = null,
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("rank")
        var rank: Int = 0,
        @SerializedName("season")
        var season: String? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("year")
        var year: Int = 0
    ) : Parcelable

    @Keep
    @Parcelize
    data class StreamingEpisode(
        @SerializedName("site")
        var site: String? = null,
        @SerializedName("thumbnail")
        var thumbnail: String? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("url")
        var url: String? = null
    ) : Parcelable

    @Keep
    @Parcelize
    data class Tag(
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("isGeneralSpoiler")
        var isGeneralSpoiler: Boolean = false,
        @SerializedName("isMediaSpoiler")
        var isMediaSpoiler: Boolean = false,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("rank")
        var rank: Int = 0,
        @SerializedName("userId")
        var userId: Int = 0,
        @SerializedName("category")
        var category: String? = null,
        @SerializedName("isAdult")
        var isAdult: Boolean = false
    ) : Parcelable
}