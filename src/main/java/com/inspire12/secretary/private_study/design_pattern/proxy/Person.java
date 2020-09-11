package com.inspire12.secretary.private_study.design_pattern.proxy;

import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@ToString
public class Person {
     int a;

     @PostConstruct
     public void postConstruct() {
          System.out.println("postConstruct");
     }
     @PreDestroy
     public void preDestroy() {
          System.out.println("preDestroy");
     }
}
