package com.example.projectFinal.utils

import com.google.gson.annotations.SerializedName

data class Organization(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val website: String?
)

data class OrganizationItem(
    val role: String,
    @SerializedName("Organization")
    val organization: Organization
)

data class OrganizationList(
    val organizations: List<OrganizationItem>
)
