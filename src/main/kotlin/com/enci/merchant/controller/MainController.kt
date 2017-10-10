package com.enci.merchant.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class MainController {
    @GetMapping("/")
    fun index( model: Model):String{
        model.addAttribute("name","ly")
        return "index"
    }
}