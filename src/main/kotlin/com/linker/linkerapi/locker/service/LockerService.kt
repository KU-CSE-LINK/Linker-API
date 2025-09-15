package com.linker.linkerapi.locker.service

import com.linker.linkerapi.locker.dto.LockerResponse
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
        lockerRepository.findAll().map { LockerResponse.from(it) }

    fun getLockerByStudentId(studentId: Long): LockerResponse {
        val locker = lockerRepository.findByStudentId(studentId)
            ?: throw LockerNotFoundException()
        return LockerResponse.from(locker)
    }

    @Transactional
    fun rentLocker(lockerId: Long, studentId: Long): LockerResponse {
        val locker = validateRentalRequest(lockerId, studentId)

        locker.status = LockerStatus.RENTED
        locker.studentId = studentId
        return LockerResponse.from(lockerRepository.save(locker))
    }

    private fun validateRentalRequest(lockerId: Long, studentId: Long): Locker {
        lockerRepository.findByStudentId(studentId)?.let {
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
        lockerRepository.save(locker)
    }

    @Transactional
    fun updateLockerStatus(lockerId: Long, status: LockerStatus): LockerResponse {
        val locker = lockerRepository.findById(lockerId)
            .orElseThrow { LockerNotFoundException() }

        locker.status = status
        if (status == LockerStatus.AVAILABLE) {
            locker.studentId = null
        }
        return LockerResponse.from(lockerRepository.save(locker))
    }
}