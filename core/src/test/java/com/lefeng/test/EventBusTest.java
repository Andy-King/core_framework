package com.lefeng.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EventBusTest{

	private Logger LOG = LoggerFactory.getLogger(EventBusTest.class);
	EventBus eventBus = new EventBus();
	
	public void register(){
		eventBus.register(this);
	}
	
	public void post(String msg){
		eventBus.post(msg);
	}
	
	public static void main(String[] args) {
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
