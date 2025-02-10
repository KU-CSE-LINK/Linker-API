package com.linker.linkerapi.equipment.controller

import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.equipment.service.EquipmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Equipment Controller")
@RestController
class EquipmentController(
    private val equipmentService: EquipmentService
) {
    @Operation(summary = "기자재 조회", description = "저장되어 있는 모든 기자재를 조회합니다.")
    @GetMapping("/equipments")
    fun getEquipments(): List<Equipment> {
        val equipments = equipmentService.getEquipments()
        return equipments
    }
}
