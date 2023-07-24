package com.nano.test.util

import android.os.Environment
import com.nano.test.util.AppConstants.Config.Companion.API_VERSION
import com.nano.test.util.AppConstants.Config.Companion.ENVIRONMENT
import com.nano.test.util.AppConstants.Config.Companion.HTTPS

interface AppConstants {

    interface Url {

        companion object {

            const val BASE_URL = "$HTTPS$APP_BASE_URL"
            const val LOGIN = "/auth/login"
            const val GET_ALL_PRODUCTS = "/products"
            const val GET_SINGLE_PRODUCT = "/products/1"
        }
    }

    interface StatusCodes {

        companion object {

            const val UNAUTHORIZED = "401"

        }
    }

    interface Config {
        companion object {
            const val HTTPS = "https://"
            const val ENVIRONMENT = ""
            const val API_VERSION = ""
        }
    }

    companion object {

        var JWT_TOKEN: String = ""
        const val APP_BASE_URL = "fakestoreapi.com"

    }

}