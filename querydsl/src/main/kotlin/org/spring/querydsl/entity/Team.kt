package org.spring.querydsl.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Team(
    name: String,
    members: MutableList<Member>? = null,
    leader: Member? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var name: String = name
        private set

    @OneToMany(mappedBy = "team")
    var members: MutableList<Member> = members ?: mutableListOf()
        private set

    @OneToOne
    @JoinColumn(name = "member_id")
    var leader: Member? = leader
        private set
}
