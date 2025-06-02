package org.apache.rocketmq.test.peng;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author : pengcheng
 * @since : 2025-05-18
 */
public class _05_18_CompletableFuture_Test {

    /**
     * CompletableFuture
     * 1. 等待异步线程完成后，在执行一些逻辑
     */
    @Test
    public void test12312() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 2 / 0;
            System.out.println("任务执行结束");
            String name = Thread.currentThread().getName();
            return "结果" + name;
        }, executorService).thenAccept(result -> {
            System.out.println("获取到异步处理结果：" + result);
        }).exceptionally(ex -> {
            // 只要其中某个出现了异常会走这里
            System.out.println("出现异常了: " + ex.getMessage());
            return null;
        });

        System.out.println("主线程继续中");

        Void aVoid = future.get();

        System.out.println("主线程执行结束");
    }


    @Test
    public void test222() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务执行结束:" + getThreadName());
            return "task1Result";
        }, executorService).thenAcceptAsync(result -> {
            System.out.println(getThreadName() + " 获取到异步处理结果：" + result);
        }, executorService).thenAccept(result -> {
            System.out.println("这里也拿到任务执行结果：" + result);
        }).exceptionally(ex -> {
            // 只要其中某个出现了异常会走这里
            System.out.println("出现异常了: " + ex.getMessage());
            return null;
        });

        System.out.println("主线程继续中");

        Void aVoid = future.get();

        System.out.println("主线程执行结束");
    }


    /**
     * 1. 等待任务1执行完毕
     * 2. 等待任务2执行完毕
     * 3. 依赖两个任务均执行完毕，才执行任务3
     */
    @Test
    public void test2() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务1";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务2";
        });


        CompletableFuture<Void> future = CompletableFuture.allOf(task1, task2);
        CompletableFuture<Void> future1 = future.thenRun(() -> {
            try {
                String s1 = task1.get();
                String s2 = task2.get();
                System.out.println("任务3：" + s1 + "--------" + s2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        future1.join();

        System.out.println("主线程执行结束");
    }

    /**
     * 链式异步操作
     */
    @Test
    public void testCompletableFuture() {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            System.out.println(getThreadName() + "获取到用户ID");
            return "1111";
        }).thenApplyAsync(userId -> {
            System.out.println(getThreadName() + "拿到了userId=" + userId);
            sleep(3);
            User user = new User();
            user.setUserId(userId);
            user.setUsername("hello");
            return user;
        }).thenApplyAsync(user -> {
            System.out.println(getThreadName() + "拿到了用户信息：" + user);
            sleep(5);
            user.setUsername(user.getUsername() + "-update");
            return user;
        }).thenAccept(user -> {
            System.out.println(getThreadName() + "处理完成入库：" + user);
        });

        future.join();

        System.out.println(getThreadName() + "主线程----------------------");

    }


    public static class User {
        public User(String userId, String username) {
            this.userId = userId;
            this.username = username;
        }

        public User() {
        }

        private String userId;
        private String username;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userId='" + userId + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    /**
     * anyOf() 任意一个完成即可
     */
    @Test
    public void testDD() {
        CompletableFuture<String> task1Future = CompletableFuture.supplyAsync(() -> {
            sleep(2);
            return "结果1";
        });
        CompletableFuture<String> task2Future = CompletableFuture.supplyAsync(() -> "结果2");
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(task1Future, task2Future);
        anyFuture.thenAccept(result -> System.out.println("最先完成的结果: " + result));
    }


    /**
     * Combine / Compose 方法使用
     */
    @Test
    public void testCombineAndCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "result1");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "result2");

        future1.thenCompose(r1 -> {
            System.out.println("收到了future1的结果：" + r1);
            return future2;
        }).thenAccept(r2 -> {
            System.out.println("收到r2结果：" + r2);
        });


        // combine
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "result1");
        CompletableFuture<String> future4 = CompletableFuture.supplyAsync(() -> "result2");

        CompletableFuture<String> future5 = future3.thenCombine(future4, (r1, r2) -> {
            System.out.println(r1 + r2);
            return r1 + "_" + r2;
        });


        String s = future5.get();
        System.out.println("结果：" + s);

        sleep(100000);

    }


    /**
     * Stream zip
     * 1. thenApply().whenComplete().exceptionally().thenAccept()
     * 2. 上面的构成链都是顺序执行的，并不是执行链中最后的thenAccept(), 才执行whenComplete()
     * 3. 也并不是链条上的任意位置发生异常都会执行exceptionally()方法，只有exceptionally前面的方法报错才会执行
     */
    @Test
    public void test() throws ExecutionException, InterruptedException {
        CompletableFuture<User> completableFuture = CompletableFuture.supplyAsync(() -> new User("111", "2222"));

        completableFuture.thenApply(user -> {
            System.out.println("thenApply: {}" + user);
            user.setUsername("2222-thenApply");
            if (true) {
                //int a = 1 / 0;
            }
            return user;
        }).whenComplete((r, e) -> {
            System.out.println("complete 执行: " + r);
            r.setUsername("complete");
        }).exceptionally(ex -> {
            System.out.println("333 发生了异常");
            User user = new User("ex-3333", "ex-33333");
            return user;
        }).thenAccept(user -> {
            System.out.println("执行到 thenAccept");
            if (true) {
                int a = 1 / 0;
            }
            System.out.println("thenAccept 执行 user:{}" + user);
        });

        User user = completableFuture.get();
        System.out.println("result " + user);

        sleep(1000);

    }


    /**
     * CompletableFuture 是否会触发whenComplete()的执行
     */
    @Test
    public void testWheComplete() {
        // 由前一个阶段触发
        CompletableFuture<User> completableFuture = buildCompletableFuture();
        completableFuture.whenComplete((user, throwable) -> {
            System.out.println("whenComplete 执行了: " + user);
        });

        System.out.println("");
        System.out.println("");
        System.out.println("");
        // 手动触发
        CompletableFuture<User> completableFuture1 = new CompletableFuture<>();

        // 注掉这行 不会触发whenComplete()执行
        //completableFuture1.complete(new User("1111", "111111"));
        // 这里就不会执行 ;
        completableFuture1.completeExceptionally(new RuntimeException("异常"));

        completableFuture1.whenComplete((user, throwable) -> {
            System.out.println("执行了whenComplete:" + user + throwable);
        });

        sleep(10000000);
    }


    private CompletableFuture<User> buildCompletableFuture() {
        return CompletableFuture.supplyAsync(() -> new User("111", "2222"));
    }


    private void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }


}
