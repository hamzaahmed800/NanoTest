package com.nano.test.data

import com.google.gson.annotations.SerializedName

object RequestBodies {

    data class LoginBody(
        val username:String,
        val password:String
    )

    data class DashboardBody(
        val BranchCode:String,
        val FromDate:String,
        val ToDate:String,
        val Consolidate: String
    )

    data class BestSellingCategoryBody(
        val BranchCode:String,
        val FromDate:String,
        val ToDate:String
    )


}