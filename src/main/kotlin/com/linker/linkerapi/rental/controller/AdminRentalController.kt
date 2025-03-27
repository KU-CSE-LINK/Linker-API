package com.linker.linkerapi.rental.controller

import com.linker.linkerapi.common.annotation.AdminUsername
import com.linker.linkerapi.rental.dto.ChangeRentalStatusRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Admin Rental Controller")
@RestController
@RequestMapping("/admin")
class AdminRentalController(
    private val rentalService: RentalService
) {
    @Operation(summary = "대여 조회", description = "대여 신청 내역을 조회합니다.")
    @GetMapping("/rentals")
    fun getRentals(
        @AdminUsername username: String,
    ): List<Rental> {
        return rentalService.getAllRentals()
    }

    @Operation(summary = "대여 단건 조회", description = "대여 신청 내역을 id를 기준으로 조회합니다.")
    @GetMapping("/rental")
    fun getRentalById(
        @AdminUsername username: String,
        @RequestParam id: Long
    ): Rental {
        return rentalService.getRentalById(id)
    }

    @Operation(summary = "대여 상태 변경", description = "대여 신청 내역의 상태를 변경합니다.")
    @PostMapping("/rental")
    fun changeRentalStatus(
        @AdminUsername username: String,
        @RequestBody body: ChangeRentalStatusRequest
    ): Rental {
        return rentalService.changeRentalStatus(body.id, body.status)
    }
}