package com.sally.tmbdreader.api

import java.io.IOException

class TMBDError(val code: Int, message: String): IOException(message) {
}