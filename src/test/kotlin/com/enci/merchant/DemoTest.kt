package com.enci.merchant

import com.enci.merchant.service.MerchantService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value


class DemoTest : BaseTest() {
    @Autowired
    lateinit var merchantServiceImpl: MerchantService

    data class A(val a: Int, val b: String)

    @Test
    fun testJackson() {
        val a = """[{"a":"1","b":"2"},{"a":"3","b":"4"}]"""
        val json = jacksonObjectMapper()
        val c = json.readValue<List<A>>(a)
        assert(c.size == 2)
    }

    @Test
    fun testProperties() {
        //先完成功能，这些以后再试试
//        assert(configProperties?.appId=="RSA")
    }

    @Value("\${config.test}")
    lateinit var test: String

    @Test
    fun testJacksonChar() {
        val a = { a: String, b: String -> a + b }
        val c = listOf("2", "3", "4"," 5")
        val d = c.reduceRight{ a: String, b: String -> a + b }
        println(d)
    }

}