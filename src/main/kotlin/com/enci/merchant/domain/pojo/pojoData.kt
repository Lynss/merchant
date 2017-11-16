package com.enci.merchant.domain.pojo

import com.sun.jersey.core.util.MultivaluedMapImpl
import java.io.Serializable
import javax.ws.rs.core.MediaType

data class RequestPayMessageDTO(var mchId: String, var tradeUrl: String) : Serializable {
    companion object {
        private const val serialVersionUID = -4873854581339351175L
    }
}


data class MerchantResult<T>(override var code: Int, override var message: String, var data: T?=null) : Serializable, BaseReturn {
    companion object {
        private const val serialVersionUID = -1L
    }

    constructor(baseReturn: BaseReturn, data: T?=null) :
            this(baseReturn.code, baseReturn.message, data)
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

class TradeRequest : Serializable {
    var merId: String? = null
    var userId: String? = null
    var outTradeNo: String? = null
    var payeeCusOpenid: String? = null
    var payerCusOpenid: String? = null
    var payeeCusName: String? = null
    var payerCusName: String? = null
    var orderName: String? = null
    var orderType: Char = ' '
    var originalAmount: Int? = null
    var discountAmount: Int? = null
    var tradeAmount: Int? = null
    var remark: String? = null
    var notifyUrl: String? = null
    var limitPay: String? = null
    var pmtTag: String? = null
    var sn: String? = null

    companion object {
        private const val serialVersionUID = 2203067580496371853L
    }
}
