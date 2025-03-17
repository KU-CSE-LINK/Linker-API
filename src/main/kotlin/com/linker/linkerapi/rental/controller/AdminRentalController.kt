package com.linker.linkerapi.rental.controller

import com.linker.linkerapi.common.annotation.AdminUsername
import com.linker.linkerapi.rental.dto.ChangeRentalStatusRequest
import com.linker.linkerapi.rental.entity.Rental
import com.linker.linkerapi.rental.service.RentalService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
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
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): Page<Rental> {
        return rentalService.getRentals(page, size)
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