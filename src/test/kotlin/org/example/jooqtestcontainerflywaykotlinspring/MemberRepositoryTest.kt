package org.example.jooqtestcontainerflywaykotlinspring

import org.jooq.generated.tables.pojos.Member
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class MemberRepositoryTest {
    @Autowired
    private lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("저장을 수행한다")
    fun save() {
        val member = Member("hello", 23)

        memberRepository.save(member)
    }
}
