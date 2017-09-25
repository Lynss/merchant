package com.enci.merchant.model.pojo

import com.sun.jersey.core.util.MultivaluedMapImpl
import java.io.Serializable
import javax.ws.rs.core.MediaType

data class RequestPayMessageDTO(var mchId: String, var tradeUrl: String) : Serializable {
    companion object {
        private const val serialVersionUID = -4873854581339351175L
    }
}


data class MerchantResult<T>(override var code: Int, override var message: String, var data: T?) : Serializable, BaseReturn {
    companion object {
        private const val serialVersionUID = -1L
    }

    constructor(baseReturn: BaseReturn, data: T?) :
            this(baseReturn.code, baseReturn.message, data)

    constructor(baseReturn: BaseReturn) :
            this(baseReturn.code, baseReturn.message, null)
}

data class HttpConfig(val httpMethod: String,
                      val encryptType:String,
                      val privateKey:String,
                      val baseUrl:String,
                      val appId: String,
                      val version: String,
                      val mediaType: MediaType,
                      val getQueryParams: MultivaluedMapImpl?,
                      val postQueryBody:String?)

interface BaseReturn {
    val code: Int
    val message: String
}