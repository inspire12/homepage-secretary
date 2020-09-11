package com.inspire12.secretary.private_study.super_type;

import org.springframework.context.support.StaticApplicationContext;

import java.util.HashMap;
import java.util.Map;

// ParameterizedTypeReference의 작동 원리인 Super Type Token의 동작 원리와 활용법을 알아봅니다.
// https://www.youtube.com/watch?v=01sdXvZSjcI

// generic 은 선언시 사이즈를 알아야하는 new 에는 쓰지 못함
// https://yaboong.github.io/java/2019/01/19/java-generics-1/
@SuppressWarnings("deprecation")
public class TypeToken {
    static <T> T create(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    static class Generic<T> {
        T value;
        void set(T t) {}
        T get() {return null;}
    }

    // typesafe 컴파일 타임에 타입에 대한 에러를 발생해줌
    static class TypeSafeMap {
        Map<Class<?>, Object> map = new HashMap<>();

        <T> void put(Class<T> clazz, T value) {
            map.put(clazz, value);
        }
        <T> T get (Class<T> clazz) {
            return clazz.cast(map.get(clazz));
        }

        <T> T get2 (Class<T> clazz) {
            return clazz.cast(map.get(clazz));
        }
    }

    // Type token: 타입 정보를 값으로 넘기겠다
    // List<Integer> 같은 걸 넘길 수 없는 이유 - class 에는 제네릭 타입을 포함한 대한 정보가 없음 -> super type token

    // Erasure

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 타입 캐스팅은 위험하다. 제네릭은 넘기는 시점에 타입을 알려주는 식으로 처리할 수 있다



        StaticApplicationContext ac = new StaticApplicationContext();
        ac.getBeanFactory().getBeanDefinitionCount();

        Generic<String> s = new Generic<>();
        s.value = "String";

        Generic<Integer> i = new Generic<>();
        i.value = 1;
        i.set(10);

        TypeSafeMap map = new TypeSafeMap();
        map.put(Integer.class, 1);
        map.put(String.class, "아니아니");

        map.get(Integer.class);
    }

}
