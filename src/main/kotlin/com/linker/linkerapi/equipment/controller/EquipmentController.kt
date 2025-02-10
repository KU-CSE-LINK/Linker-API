package com.linker.linkerapi.equipment.controller

import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.equipment.service.EquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/equipments")
class EquipmentController(
    private val equipmentService: EquipmentService
) {
    @GetMapping("/available")
    fun getAvailableEquipments(): ResponseEntity<List<Equipment>> {
        val availableEquipments: List<Equipment> = equipmentService.getAvailableEquipment()
        return ResponseEntity.ok(availableEquipments)
    }
}
