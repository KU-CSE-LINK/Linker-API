package com.linker.linkerapi.locker.controller

import com.linker.linkerapi.locker.dto.LockerResponse
import com.linker.linkerapi.locker.dto.RentLockerRequest
import com.linker.linkerapi.locker.service.LockerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Locker Controller")
@RestController
@RequestMapping("/lockers")
class LockerController(private val lockerService: LockerService) {

    @Operation(summary = "모든 사물함 조회")
    @GetMapping
    fun getAllLockers(): List<LockerResponse> = lockerService.getAllLockers()

    @Operation(summary = "학번으로 내 사물함 조회")
    @GetMapping("/my")
    fun getMyLocker(@RequestParam studentId: Long): LockerResponse =
        lockerService.getLockerByStudentId(studentId)

    @Operation(summary = "사물함 대여")
    @PostMapping("/{lockerId}/rent")
    @ResponseStatus(HttpStatus.CREATED)
    fun rentLocker(
        @PathVariable lockerId: Long,
        @RequestBody request: RentLockerRequest
    ): LockerResponse = lockerService.rentLocker(lockerId, request)

    @Operation(summary = "사물함 반납")
    @PostMapping("/return")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun returnLocker(@RequestParam studentId: Long) {
        lockerService.returnLocker(studentId)
    }
}