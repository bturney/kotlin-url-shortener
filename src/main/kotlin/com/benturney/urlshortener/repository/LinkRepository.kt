package com.benturney.urlshortener.repository

import com.benturney.urlshortener.model.Link
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepository : CrudRepository<Link, Long> {
    fun findByHash(hash: String): Link?
}