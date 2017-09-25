package com.enci.merchant.model.constant

import com.enci.merchant.model.pojo.BaseReturn

enum class MerchantResultEnum(override val code:Int, override val message:String): BaseReturn {
    EASY_PAY_SUCCESS(1,"成功"),
    EASY_PAY_FAIL_GETURL(301,"获取支付平台地址失败"),
    MERCHANT_SUCCESS(200,"成功"),
    FAIL_ENCRYPT_TYPE_NOT_FOUNT(401,"没有合适的加密方式"),
    FAIL_UNSUPPORT_HTTP_METHOD(402,"无法进行对应的http请求");
}


