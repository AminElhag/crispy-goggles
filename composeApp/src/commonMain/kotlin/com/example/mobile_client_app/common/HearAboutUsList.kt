package com.example.mobile_client_app.common

import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

val hearAboutUs = mutableListOf(
    HearAboutUs(Res.string.social_media, 1),
    HearAboutUs(Res.string.search_engine, 2),
    HearAboutUs(Res.string.email_newsletter, 3),
    HearAboutUs(Res.string.google_ads, 4),
    HearAboutUs(Res.string.facebook_ads, 5),
    HearAboutUs(Res.string.blogs_or_articles, 6),
    HearAboutUs(Res.string.affiliate_links, 7),
    HearAboutUs(Res.string.customer_review_sites, 8),
)

data class HearAboutUs(
    val name: StringResource,
    val id: Int
)