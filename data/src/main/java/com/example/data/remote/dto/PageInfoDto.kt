package com.example.data.remote.dto

data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)