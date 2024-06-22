package org.example.jooqtestcontainerflywaykotlinspring

import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.generated.tables.JMember
import org.jooq.generated.tables.daos.MemberDao
import org.jooq.generated.tables.pojos.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MemberRepository(
    private val dslContext: DSLContext,
    private val memberDao: MemberDao,
    private val MEMBER: JMember = JMember.MEMBER,
) {
    @Autowired
    constructor(
        dslContext: DSLContext,
        configuration: Configuration,
    ) :
        this(
            dslContext,
            MemberDao(configuration),
        )

    fun save(member: Member) = memberDao.insert(member)

    fun update(member: Member) = memberDao.update(member)

    fun findById(id: String) = memberDao.findById(id)

    fun findByAge(age: Int): Member? =
        dslContext.selectFrom(MEMBER)
            .where(MEMBER.AGE.eq(age))
            .fetchOneInto(Member::class.java)
}
