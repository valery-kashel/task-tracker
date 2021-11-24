package com.vkashel.tasktracker

import com.fasterxml.jackson.databind.ObjectMapper
import com.vkashel.tasktracker.repository.datajpa.jparepositories.DataJpaUserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.properties"])
@AutoConfigureMockMvc
class AbstractTest {
    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    protected val mapper = ObjectMapper()

    @AfterEach
    private fun clearDb() {
        dataJpaUserRepository.deleteAll()
    }
}
