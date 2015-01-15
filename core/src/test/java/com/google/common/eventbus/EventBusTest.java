package com.google.common.eventbus;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
　　EventBus是Guava的事件处理机制，是设计模式中的观察者模式（生产/消费者编程模型）的优雅实现。
　　对于事件监听和发布订阅模式，EventBus是一个非常优雅和简单解决方案，我们不用创建复杂的类和接口层次结构。

　　Observer模式是比较常用的设计模式之一，虽然有时候在具体代码里，它不一定叫这个名字，
　　比如改头换面叫个Listener，但模式就是这个模式。手工实现一个Observer也不是多复杂的一件事，
　　只是因为这个设计模式实在太常用了，Java就把它放到了JDK里面：Observable和Observer，
　　从JDK 1.0里，它们就一直在那里。从某种程度上说，它简化了Observer模式的开发，
　　至少我们不用再手工维护自己的Observer列表了。不过，如前所述，JDK里的Observer从1.0就在那里了，
　　直到Java 7，它都没有什么改变，就连通知的参数还是Object类型。
　　要知道，Java 5就已经泛型了。Java 5是一次大规模的语法调整，许多程序库从那开始重新设计了API，
　　使其更简洁易用。当然，那些不做应对的程序库，多半也就过时了。这也就是这里要讨论知识更新的原因所在。
　　今天，对于普通的应用，如果要使用Observer模式该如何做呢？答案是Guava的EventBus。

    example:
    
    public class TestEvent {
        private final int message;
        public TestEvent(int message) {        
            this.message = message;
            System.out.println("event message:"+message);
        }
        public int getMessage() {
            return message;
        }
    }
    
    public class EventListener {
        public int lastMessage = 0;
    
        @Subscribe
        public void listen(TestEvent event) {
            lastMessage = event.getMessage();
            System.out.println("Message:"+lastMessage);
        }
        
        // 多监听
        @Subscribe
        public void listen(TestEvent event) {
            lastMessage = event.getMessage();
            System.out.println("Message:"+lastMessage);
        }
    
        public int getLastMessage() {      
            return lastMessage;
        }
    }
    
    public class TestEventBus {
        @Test
        public void testReceiveEvent() throws Exception {
    
            EventBus eventBus = new EventBus("test");
            EventListener listener = new EventListener();
    
            eventBus.register(listener);
    
            eventBus.post(new TestEvent(200));
            eventBus.post(new TestEvent(300));
            eventBus.post(new TestEvent(400));
    
            System.out.println("LastMessage:"+listener.getLastMessage());
            ;
        }
    }
    
    //输出信息
    event message:200
    Message:200
    event message:300
    Message:300
    event message:400
    Message:400
    LastMessage:400
    
    Dead Event：
　　如果EventBus发送的消息都不是订阅者关心的称之为Dead Event。实例如下：
    public class DeadEventListener {
        boolean notDelivered = false;  
           
        @Subscribe  
        public void listen(DeadEvent event) {  
            
            notDelivered = true;  
        }  
       
        public boolean isNotDelivered() {  
            return notDelivered;  
        }  
    }
    
    public class TestDeadEventListeners {
        @Test  
        public void testDeadEventListeners() throws Exception {  
           
            EventBus eventBus = new EventBus("test");               
            DeadEventListener deadEventListener = new DeadEventListener();  
            eventBus.register(deadEventListener);  
    
            eventBus.post(new TestEvent(200));         
            eventBus.post(new TestEvent(300));        
           
            System.out.println("deadEvent:"+deadEventListener.isNotDelivered());
    
        }  
    }
    
    //输出信息
    event message:200
    event message:300
    deadEvent:true
    
    Event的继承：

　　如果Listener A监听Event A, 而Event A有一个子类Event B, 此时Listener A将同时接收Event A和B消息，实例如下：
    public class NumberListener {  
           
        private Number lastMessage;  
       
        @Subscribe  
        public void listen(Number integer) {  
            lastMessage = integer; 
            System.out.println("Message:"+lastMessage);
        }  
       
        public Number getLastMessage() {  
            return lastMessage;  
        }  
    }  
    
    public class IntegerListener {  
           
        private Integer lastMessage;  
       
        @Subscribe  
        public void listen(Integer integer) {  
            lastMessage = integer; 
            System.out.println("Message:"+lastMessage);
        }  
       
        public Integer getLastMessage() {  
            return lastMessage;  
        }  
    }  
    
    public class TestEventsFromSubclass {
        @Test  
        public void testEventsFromSubclass() throws Exception {  
           
            EventBus eventBus = new EventBus("test");  
            IntegerListener integerListener = new IntegerListener();  
            NumberListener numberListener = new NumberListener();  
            eventBus.register(integerListener);  
            eventBus.register(numberListener);  
           
            eventBus.post(new Integer(100));  
           
            System.out.println("integerListener message:"+integerListener.getLastMessage());
            System.out.println("numberListener message:"+numberListener.getLastMessage());
                  
            eventBus.post(new Long(200L));  
           
            System.out.println("integerListener message:"+integerListener.getLastMessage());
            System.out.println("numberListener message:"+numberListener.getLastMessage());        
        }  
    }
    
    //输出类
    Message:100
    Message:100
    integerListener message:100
    numberListener message:100
    Message:200
    integerListener message:100
    numberListener message:200
    
 * </pre>
 *
 */
public class EventBusTest {
	@Before
	public void setUp() throws Exception {
	}
	
	private Logger LOG = LoggerFactory.getLogger(EventBusTest.class);
	EventBus eventBus = new EventBus();
	
	public void register(){
		eventBus.register(this);
	}
	
	public void post(String msg){
		eventBus.post(msg);
	}
	
	@Test
	public void testEventBus() {
		EventBusTest t = new EventBusTest();
		t.register();
		t.post("test 123");
		t.unregister();
		t.post("post string method after unregister");
	}

	@Subscribe
    public void ob1Mthod1(String msg) {
		LOG.info("msg:{}", msg);
    }
	
	public void unregister(){
		eventBus.unregister(this);
	}

}
