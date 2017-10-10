package com.enci.merchant.domain

import com.enci.merchant.domain.pojo.BaseReturn

class BusinessException(val code: Int, override val message: String):Exception(){
    constructor(baseReturn: BaseReturn) : this(baseReturn.code, baseReturn.message)
}
