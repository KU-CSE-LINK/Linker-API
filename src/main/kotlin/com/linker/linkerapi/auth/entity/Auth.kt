package com.linker.linkerapi.auth.entity

import com.linker.linkerapi.common.entity.BaseEntity
import jakarta.persistence.Entity

@Entity
class Auth(
    val code: String,
) : BaseEntity()