package org.jee.framework.core.rpc.dubbo.service;

import java.util.ArrayList;  
import java.util.List;  
  
  
public class DemoServiceImpl implements DemoService{  
      
     public String sayHello(String name) {  
            return "Hello " + name;  
     }  
     public List<String> getUsers() {  
         List<String> list = new ArrayList<String>();  
         String s1 = "t1";
         String s2 = "t1";
         String s3 = "t1";
           
         list.add(s1);  
         list.add(s2);  
         list.add(s3);  
         return list;  
     }  
}  