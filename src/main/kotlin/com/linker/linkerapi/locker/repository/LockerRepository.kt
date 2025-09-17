package com.linker.linkerapi.locker.repository

import com.linker.linkerapi.locker.entity.Locker
import org.springframework.data.jpa.repository.JpaRepository

interface LockerRepository : JpaRepository<Locker, Long> {
    fun findByStudentId(studentId: Long): Locker?

    fun findAllByOrderByIdAsc(): List<Locker>
}