package com.example.ydzeinEmployee.core.domain.model

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class TargetModel(
    var id: String = "",
    var idEmployee: String = "",
    var idDivision: String = "",
    var dateStart: String = "",
    var dateFinish: String = "",
    var targetBeenDone: Int = 0,
    var totalTarget: Int = 0,
    var productType: String = "",
    var targetType: String = "",
): Parcelable

fun TargetModel.toUriString(): String {
    return Gson().toJson(this)
}

fun String.toTarget(): TargetModel? {
    return try {
        val type = object : com.google.gson.reflect.TypeToken<TargetModel>() {}.type
        Gson().fromJson(this, type)
    } catch (e: Exception) {
        null
    }
}

data class TargetModelRequest(
    var idEmployee: String = "",
    var idDivision: String = "",
    var dateStart: String = "",
    var dateFinish: String = "",
    var targetBeenDone: Int = 0,
    var totalTarget: Int = 0,
    var productType: String = "",
    var targetType: String = "",
)