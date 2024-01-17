package com.ydzmobile.employee.core.domain.model.auth

import com.google.errorprone.annotations.Keep

@Keep
data class User(
    var uid: String,
    var idEmployee: String? = null,
    var name: String? = null,
    var nik: String? = null,
    var email: String,
    var dateBirth: String? = null,
    var phoneNumber: String? = null,
    var address: String? = null,
    var rt: String? = null,
    var division: String? = null,
    var bloodType: String? = null,
    var profilePicture: String? = null,
    var role: String? = null,
    @field:JvmField
    var isSupervisor: Boolean = false
) {
    // No-argument constructor
    constructor() : this("", null, null, null, "", null, null, null, null, null, null, null, null, false)
}
