package com.enci.merchant.service

import com.enci.merchant.model.pojo.MerchantResult
import com.enci.merchant.model.pojo.RequestPayMessageDTO
import javax.servlet.http.HttpServletResponse

interface MerchantService {
    fun  postForPlatformData(testRequestData: String,response:HttpServletResponse): MerchantResult<RequestPayMessageDTO>
}