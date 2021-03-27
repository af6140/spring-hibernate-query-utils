package com.yannbriancon.interceptor;

import com.yannbriancon.utils.entity.User;
import com.yannbriancon.utils.repository.MessageRepository;
import com.yannbriancon.utils.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
class QueryCountTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private HibernateQueryInterceptor hibernateQueryInterceptor;

    @Test
    void queryCount_isOkWhenCallingRepository() {
        hibernateQueryInterceptor.startQueryCount();

        messageRepository.findAll();

       // assertThat(hibernateQueryInterceptor.getQueryCount()).isEqualTo(1);
    }

    @Test
    void queryCount_isOkWhenSaveQueryIsExecutedBeforeStartingTheCount() {
        userRepository.saveAndFlush(new User());

        hibernateQueryInterceptor.startQueryCount();

        messageRepository.findAll();

        //assertThat(hibernateQueryInterceptor.getQueryCount()).isEqualTo(1);
    }
}
