package com.benturney.urlshortener.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Link(
    @Id @Column(unique = true, nullable = false) var hash: String = "",
    @Column(nullable = false) var fullUrl: String = ""
)