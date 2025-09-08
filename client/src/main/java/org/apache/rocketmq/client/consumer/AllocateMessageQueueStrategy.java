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
package org.apache.rocketmq.client.consumer;

import java.util.List;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * Strategy Algorithm for message allocating between consumers
 * todo: 消费者 消费队列的分配策略；核心作用是在消费者组中实现消息队列的负载均衡。
 * 1.1. AllocateMessageQueueAveragely (平均分配策略)
 *  - 将消息队列尽可能均匀地分配给所有消费者
 *  - 如果有余数，则前几个消费者会多分配一个队列
 *  - 优缺点：消费者处理能力可能不同
 * 2. AllocateMessageQueueAveragelyByCircle (环形平均分配策略)
 *  - 按照消费者顺序依次分配队列，形成环形分配
 * 3. AllocateMessageQueueByConfig (配置分配策略)
 * - 完全按照用户配置的队列列表进行分配；不进行任何自动计算
 * - 需要精确控制每个消费者分配特定队列时；特殊业务场景下的固定分配需求； 测试和调试环境
 * - 缺点: 缺乏灵活性，配置复杂
 * 4. AllocateMessageQueueByMachineRoom (机房分配策略)
 *  - 根据队列的Broker名称和消费者ID中的机房信息进行分配
 *  - 优先将队列分配给同机房的消费者
 *  - 优缺点：跨机房部署环境；需要减少跨机房网络流量时；对网络延迟敏感的应用；优点: 减少跨机房流量，降低延迟；缺点: 机房负载可能不均衡，实现较复杂
 * 5. AllocateMessageQueueConsistentHash (一致性哈希分配策略)
 *  - 使用一致性哈希算法分配队列
 *  - 通过虚拟节点实现更均匀的分配
 *  - 消费者数量变化频繁的场景
 *  - 优点: 消费者增减时只有少量队列需要重新分配 缺点: 实现复杂，可能出现分配不均
 */
public interface AllocateMessageQueueStrategy {

    /**
     * Allocating by consumer id
     *
     * @param consumerGroup current consumer group
     * @param currentCID current consumer id
     * @param mqAll message queue set in current topic
     * @param cidAll consumer set in current consumer group
     * @return The allocate result of given strategy
     */
    List<MessageQueue> allocate(
        final String consumerGroup,
        final String currentCID,
        final List<MessageQueue> mqAll,
        final List<String> cidAll
    );

    /**
     * Algorithm name
     *
     * @return The strategy name
     */
    String getName();
}
