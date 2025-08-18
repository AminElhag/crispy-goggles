package com.example.mobile_client_app.features.auth.profile.domain.models

import mobile_client_app.composeapp.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class MemberStatus(val id: Int) {
    ACTIVE(0), INACTIVE(1), CANCELLED(2), BLOCKED(3), DELETED(4);
    companion object {
        fun fromId(id: Int): MemberStatus? {
            return entries.find { it.id == id }
        }
    }
}

fun getMemberStatus(memberStatus: MemberStatus): StringResource {
    return when (memberStatus) {
        MemberStatus.ACTIVE -> Res.string.active_membership
        MemberStatus.INACTIVE -> Res.string.inactive_membership
        MemberStatus.CANCELLED -> Res.string.cancelled_membership
        MemberStatus.BLOCKED -> Res.string.blocked_membership
        MemberStatus.DELETED -> Res.string.deleted_membership
    }
}

fun getMemberStatus(id: Int): StringResource? {
    return MemberStatus.fromId(id)?.let { getMemberStatus(it) }
}