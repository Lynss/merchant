package com.enci.merchant

import com.enci.merchant.config.CorsFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
class MerchantApplication
fun main(args: Array<String>) {
    SpringApplication.run(MerchantApplication::class.java, *args)
}
