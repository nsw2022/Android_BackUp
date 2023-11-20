package com.nsw2022.tpqucikplacebykakaosearchapi.model

data class NidUserInfoResponse(
    var resultcode:String,
    var message:String,
    var response:NidUser
)

data class NidUser(var id:String, var email:String)
