package com.enci.merchant

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class MerchantApplication
fun main(args: Array<String>) {
    SpringApplication.run(MerchantApplication::class.java, *args)
}
