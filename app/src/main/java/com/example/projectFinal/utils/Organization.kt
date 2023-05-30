package com.example.projectFinal.utils

data class OrganizationData(
    val name: String,
    val description: String
)

data class CreateOrganizationRequest(val organization: OrganizationData)