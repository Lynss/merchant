package com.enci.merchant.model

import com.enci.merchant.model.pojo.BaseReturn

class BusinessException(val code: Int, override val message: String):Exception(){
    constructor(baseReturn: BaseReturn) : this(baseReturn.code, baseReturn.message)
}
