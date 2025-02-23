package com.linker.linkerapi.rental.controller

import com.linker.linkerapi.rental.dto.RentalCreateRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Rental Controller")
@RestController
class RentalController(
    private val rentalService: RentalService
) {
    @Operation(summary = "대여 신청", description = "기자재 대여 신청을 처리합니다.")
    @PostMapping("/rental")
    fun createRental(@RequestBody request: RentalCreateRequest): Rental {
        return rentalService.createRental(
            request.equipmentId,
            request.phoneNumber,
            request.name,
            request.studentId,
            request.rentalType
        )
    }
}