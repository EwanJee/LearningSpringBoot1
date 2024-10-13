package org.spring.querydsl.dto

import com.querydsl.core.annotations.QueryProjection

data class MemberDto
    @QueryProjection
    constructor(
        var id: Long,
        var username: String,
    )
