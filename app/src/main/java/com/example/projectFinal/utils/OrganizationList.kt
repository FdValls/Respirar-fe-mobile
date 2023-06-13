package com.example.projectFinal.utils

import com.google.gson.annotations.SerializedName

data class Organization(
    val id: String,
    var name: String,
    var description: String,
    val image: String,
    var website: String?
)

data class OrganizationItem(
    val role: String,
    @SerializedName("Organization")
    val organization: Organization
)

data class OrganizationList(
    val organizations: List<OrganizationItem>
)


