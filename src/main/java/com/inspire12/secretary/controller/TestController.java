package com.inspire12.secretary.controller;

import com.inspire12.secretary.model.FruitInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("/test")
@EnableWebMvc
public class TestController {

    public static class IntObservable extends Observable implements Runnable {
        @Override
        public void run() {
            for (int i=0; i<= 10; i++) {
                setChanged(); // 새로운 변화가 있다는 걸 알려줘야함
                notifyObservers(i);     // push
                // int i = it.next();   // pull
                // 좌우가 바뀐 상태 i를 매개변수로 넘긴 것과 i에 할당하는 duality
            }
        }
    }
    @GetMapping("/observer")
    public String test() {
        Iterable<Integer> iter = Arrays.asList(1,2,3,4);

        Observer ob = new Observer() { // 통보를 받고 일을 함, Observable은 observer 입장을 신경 안써도 되는 장점
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(arg);
            }
        };
        IntObservable io = new IntObservable();
        io.addObserver(ob);
        io.run();


        return "Hello, docker spring";
    }

    @GetMapping("/mono")
    public Flux<List<String>> testMono() {

        final List<String> basket1 = Arrays.asList(new String[]{"kiwi", "orange", "lemon", "orange", "lemon", "kiwi"});
        final List<String> basket2 = Arrays.asList(new String[]{"banana", "lemon", "lemon", "kiwi"});
        final List<String> basket3 = Arrays.asList(new String[]{"strawberry", "orange", "lemon", "grape", "strawberry"});
        final List<List<String>> baskets = Arrays.asList(basket1, basket2, basket3);
        final Flux<List<String>> basketFlux = Flux.fromIterable(baskets);
        Object a = basketFlux.concatMap(basket -> {
            final Mono<List<String>> distinctFruits = Flux.fromIterable(basket).distinct().collectList();
            final Mono<Map<String, Long>> countFruitsMono = Flux.fromIterable(basket)
                    .groupBy(fruit -> fruit)
                    .concatMap(groupedFlux -> groupedFlux.count()
                            .map(count -> {
                                final Map<String, Long> fruitCount = new LinkedHashMap<>();
                                fruitCount.put(groupedFlux.key(), count);
                                return fruitCount;
                            })// 각 과일별로 개수를 Map으로 리턴
                    )// concatMap 으로 순서보장
                    .reduce((accumulatedMap, currentMap) -> new LinkedHashMap<String, Long>() {
                        {
                            putAll(accumulatedMap);
                            putAll(currentMap);
                        }
                    });
            return Flux.zip(distinctFruits, countFruitsMono, (distinct, count) -> new FruitInfo(distinct, count));
        }).subscribe(System.out::println);


        return basketFlux;
    }
}
