package com.enci.merchant.service.impl

import com.enci.merchant.domain.BusinessException
import com.enci.merchant.domain.constant.MerchantResultEnum
import com.enci.merchant.domain.pojo.HttpConfig
import com.enci.merchant.domain.pojo.MerchantResult
import com.enci.merchant.domain.pojo.RequestPayMessageDTO
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
        val PRIVATE_KEY ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJNgACdcxwVVL6DACVanU71J6DbJFse+0kUTwvxvCrwUfQr368Sx6rCBzH/6NAqiItFGDS6KtbjYDoMCRRMgiOrTshIjUpdmZQN6obeVaLwuJxCc7erzZV9ZZunEel4JT/y0sjI2oGCBLl3L3pBu5c3VtonZVJ70xyhmciaTodkrAgMBAAECgYBnekChYsNbOzT16eCkt1hU0E/8J7WXCqUZW4bNOCqRZNFnoiwpL8NZq8mrEkL4NF++ETGwPDTcVNfbpPxbMbzwFGV2mfIsVKtIoPfS67i5FHXnhNEb2oURMVR4k4Y70jSd5HlpzIImN/HAlriEmMKOyx5hwFKwBWUbs43zoVK0AQJBAMWkgnrehmu8N2zOpBeezJfPlOGH8A28zEPWykGAZCqTNNF6pPZkSoU1WAnbW1oPxEjyutpJXEjBFwfMPpHQU5kCQQC+49eVWxufYRFqZR/BX6DTrVBSwro1liw9OyZo/c6m7JR6DXVc1IyU8N/4K6UkGz88wiJ/SUzdCoMrJSd6IM1jAkAYqaimkHIRq5D3AOo1EFnTb9HSOtZXwIF0za67cbwOHARxR26iWG18JeXwhPDnUiRaPf/XEWR0p7OqA3CjXW2xAkA6eng31CJhMA5yxqn0xoPxdP3PbMI42lmRJIa+0Uo2jvFpdqgGmUK7+hLS5yP/LK2xwlNpJR579NV8KTSv0E67AkEAtdODq4h23DZT4y0XOwKimd/Rk2e0/7tkd/LxBiSxV+wszPk+qqF44kmrlwbqy+rHlgttzT6YZQ7typ9EMjgthg=="
    }

    //获取加密方式，根据配置或者数据库信息获取,默认支持rsa
    override fun postForPlatformData(testRequestData: String,response: HttpServletResponse): MerchantResult<RequestPayMessageDTO> {
        val getUrl = "http://sitpay.e-nci.com/api/pay/gateway"
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