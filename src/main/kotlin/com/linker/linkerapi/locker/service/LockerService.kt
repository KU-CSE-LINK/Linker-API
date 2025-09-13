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
        lockerRepository.findByStudentId(studentId)?.let {
            throw LockerAlreadyRentedException()
        }

        val locker = lockerRepository.findById(lockerId)
            .orElseThrow { LockerNotFoundException() }

        if (locker.status != LockerStatus.AVAILABLE) {
            throw LockerAlreadyRentedException()
        }

        locker.status = LockerStatus.RENTED
        locker.studentId = studentId
        return LockerResponse.from(lockerRepository.save(locker))
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

    // 초기 사물함 데이터 생성
    fun initializeLockerData() {
        if (lockerRepository.count() > 0) return

        val lockers = mutableListOf<Locker>()

        // A동 301호 앞(과방)
        for (i in 10..54) {
            lockers.add(Locker("3a$i", "A동 301호 앞(과방)"))
        }

        // A동 3층 계단 앞
        for (i in 55..63) {
            lockers.add(Locker("3a$i", "A동 3층 계단 앞"))
        }

        // A동 2층 217호 앞(서점쪽 계단)
        for (i in 28..36) {
            lockers.add(Locker("2c$i", "A동 2층 217호 앞(서점쪽 계단)"))
        }

        // B동 3층 중앙계단
        for (i in 1..18) {
            lockers.add(Locker("3b${String.format("%02d", i)}", "B동 3층 중앙계단"))
        }

        // B동 안뜰로 가는 통로
        for (i in 1..57) {
            lockers.add(Locker("1a${String.format("%02d", i)}", "B동 안뜰로 가는 통로"))
        }

        // C동 3층 경사로(352-2호 앞)
        for (i in 1..36) {
            lockers.add(Locker("3c${String.format("%02d", i)}", "C동 3층 경사로(352-2호 앞)"))
        }

        // C동 3층 중앙 계단
        for (i in 37..54) {
            lockers.add(Locker("3c${String.format("%02d", i)}", "C동 3층 중앙 계단"))
        }

        // C동 끝 3~4층 계단
        for (i in 55..117) {
            lockers.add(Locker("3c${String.format("%02d", i)}", "C동 끝 3~4층 계단"))
        }

        // A,C동 넘어가는 계단 앞
        for (i in 1..27) {
            lockers.add(Locker("2c${String.format("%02d", i)}", "A,C동 넘어가는 계단 앞"))
        }

        lockerRepository.saveAll(lockers)
    }
}