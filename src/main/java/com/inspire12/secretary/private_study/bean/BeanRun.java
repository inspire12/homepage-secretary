package com.inspire12.secretary.private_study.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

public class BeanRun {
    public static void main(String[] args) {
        // 빈 등록
        //IoC컨테이너 생성, 동시에 컨테이너로 동작한다
        StaticApplicationContext ac = new StaticApplicationContext();
        // Hello 클래스를 Hello1이라는 이름의 싱글톤 빈으로 컨테이너 등록한다
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        // IoC 컨테이너가

        // 빈 메타정보를 담은 오브젝트를 만든다. 빈 클래스는 Hello로 지정한다
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);

        helloDef.getPropertyValues().addPropertyValue("name","Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
        ac.registerBeanDefinition("hello2", helloDef);

        ac.registerBeanDefinition("printer", new RootBeanDefinition(Hello.class));

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();

//        GenericApplicationContext gac = new GenericApplicationContext("springbook.xml");
//

    }
}
