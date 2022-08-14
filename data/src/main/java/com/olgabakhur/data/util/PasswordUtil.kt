package com.olgabakhur.data.util

import com.olgabakhur.data.util.Constants.EMPTY
import java.security.MessageDigest
import java.util.*

class PasswordUtil {
    companion object {
        private const val PASS_HASH_ALGORITHM = "SHA-256"
        private const val PASS_HASH_PREFIX = "(=m1,<q6agUNghKFi67q>,aJ"
        private const val PASS_HASH_SUFFIX = "nnjVrFLgiFbBZEIIzwOJ09oj23qhM7I1"

        fun getPasswordHash(password: String): String {
            val stringToHash = PASS_HASH_PREFIX + password + PASS_HASH_SUFFIX
            try {
                val digest: MessageDigest = MessageDigest.getInstance(PASS_HASH_ALGORITHM)
                val hash: ByteArray = digest.digest(stringToHash.toByteArray())
                val hex: String = byteArrayToHex(hash)
                return hex.lowercase(Locale.getDefault())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return EMPTY
        }

        private fun byteArrayToHex(a: ByteArray): String {
            val sb = StringBuilder(a.size * 2)
            for (b in a) sb.append(String.format("%02x", b))
            return sb.toString()
        }
    }
}