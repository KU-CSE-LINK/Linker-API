package com.linker.linkerapi.rental.entity

import com.linker.linkerapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class Rental(
    val equipmentId: Long,
    val phoneNumber: String,
    val name: String,
    val studentId: String,
    val rentalType: String,
    var status: String = "신청"
) : BaseEntity()
