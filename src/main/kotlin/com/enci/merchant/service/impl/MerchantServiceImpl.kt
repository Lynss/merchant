package com.enci.merchant.service.impl

import com.enci.merchant.model.BusinessException
import com.enci.merchant.model.constant.MerchantResultEnum
import com.enci.merchant.model.pojo.HttpConfig
import com.enci.merchant.model.pojo.MerchantResult
import com.enci.merchant.model.pojo.RequestPayMessageDTO
import com.enci.merchant.service.MerchantService
import com.enci.signature.Base64
import com.enci.signature.Parameters
import com.enci.signature.Secrets
import com.enci.signature.api.RSAUtils
import com.enci.signature.client.ClientSignatureFilter
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sun.jersey.api.client.Client
import com.sun.jersey.api.client.filter.LoggingFilter
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse
import javax.ws.rs.core.MediaType

@Service
class MerchantServiceImpl : MerchantService {
    companion object {
        val APP_ID = "dpf43f3p2l4k3l03"
        val SIGNATURE_METHOD = "RSA-SHA1"
        val VERSION = "1.0"
        val PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3ZfSRieTqOKtxNxVydZgRf9yXqlovZwu2diD8d8CT57n4OiraNxAaJ2ENaFbRHfid9oiBhiwZyEja1Sat1FZeaH1I8QlZL5sU9vbNuQC03bjrXlb8UI4enVhiiXB1ieY6rFLXXMpDP90htLKyIvH+RkNgzJvQXVhzzoFm12gOBAgMBAAECgYBD14DgjW47C3VCYC6OApwhDznC0xNHIh2UUJuJPQ3EZqLpDMjzcvSoNsB7GhGv/PYsdOOkdSfyaj6HwtzZ0yWm5QZIQv66nQpxL1Ss3fQeqt5px/kFjPOnYOKYg4A9Cn9ffSMKo7kMwh1KXOVrZ+NLHdPp/8QAhcGyE5eHZmKFzQJBAPh+WHSOYiLfxd0WrQpNvMA8Hmio8BzLnVRJKG7TZwdD47FdY6tjCGg7zXVviISBaJdzVSf6wYAg9tkPwtGykVsCQQCinix+AyM7g11WjBD0be4kwHqWlKJ8kCApIkitrbwMVaalBfLsmhimUPv6uyEdTNAFVJTjOAvuAuYy6GUauRlTAkEA4/7w5Aib7EmK/v7GSBTpYSwH7plKrfD4apQxP/ZBqr3UlTENuPvFg/WS3vQ1uvYNZCBS+rqtfgVA2AoJA2QmzwJANj+8KgGT9FubfK7XTSOLKXmIq8lD93gBMpe8VSw7KoY8RJsacjHp/TnRBdD9eA/S6aRQ0wg0ep8++kaqy+Jp/wJAdKBASp/9fgp0iJiVxG/IEx/2fJDDATsEqqAeA/F82HutSJQglFnqh5/rGZs9Eq7ovzlSa7qOLzRv6KTtt0U95g=="
    }

    //获取加密方式，根据配置或者数据库信息获取,默认支持rsa
    override fun postForPlatformData(testRequestData: String,response: HttpServletResponse): MerchantResult<RequestPayMessageDTO> {
        val getUrl = "http://localhost:80/api/pay/gateway"
        val queryBody = Base64.encode(RSAUtils.encryptByPrivateKey(testRequestData.toByteArray(), PRIVATE_KEY))
        val getUrlConfig = HttpConfig("post", SIGNATURE_METHOD, PRIVATE_KEY, getUrl, APP_ID, VERSION,
                MediaType.APPLICATION_JSON_TYPE, null, queryBody)
        //获取数据后对数据进行解析，并且组装成需要的值
        val merchantResult = jacksonObjectMapper().readValue<MerchantResult<RequestPayMessageDTO>>(queryHttpByConfigs(getUrlConfig))
        if (merchantResult.code!=MerchantResultEnum.EASY_PAY_SUCCESS.code){
            throw BusinessException(MerchantResultEnum.EASY_PAY_FAIL_GETURL)
        }
        merchantResult.code = MerchantResultEnum.MERCHANT_SUCCESS.code
        merchantResult.message = MerchantResultEnum.MERCHANT_SUCCESS.message
//        demo默认请求地址
//        val queryUrl = merchantResult.data?.tradeUrl?:throw BusinessException(MerchantResultEnum.EASY_PAY_FAIL_GETURL)
//        val queryUrlConfig = HttpConfig("get", SIGNATURE_METHOD, PRIVATE_KEY, queryUrl, APP_ID, VERSION,
//                MediaType.APPLICATION_JSON_TYPE, null, null)
//        val queryResult = queryHttpByConfigs(queryUrlConfig)
//        response.characterEncoding = "UTF-8"
//        val writer = response.writer
//        writer.print(queryResult)
//        其实不必要返回，看具体使用哪种请求方式吧
        return merchantResult
    }

    fun queryHttpByConfigs(httpConfig: HttpConfig): String {
        val apacheClient = HttpClientBuilder.create().build()
        val client = Client(ApacheHttpClient4Handler(apacheClient, BasicCookieStore(), true))
        client.addFilter(LoggingFilter())
        val providers = client.providers
        val params = Parameters().appId(httpConfig.appId).version(httpConfig.version).signatureMethod(httpConfig.encryptType)
        val secrets = Secrets().appSecret(httpConfig.privateKey)
        val filter = ClientSignatureFilter(providers, params, secrets)
        val resource = client.resource(httpConfig.baseUrl)
        resource.addFilter(filter)
        val beforeSent =if(httpConfig.getQueryParams!=null){
             resource.queryParams(httpConfig.getQueryParams).type(MediaType.APPLICATION_JSON_TYPE)
        }else{
            resource.type(MediaType.APPLICATION_JSON_TYPE)
        }
        return when (httpConfig.httpMethod) {
            "get" -> beforeSent.get(String::class.java)
            "post" -> beforeSent.post(String::class.java, httpConfig.postQueryBody)
            else -> throw BusinessException(MerchantResultEnum.FAIL_UNSUPPORT_HTTP_METHOD)
        }
    }
}