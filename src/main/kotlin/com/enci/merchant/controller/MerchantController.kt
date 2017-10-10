package com.enci.merchant.controller

import com.enci.merchant.domain.constant.MerchantResultEnum
import com.enci.merchant.domain.pojo.MerchantResult
import com.enci.merchant.domain.pojo.RequestPayMessageDTO
import com.enci.merchant.domain.pojo.TradeRequest
import com.enci.merchant.service.MerchantService
import com.enci.merchant.utils.IDUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class MerchantController(val merchantService: MerchantService) {
    val objectMapper = jacksonObjectMapper()
    @GetMapping("/orderMessage")
    fun orderMessage(response: HttpServletResponse): MerchantResult<TradeRequest> {
        val testDemoString = """
            {
                "merId": "MER001",
                "outTradeNo": "258228420608",
                "remark": "该订单为测试订单",
                "orderType": 1,
                "userId":"321",
                "discountAmount": 0,
                "originalAmount": 1,
                "tradeAmount": 1,
                "notifyUrl": "http://localhost:8080/callback",
                "orderName": "车险订单",
                "payeeCusOpenid":"73b24f53ffc64486eb40d606456fb04d",
                "payerCusOpenid":"231",
                "limitPay": "no_credit"
            } """.trimIndent()
        val tradeRequest = objectMapper.readValue<TradeRequest>(testDemoString)
        tradeRequest.outTradeNo = IDUtil.getId()
        return MerchantResult(MerchantResultEnum.MERCHANT_SUCCESS, tradeRequest)

    }

    @PostMapping("/url")
    fun platformUrl(@RequestBody testRequest: TradeRequest, response: HttpServletResponse): MerchantResult<RequestPayMessageDTO> {
        //测试请求参数
        val testRequestData = objectMapper.writeValueAsString(testRequest)
        return merchantService.postForPlatformData(testRequestData, response)
    }

    @PostMapping("/callback")
    fun callback(request: HttpServletRequest, response: HttpServletResponse) {
        val requestData: String = request.getParameter("code")
        val writer = response.writer
        writer.print("notify_success")
        if (MerchantResultEnum.EASY_PAY_SUCCESS.code != requestData.toInt()) {
            println(request.getParameter("message"))
        } else {
            println("paySuccess")
        }
        println("message get success")
    }

}
