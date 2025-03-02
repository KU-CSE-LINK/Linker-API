package com.linker.linkerapi.rental.service

import com.linker.linkerapi.equipment.service.EquipmentService
import com.linker.linkerapi.rental.dto.RentalQueryRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.enums.RentalType
import com.linker.linkerapi.rental.repository.RentalRepository
import org.springframework.stereotype.Service

@Service
class RentalService(
    private val rentalRepository: RentalRepository,
    private val equipmentService: EquipmentService
) {
    fun createRental(
        equipmentId: Long,
        phoneNumber: String,
        name: String,
        studentId: Long,
        rawRentalType: String
    ): Rental {
        val equipment = equipmentService.getEquipmentById(equipmentId)
        val rentalType = RentalType.valueOf(rawRentalType)
        val rental = Rental(
            equipment = equipment,
            phoneNumber = phoneNumber,
            name = name,
            studentId = studentId,
            rentalType = rentalType
        )
        return rentalRepository.save(rental)
    }

    fun getRentalsByUserInfo(request: RentalQueryRequest): List<Rental> {
        if (!request.isValid()) {
            throw IllegalArgumentException("모든 필드(학번, 전화번호, 이름)가 입력되어야 합니다")
        }

        return rentalRepository.findByStudentIdAndPhoneNumberAndName(
            request.studentId,
            request.phoneNumber,
            request.name
        )
    }
}