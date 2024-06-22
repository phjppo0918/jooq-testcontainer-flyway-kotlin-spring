package org.example.jooqtestcontainerflywaykotlinspring

import org.jooq.generated.tables.pojos.Member
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/members")
class MemberController(
    private val memberRepository: MemberRepository,
) {
    @PostMapping
    fun save(
        @RequestBody member: Member,
    ) {
        memberRepository.save(member)
    }
}
