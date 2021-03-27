package com.yannbriancon.interceptor;


import com.yannbriancon.SpringJpaConfig;
import com.yannbriancon.utils.entity.Avatar;
import com.yannbriancon.utils.repository.AvatarRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringJpaConfig.class,loader= AnnotationConfigContextLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    SqlScriptsTestExecutionListener.class,
    TransactionalTestExecutionListener.class})

public class BaseTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private AvatarRepository avatarRepository;

  @Test
  @Transactional
  public void test() {
    System.out.println("test");
    if(entityManager==null) {
      System.out.println("null");
    }
    List<Avatar> avartarList=avatarRepository.findAll();
    System.out.println(avartarList.size());
  }
}
