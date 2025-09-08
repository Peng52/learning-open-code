/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.client.impl.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.body.ConsumeMessageDirectlyResult;

import java.util.List;

/**
 * 1. ConsumeMessageConcurrentlyService ：处理并发消费模式
 * - 将任务提交到 consumeExecutor线程池
 * - 在 run()方法中调用用户的 MessageListenerConcurrently
 * - 根据消费结果进行ACK或重试
 * 2. ConsumeMessageOrderlyService：专门用于顺序消费场景的实现。
 * - ​​队列加锁​​：对每个消息队列加锁，确保同一时间只有一个线程消费该队列
 * - ​​顺序拉取​​：严格按照队列中的消息顺序进行消费
 * - ​​锁续期​​：通过定时任务定期续期队列锁，防止消费过程中锁过期
 * -   顺序提交​​：确保消费成功后才提交下一条消息
 * 3. ConsumeMessagePopConcurrentlyService：基于POP（Pop Over Pull）模式的并发消费服务。
 */
public interface ConsumeMessageService {
    void start();

    void shutdown(long awaitTerminateMillis);

    void updateCorePoolSize(int corePoolSize);

    void incCorePoolSize();

    void decCorePoolSize();

    int getCorePoolSize();

    ConsumeMessageDirectlyResult consumeMessageDirectly(final MessageExt msg, final String brokerName);

    void submitConsumeRequest(
            final List<MessageExt> msgs,
            final ProcessQueue processQueue,
            final MessageQueue messageQueue,
            final boolean dispathToConsume);

    void submitPopConsumeRequest(
            final List<MessageExt> msgs,
            final PopProcessQueue processQueue,
            final MessageQueue messageQueue);
}
