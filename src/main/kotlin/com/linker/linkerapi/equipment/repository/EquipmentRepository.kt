package com.linker.linkerapi.equipment.repository

import com.linker.linkerapi.equipment.entity.Equipment
import org.springframework.data.jpa.repository.JpaRepository

interface EquipmentRepository: JpaRepository<Equipment, Long>