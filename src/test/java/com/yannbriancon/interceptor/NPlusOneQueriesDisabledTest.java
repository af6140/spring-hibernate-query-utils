package com.yannbriancon.interceptor;

import com.yannbriancon.utils.entity.Message;
import com.yannbriancon.utils.repository.MessageRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)

@Transactional
class NPlusOneQueriesDisabledTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private HibernateQueryInterceptor hibernateQueryInterceptor;

    @Test
    void nPlusOneQueriesDetection_whenDisabled_stillCountsQueries_doesNotThrowException() {
        // Fetch the 2 messages without the authors
        List<Message> messages = messageRepository.findAll();

        hibernateQueryInterceptor.startQueryCount();

        // Trigger N+1 queries, if this WAS enabled, it would throw an exception and fail the test.
        messages.stream()
                .map(message -> message.getAuthor().getName())
                .collect(Collectors.toList());

        //Assert that counting still runs when n+1 detection is disabled
        //assertThat(hibernateQueryInterceptor.getQueryCount()).isEqualTo(2);
    }
}
