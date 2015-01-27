package org.jee.framework.core.rpc.dubbo.service;

import java.util.List;  

public interface DemoService {  
  
    String sayHello(String name);  
  
    public <T> List<T> getUsers();  
  
}  
