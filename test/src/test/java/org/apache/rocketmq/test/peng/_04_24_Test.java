package org.apache.rocketmq.test.peng;

import org.junit.Test;

/**
 * 测试
 *
 * @author : pengcheng
 * @since : 2025-04-24
 */
public class _04_24_Test {

    /**
     * 测试
     */
    @Test
    public void testPipeline() {
        DemoPipeline demoPipeline = (name, age) -> {
            System.out.println("name: " + name + 1);
            System.out.println("age: " + age + 1);
        };

        DemoPipeline demoPipeline2 = (name, age) -> {
            System.out.println("name: " + name + 2);
            System.out.println("age: " + age + 2);
        };

        DemoPipeline demoPipeline3 = (name, age) -> {
            System.out.println("name: " + name + 3);
            System.out.println("age: " + age + 3);
            name = name + 3;
        };

        demoPipeline.pipe(demoPipeline2).pipe(demoPipeline3).execute("nih", 1);

        System.out.println("sdddddddddddd");
    }


}
