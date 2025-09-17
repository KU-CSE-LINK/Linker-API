package com.linker.linkerapi.locker.service

import com.linker.linkerapi.locker.dto.LockerResponse
import com.linker.linkerapi.locker.dto.RentLockerRequest
import com.linker.linkerapi.locker.entity.Locker
import com.linker.linkerapi.locker.enums.LockerStatus
import com.linker.linkerapi.locker.exception.LockerAlreadyRentedException
import com.linker.linkerapi.locker.exception.LockerNotFoundException
import com.linker.linkerapi.locker.repository.LockerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LockerService(private val lockerRepository: LockerRepository) {

    fun getAllLockers(): List<LockerResponse> =
        lockerRepository.findAllByOrderByIdAsc().map { LockerResponse.from(it) }

    fun getLockerByStudentId(studentId: Long): LockerResponse {
        val locker = lockerRepository.findByStudentId(studentId)
            ?: throw LockerNotFoundException()
        return LockerResponse.from(locker)
    }

    @Transactional
    fun rentLocker(lockerId: Long, request: RentLockerRequest): LockerResponse {
        val locker = validateLockerRentalPreconditions(lockerId, request.studentId)

        locker.status = LockerStatus.RENTED
        locker.studentId = request.studentId
        locker.userName = request.userName
        locker.phoneNumber = request.phoneNumber
        return LockerResponse.from(lockerRepository.save(locker))
    }

    private fun validateLockerRentalPreconditions(lockerId: Long, studentId: Long): Locker {
        if (lockerRepository.findByStudentId(studentId) != null) {
            throw LockerAlreadyRentedException()
        }

        val locker = lockerRepository.findById(lockerId)
            .orElseThrow { LockerNotFoundException() }

        if (locker.status != LockerStatus.AVAILABLE) {
            throw LockerAlreadyRentedException()
        }
        return locker
    }

    @Transactional
    fun returnLocker(studentId: Long) {
        val locker = lockerRepository.findByStudentId(studentId)
            ?: throw LockerNotFoundException()

        locker.status = LockerStatus.AVAILABLE
        locker.studentId = null
        locker.userName = null
        locker.phoneNumber = null
        lockerRepository.save(locker)
    }

    @Transactional
    fun updateLockerStatus(lockerId: Long, status: LockerStatus): LockerResponse {
        val locker = lockerRepository.findById(lockerId)
            .orElseThrow { LockerNotFoundException() }

        locker.status = status
        if (status != LockerStatus.RENTED) {
            locker.studentId = null
            locker.userName = null
            locker.phoneNumber = null
        }
        return LockerResponse.from(lockerRepository.save(locker))
    }
}
