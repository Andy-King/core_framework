package org.jee.framework.core.rpc.dubbo.service;

import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@org.springframework.test.context.ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Provider {  
   
    public static void main(String[] args) throws Exception {  
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/applicationContext.xml"});  
        context.start();  
   
        System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟  
        
    }  
   
} 
