package org.jee.framework.core.monitor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.StopWatch;

/**
 * Simple aspect that monitors call count and call invocation time. It uses JMX annotations and therefore can be
 * monitored using any JMX console such as the jConsole
 * <pre>
 * {@code
	<?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:cache="http://www.springframework.org/schema/cache"
           xsi:schemaLocation="http://www.springframework.org/schema/aop
             http://www.springframework.org/schema/aop/spring-aop.xsd
             http://www.springframework.org/schema/beans
             http://www.springframework.org/schema/beans/spring-beans.xsd
             http://www.springframework.org/schema/cache
             http://www.springframework.org/schema/cache/spring-cache.xsd
             http://www.springframework.org/schema/context
             http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--
            Simply defining this bean will cause requests to owner names to be saved.
            This aspect is defined in petclinic.jar's META-INF/aop.xml file.
            Note that we can dependency inject this bean like any other bean.
        -->
        <aop:aspectj-autoproxy>
            <aop:include name="callMonitor"/>
        </aop:aspectj-autoproxy>
    
        <!-- Call monitoring aspect that monitors call count and call invocation time -->
        <bean id="callMonitor" class="org.jee.framework.core.monitor.MethodCallMonitoringAspect"/>
    
        <!--
            Exporter that exposes the CallMonitoringAspect via JMX,
            based on the @ManagedResource, @ManagedAttribute, and @ManagedOperation annotations.
        -->
        <context:mbean-export/>
        
    </beans>
 * }
 * </pre>
 * @author AK
 */
@ManagedResource("repository:type=CallMonitor")
@Aspect
public class MethodCallMonitoringAspect {

    private boolean enabled = true;

    private int callCount = 0;

    private long accumulatedCallTime = 0;


    @ManagedAttribute
    public void setEnabled(boolean enabled) {
    	this.enabled = enabled;
    }

    @ManagedAttribute
    public boolean isEnabled() {
        return enabled;
    }

    @ManagedOperation
    public void reset() {
        this.callCount = 0;
        this.accumulatedCallTime = 0;
    }

    @ManagedAttribute
    public int getCallCount() {
        return callCount;
    }

    @ManagedAttribute
    public long getCallTime() {
        return (this.callCount > 0 ? this.accumulatedCallTime / this.callCount : 0);
    }


    @Around("within(@org.springframework.stereotype.Repository *)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        if (this.enabled) {
            StopWatch sw = new StopWatch(joinPoint.toShortString());

            sw.start("invoke");
            try {
                return joinPoint.proceed();
            } finally {
                sw.stop();
                synchronized (this) {
                    this.callCount++;
                    this.accumulatedCallTime += sw.getTotalTimeMillis();
                }
            }
        } else {
            return joinPoint.proceed();
        }
    }

}
