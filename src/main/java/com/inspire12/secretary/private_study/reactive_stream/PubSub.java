package com.inspire12.secretary.private_study.reactive_stream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
// https://www.youtube.com/watch?v=DChIxy9g19o&t=148s
// publisher - (events) -> subscriber - (demand) -> subscription <-> publisher
// 비동기 관점: 각 단계에서 속도, 메모리 부하 많이 다를 수 있다. 생성을 지연 -> 메모리 크기가 일정
// subscriber 실제로 일을 처리, 시퀀셜로 올거라 생각을 하고 그 이외 상황에 대한 예외는 생각 안하자, 병렬적으로 하면 코드가 너무 지저분하고 복잡해짐
// subscription 에서 같은 쓰레드에서 처리한다
// 스레드 한정 위반
public class PubSub {
    public static void main(String[] args) throws InterruptedException {
        Iterable<Integer> itr = Arrays.asList(1,2,3,4,5);
        ExecutorService es = Executors.newCachedThreadPool();

        Publisher p = new Publisher() {

            @Override
            public void subscribe(Flow.Subscriber subscriber) {
                Iterator<Integer> it = itr.iterator(); // subscriber는 이어서할수도 한번에 다할 수도 있으니 새로 만들어서 써야지 (db에서 가져온 collection 데이터라 생각 )

                // scriber가 subscribe 하는 순간에 subcription의 onSubscribe가 실행됨
                // subscription 은 요청을 날림 
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        es.execute( () -> {
                            int i = 0;
                            while(i++ < n) {
                                if (it.hasNext()) {
                                    subscriber.onNext(it.next());
                                } else {
                                    subscriber.onComplete();
                                    break;
                                }
                            }
                        });
                    }
                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        Subscriber s = new Subscriber() {
            Flow.Subscription subscription;
            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println(Thread.currentThread().getName()  + " onSubscribe");
                this.subscription = subscription;
                this.subscription.request(1);
            }

            int bufferSize = 2;
            @Override
            public void onNext(Object item) {
                // 작업을 수행
                System.out.println(Thread.currentThread().getName() + " onNext " + item);

//                if (--bufferSize <= 0) {
//                    System.out.println("buffer reset");
//                    bufferSize = 2;
                    this.subscription.request(1);// lazy 하게 처리를 했을 때 요청을 이어가야하니까
//                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
                subscription.cancel();
            }
        };
        p.subscribe(s);
//        es.awaitTermination(10, TimeUnit.DAYS);
//        es.shutdown();
    }
}
