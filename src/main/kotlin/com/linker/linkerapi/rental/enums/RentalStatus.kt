package com.linker.linkerapi.rental.enums

enum class RentalStatus {
    REQUESTED,       // 대여 신청
    PREPARING,       // 대여 준비 완료
    RENTED,          // 대여중
    RETURNED         // 반납
}