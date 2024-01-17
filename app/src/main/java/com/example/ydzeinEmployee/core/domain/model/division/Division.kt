package com.ydzmobile.employee.core.domain.model.division

import com.ydzmobile.employee.core.domain.model.monitor.TargetModel

data class Division(
    val id: String = "",
    val name: String = "",
    var listOfTarget: List<TargetModel> = listOf()
)
