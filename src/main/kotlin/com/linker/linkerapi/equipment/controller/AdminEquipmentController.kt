package com.linker.linkerapi.equipment.controller

import com.linker.linkerapi.common.annotation.AdminUsername
import com.linker.linkerapi.equipment.dto.AddEquipmentRequestBody
import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.equipment.service.EquipmentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Admin Equipment Controller")
@RestController
@RequestMapping("/admin")
class AdminEquipmentController(
    private val equipmentService: EquipmentService
) {
    @Operation(summary = "기자재 추가", description = "기자재를 추가합니다.")
    @PostMapping("/equipment")
    fun addEquipment(
        @AdminUsername username: String,
        @RequestBody body: AddEquipmentRequestBody
    ): Equipment {
        return equipmentService.addEquipment(
            body.name,
            body.imageUrl,
            body.totalStock,
            body.availableStock
        )
    }
}
