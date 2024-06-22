package org.example.jooqtestcontainerflywaykotlinspring

import org.assertj.core.api.Assertions.assertThat
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
    @DisplayName("저장을 수행 후 조회")
    fun save() {
        val id = "hello"
        val member = Member(id, 23)

        memberRepository.save(member)

        val result: Collection<Member> = memberRepository.findByAge(23)
        assertThat(result).isNotEmpty()

        val resultOne: Member? = memberRepository.findById(id)

        assertThat(resultOne).hasNoNullFieldsOrProperties()
    }
}
