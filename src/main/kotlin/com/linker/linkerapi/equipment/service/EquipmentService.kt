package com.linker.linkerapi.equipment.service

import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.equipment.repository.EquipmentRepository
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    private val equipmentRepository: EquipmentRepository) {
        fun getAvailableEquipment(): List<Equipment> {
            return equipmentRepository.findAll().filter { it.availableStock>0 }
        }
}
