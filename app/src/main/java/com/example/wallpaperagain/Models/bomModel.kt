package com.example.wallpaperagain.Models

import java.util.Date


data class bomModel(
        val link: String?,
        var category: String? = null,
        var timestamp: Date? = null
)
{
        constructor() : this(null, null, null)
}


