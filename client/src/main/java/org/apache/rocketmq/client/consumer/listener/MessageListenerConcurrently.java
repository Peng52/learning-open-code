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
package org.apache.rocketmq.client.consumer.listener;

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * A MessageListenerConcurrently object is used to receive asynchronously delivered messages concurrently
 *1.RocketMQ 并发消费模式
 *2 “至少一次”（At Least Once） 投递，并依靠客户端的幂等性来保证最终的业务正确性。
 * 并发处理：消息由线程池并发消费。
 *
 * 批量传递：一次调用收到一个消息列表。
 *
 * 两个状态：CONSUME_SUCCESS（成功） 或 RECONSUME_LATER（失败重试）。
 *
 * 幂等性：必须确保业务逻辑能处理重复消息。
 *
 * 异常捕获：推荐在内部捕获异常并返回重试状态，而非抛出。
 */
public interface MessageListenerConcurrently extends MessageListener {
    /**
     * It is not recommend to throw exception,rather than returning ConsumeConcurrentlyStatus.RECONSUME_LATER if
     * consumption failure
     *
     * todo 实现消息的消费，实现这个接口就好了
     * @param msgs msgs.size() >= 1<br> DefaultMQPushConsumer.consumeMessageBatchMaxSize=1,you can modify here
     * @return The consume status
     */
    ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
        final ConsumeConcurrentlyContext context);
}
