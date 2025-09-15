package com.linker.linkerapi.locker.controller

import com.linker.linkerapi.common.annotation.AdminUsername
import com.linker.linkerapi.locker.dto.LockerResponse
import com.linker.linkerapi.locker.dto.UpdateLockerStatusRequest
import com.linker.linkerapi.locker.service.LockerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Admin Locker Controller")
@RestController
@RequestMapping("/admin/lockers")
class AdminLockerController(private val lockerService: LockerService) {

    @Operation(summary = "사물함 상태 변경")
    @PatchMapping("/{lockerId}/status")
    fun updateLockerStatus(
        @AdminUsername username: String,
        @PathVariable lockerId: Long,
        @RequestBody request: UpdateLockerStatusRequest
    ): LockerResponse = lockerService.updateLockerStatus(lockerId, request.status)
}