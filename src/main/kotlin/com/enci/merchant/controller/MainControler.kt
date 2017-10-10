package com.enci.merchant.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainControler {
    @GetMapping("/")
    fun index() = "index"
}