package com.ydzmobile.employee.core.domain.model

data class TargetListCellModel(
    val id: Int,
    val deadline: String,
    val targetCode: String,
    val target: Int,
    val targetBeenDone: Int,
    val type: String
)
