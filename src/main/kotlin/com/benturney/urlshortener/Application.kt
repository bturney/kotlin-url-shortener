package com.benturney.urlshortener

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinUrlShortenerApplication

fun main(args: Array<String>) {
    runApplication<KotlinUrlShortenerApplication>(*args)
}

fun <T> loggerFor(clazz: Class<T>) = LoggerFactory.getLogger(clazz)!!