package com.example.orderfood.model

import com.google.gson.annotations.SerializedName

data class UserData(

    @field:SerializedName("type_account")
    val typeAccount: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("level")
    val level: Int? = null,

    @field:SerializedName("name_staff")
    val nameStaff: String? = null,

    @field:SerializedName("roles_id")
    val rolesId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("page_access")
    val pageAccess: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("current_password")
    var currentPassword: String? = null
    ,
    @field:SerializedName("address")
    var address: String? = null
)
