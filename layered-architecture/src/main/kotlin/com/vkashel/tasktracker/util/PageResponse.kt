package com.vkashel.tasktracker.util

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PageResponse<T> private constructor(
    var elements: List<T> = listOf(),
    var totalElements: Long = 0,
    var nextPage: Boolean = false,
    var currentPage: Int = 0,
    var currentSize: Int = 0
) {

    fun <R> map(transform: (T) -> R): PageResponse<R> {
        val elements1 = elements.map(transform)
        return PageResponse(
            elements = elements1,
            totalElements, nextPage, currentPage, currentSize
        )
    }

    companion object {
        fun <T> of(pageable: Pageable, page: Page<T>): PageResponse<T> {
            return PageResponse(
                elements = page.content,
                totalElements = page.totalElements,
                nextPage = page.hasNext(),
                currentPage = pageable.pageNumber,
                currentSize = page.content.size
            )
        }
    }
}
