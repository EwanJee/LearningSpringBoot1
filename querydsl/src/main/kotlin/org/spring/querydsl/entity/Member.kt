@file:Suppress("ktlint:standard:no-wildcard-imports")

package org.spring.querydsl.entity

import jakarta.persistence.*

@Entity
class Member(
    username: String,
    team: Team? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var username: String = username
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    var team: Team? = null
        private set

    fun changeTeam(team: Team) {
        this.team = team
        team.members.add(this)
    }
}
