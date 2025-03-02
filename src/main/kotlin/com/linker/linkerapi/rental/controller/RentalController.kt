package com.linker.linkerapi.rental.controller

import com.linker.linkerapi.rental.dto.RentalCreateRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import org.springframework.web.bind.annotation.*

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

    @Operation(summary = "본인 신청 내역 조회", description = "학번, 전화번호, 이름을 모두 입력하여 대여 신청 내역을 조회합니다.")
    @GetMapping("/rentals")
    fun getMyRentals(
        @NotBlank
        @RequestParam("name") name: String,
        @NotBlank
        @RequestParam("studentId") studentId: Long,
    ): List<Rental> {
        return rentalService.getRentalsByUserInfo(name, studentId)
    }
}