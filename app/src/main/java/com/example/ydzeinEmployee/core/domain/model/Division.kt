package com.example.ydzeinEmployee.core.domain.model

import com.example.ydzeinEmployee.core.domain.model.auth.TargetModel

data class Division(
    val id: String = "",
    val name: String = "",
    var listOfTarget: List<TargetModel> = listOf()
)