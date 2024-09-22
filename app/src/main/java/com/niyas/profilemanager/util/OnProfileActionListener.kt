package com.niyas.profilemanager.util

import com.niyas.profilemanager.roomdb.Profile

interface OnProfileActionListener {
    fun onProfileAccepted(profile: Profile)
    fun onProfileRejected(profile: Profile)
}