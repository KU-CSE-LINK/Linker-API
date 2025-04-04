package com.linker.linkerapi.rental.entity

import com.linker.linkerapi.common.entity.BaseEntity
import com.linker.linkerapi.equipment.entity.Equipment
import com.linker.linkerapi.rental.enums.RentalStatus
import com.linker.linkerapi.rental.enums.RentalType
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
class Rental(
    @ManyToOne
    val equipment: Equipment,
    val phoneNumber: String,
    val name: String,
    val studentId: Long,
    val rentalType: RentalType,
    val returnDate: LocalDateTime,
    var status: RentalStatus = RentalStatus.REQUESTED
) : BaseEntity()