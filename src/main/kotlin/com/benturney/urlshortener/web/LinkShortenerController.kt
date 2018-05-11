package com.benturney.urlshortener.web

import com.benturney.urlshortener.loggerFor
import com.benturney.urlshortener.model.Link
import com.benturney.urlshortener.repository.LinkRepository
import com.google.common.hash.Hashing
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class LinkShortenerController(val linkRepository: LinkRepository) {
    private val log = loggerFor(javaClass)

    @GetMapping("/{hash}")
    fun redirect(@PathVariable hash: String, response: HttpServletResponse) {
        val url: Link? = linkRepository.findByHash(hash)

        if (url != null) {
            response.sendRedirect(url.fullUrl).also { log.info("Redirecting to ${url.fullUrl}") }
        } else {
            log.warn("URL /$hash not found")
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Full url for $hash not found")
        }
    }

    @PostMapping
    fun shortenLink(req: HttpServletRequest): ResponseEntity<String> {

        val url = (req.requestURI + (req.queryString ?: "")).substring(1)
            .also { log.debug("Request received to shorten [$it]") }

        // todo Check if link is valid, if so return 400 Bad Request

        val hash = generateUrlHash(url).also { log.debug("generated hash [$it]") }

        linkRepository.save(Link(hash, url)).also { log.debug("Saved $it record") }

        return ResponseEntity("http://localhost:8080/$hash", HttpStatus.OK)
    }

    // todo - This works, but how unique and/or easy to tamper is it?
    private fun generateUrlHash(url: String) = Hashing.murmur3_32().hashString(url, Charsets.UTF_8).toString()
}