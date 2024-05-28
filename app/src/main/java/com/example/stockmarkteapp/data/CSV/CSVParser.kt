package com.example.stockmarkteapp.data.CSV

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parse(stream: InputStream): List<T>
}