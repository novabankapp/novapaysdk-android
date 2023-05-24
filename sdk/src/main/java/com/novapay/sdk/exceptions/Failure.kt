package com.novapay.sdk.exceptions

import org.json.JSONObject

private const val UNDEFINED_MESSAGE = "error.transport.undefined"

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(open val error: String = "") {

    fun errorMessage(): String = error

    object RateLimitFailure : Failure("error_transport_rate_limit")

    object NetworkConnection : Failure("no_network_description")

    object MaintenanceMode : Failure("maintenance_description")

    object DeprecatedSDK : Failure()
    object UserSessionExpired : Failure("session_expired_error")

    open class ServerError(
        val code: Int?,
        val apiMessage: String? = null,
        override val error: String = UNDEFINED_MESSAGE
    ) : Failure() {

        fun hasUndefinedKey() = error == UNDEFINED_MESSAGE

        fun toJSonObject(): JSONObject {
            val rawCode = if (error == UNDEFINED_MESSAGE) "" else (code?.toString() ?: "")

            val json = JSONObject()
            json.put("code", code)
            json.put("message", error)
            json.put("raw_code", rawCode)

            addErrorTracking255CharactersRestriction(json, apiMessage)
            return json
        }

        private fun addErrorTracking255CharactersRestriction(json: JSONObject, message: String?) {
            if (!message.isNullOrEmpty()) {
                message
                    .chunked(255)
                    .take(7)
                    .forEachIndexed { index, chunk -> json.put("reason$index", chunk) }
            }
        }
    }

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(override val error: String = "", val titleKey: String = "") : Failure()
}

internal class NoEnoughParametersFailure : Failure.FeatureFailure()
internal class TooMuchParametersFailure : Failure.FeatureFailure()
