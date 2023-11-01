package jpabook.jpashop.service;

import io.micrometer.common.util.internal.logging.InternalLogLevel;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

//JUnit4
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //롤백을 위해
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test(expected = IllegalStateException.class)
    //@Rollback(false) //insert문을 볼 수 있음, Transactional은 rollback을 하니까
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("cho");
        //when
        Long saveId =  memberService.join(member);
        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("cho");

        Member member2 = new Member();
        member2.setName("cho");
        //when
        memberService.join(member1);
/*        try{ //exception을 잡아주어야 함
            memberService.join(member2);
        } catch (IllegalStateException e){ //여기서 exception을 잡아 줌
            return;
        }*/
        memberService.join(member2); //예외가 발생해야 한다
        //then
        fail("예외가 발생해야 한다.");

    }
}