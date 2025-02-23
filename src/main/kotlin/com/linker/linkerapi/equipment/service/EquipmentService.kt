package com.linker.linkerapi.equipment.service

import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.equipment.repository.EquipmentRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository
) {
    fun getEquipments(): List<Equipment> {
        return equipmentRepository.findAll()
    }

    fun getEquipmentById(equipmentId: Long): Equipment {
        return equipmentRepository.findById(equipmentId)
            .orElseThrow { RuntimeException("Equipment not found with id: $equipmentId") }
    }
}