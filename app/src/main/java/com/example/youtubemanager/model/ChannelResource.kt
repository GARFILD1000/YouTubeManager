package com.example.youtubemanager.model

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChannelResource: Resource(){
    var id: String = ""

    var contentDetails: ContentDetails? = null
    var localizations = HashMap<String, String>()
    var targeting: Targeting? = null
    var statistic: Statistics? = null
    var topicDetails: TopicDetails? = null
    var status: Status? = null
    var brandingSettings: BrandingSettings? = null
    var invideoPromotion: InvideoPromotion? = null
    var auditDetails: AuditDetails? = null
    var localizaions = HashMap<String, Localization>()

    class ContentDetails{
        var relatedPlaylists = ArrayList<Playlist>()
        var playlists = ArrayList<String>(0)
        var channels = ArrayList<String>(0)
    }

    class Targeting{
        var languages = ArrayList<String>()
        var regions = ArrayList<String>()
        var countries = ArrayList<String>()
    }

    class Playlist{
        var likes = ""
        var favourites = ""
        var uploads = ""
        var watchHistory = ""
        var watchLater = ""
    }

    class Statistics{
        var viewCount = 0L
        var commentCount = 0L
        var subscriberCount = 0L
        var hiddenSubscriberCount = true
        var videoCount = 0L
    }

    class TopicDetails{
        var topicDetails = ArrayList<String>()
        var topicCategories = ArrayList<String>()
    }

    class Status{
        var privacyStatus = ""
        var isLinked = false
        var longUploadsStatus = ""
    }

    class BrandingSettings{
        var channel: ChannelBrandingSettings? = null
        var watch: WatchBrandingSettings? = null
        var image: ImageBrandingSettings? = null
        var hints = ArrayList<Hint>()

        class ChannelBrandingSettings{
            var title = ""
            var description = ""
            var keyword = ""
            var defaultTab = ""
            var trackingAnalyticsAccountId = ""
            var moderateComments = false
            var showRelatedChannels = false
            var showBrowseView = false
            var featuredChannelsTitle = ""
            var featuredChannelsUrls = ArrayList<String>()
            var unsubscribedTrailer = ""
            var profileColor = ""
            var defaultLanguage = ""
            var country = ""
        }

        class WatchBrandingSettings{
            var textColor = ""
            var backgroundColor = ""
            var featuredPlaylistId = ""
        }

        class ImageBrandingSettings{
            var bannerImageUrl = ""
            var bannerMobileImageUrl = ""
            var watchIconImageUrl = ""
            var trackingImageUrl = ""

            var bannerTabletLowImageUrl = ""
            var bannerTabletImageUrl = ""
            var bannerTabletHdImageUrl = ""
            var bannerTabletExtraHdImageUrl = ""

            var bannerMobileLowImageUrl = ""
            var bannerMobileMediumHdImageUrl = ""
            var bannerMobileHdImageUrl = ""
            var bannerMobileExtraHdImageUrl = ""

            var bannerTvImageUrl = ""
            var bannerTvLowImageUrl = ""
            var bannerTvMediumImageUrl = ""
            var bannerTvHighImageUrl = ""
            var bannerExternalUrl = ""
        }

        class Hint{
            var property = ""
            var value = ""
        }
    }

    class InvideoPromotion{
        var defaultTiming: Timing? = null
        var position: Position? = null
        var items: Item? = null
        var useSmartTiming = false

        class Timing{
            var type = ""
            var offsetMs = 0L
            var durationMs = 0L
        }

        class Position{
            var type = ""
            var cornerPosition = ""
        }

        class Item{
            var id: ItemId? = null
            var timing: Timing? = null
            var customMessage = ""
            var promotedByContentOwner = false

            class ItemId{
                var type = ""
                var videoId = ""
                var websiteUrl = ""
                var recentlyUploadedBy = ""
            }
        }
    }

    class AuditDetails{
        var overallGoodStanding = false
        var communityGuidelinesGoodStanding = false
        var copyrightStrikesGoodStanding = false
        var contentIdClaimsGoodStanding = false
    }

    class ContentOwnerDetails{
        var contentOwner = ""
        var timeLinked = ""
    }

    class Localization{
        var title = ""
        var descriptiom = ""
    }
}