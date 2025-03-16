package com.linker.linkerapi.equipment.dto

class AddEquipmentRequestBody(
    val name: String,
    val imageUrl: String,
    val totalStock: Int,
    val availableStock: Int,
)