package org.apache.rocketmq.test.peng;

public interface DemoPipeline {

    void execute(String star1, Integer aa);

    default DemoPipeline pipe(DemoPipeline source) {
        return (ctx, request) -> {
            source.execute(ctx, request);
            execute(ctx, request);
        };
    }
}