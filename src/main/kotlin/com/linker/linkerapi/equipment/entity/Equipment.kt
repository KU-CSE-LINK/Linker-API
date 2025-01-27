package com.linker.linkerapi.equipment.entity

import com.linker.linkerapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class Equipment(
    var name: String,
    var totalStock: Int,
    var availableStock: Int,
):BaseEntity()