package com.google.common.eventbus;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
