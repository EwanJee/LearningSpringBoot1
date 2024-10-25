package org.spring.querydsl.dto

import com.querydsl.core.annotations.QueryProjection
import org.spring.querydsl.entity.Team

class MemberTeamDto
    @QueryProjection
    constructor(
        val memberId: Long,
        val username: String,
        val teamName: String,
        val team: Team,
    )
