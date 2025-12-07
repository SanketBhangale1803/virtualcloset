package com.example.virtualcloset.utils

import android.content.Context
import android.net.Uri

object ImageUtils {

    /**
     * Takes persistable URI permission so images remain accessible after app restart
     */
    fun takePersistableUriPermission(context: Context, uri: Uri): Boolean {
        return try {
            val contentResolver = context.contentResolver
            val takeFlags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            contentResolver.takePersistableUriPermission(uri, takeFlags)
            true
        } catch (e: SecurityException) {
            // Some URIs don't support persistent permissions
            e.printStackTrace()
            false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Check if we have permission to access this URI
     */
    fun hasUriPermission(context: Context, uri: Uri): Boolean {
        return try {
            val persistedUris = context.contentResolver.persistedUriPermissions
            persistedUris.any { it.uri == uri }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Validate that the URI is accessible
     */
    fun isUriAccessible(context: Context, uriString: String): Boolean {
        return try {
            val uri = Uri.parse(uriString)
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}