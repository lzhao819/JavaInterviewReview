## Quartz 任务调度框架
* 核心概念：包含 Job（任务逻辑）、Trigger（触发规则）、Scheduler（调度器）。支持简单触发、Cron 表达式触发等多种调度方式。
### 线程池隔离技术与生产者 - 消费者模型
•	线程池原理：通过复用线程减少线程创建和销毁开销，控制并发量，避免资源耗尽。核心参数包括核心线程数、最大线程数、队列容量等。
•	项目实践：在 “Trace Popup” 项目中，将原有串行消息处理重构为生产者 - 消费者模型，线程池作为消费者处理任务，实现并行分发与执行。复习时需理解线程池拒绝策略（AbortPolicy、CallerRunsPolicy 等），以及生产者 - 消费者模型如何通过阻塞队列（如 BlockingQueue）实现线程间通信和数据传递。

#### 案例：使用Quartz 定时任务，在特定时间点（5 分钟、8 分钟、12 分钟）发送不同状态通知：
需要利用 Quartz 的Cron 表达式定义触发规则，并为每个时间点创建独立的任务实例。
1. Quartz 核心组件：
   * Job：定义通知任务逻辑
   * Trigger：通过 Cron 表达式配置触发时间。
   * Scheduler：调度器管理任务的注册、启动与停止。
2. Cron 表达式配置 
   * **语法结构：秒 分 时 日 月 周 年（可选**
   * 5 分钟后触发：假设交易时间为10:00:00，5 分钟后为10:05:00，Cron 为0 5 10 * * ?
   * 8 分钟后触发：0 8 10 * * ?
   * 12 分钟后触发：0 12 10 * * ?
   * 动态生成 Cron：通过代码计算触发时间的分钟、小时、日期等字段，拼接为 Cron 表达式，避免硬编码。
3. 参数传递与任务标识
   
   JobDataMap：通过```usingJobData()```方法向 Job 传递交易Id/Payload/通知状态等参数。 

   任务唯一性：
      * 用tradeId + 时间延迟作为 Payload 和 Trigger 的 identity，确保同一交易的相同时间点任务不会重复创建。
      * 若交易已确认，可通过```scheduler.checkExists(identity)```检查任务是否存在，避免重复调度。
      * Quartz 会自动处理重复注册的任务（覆盖或报错，取决于配置）
4. 持久化任务（适用于分布式系统）
   * 问题：默认 Quartz 使用内存存储任务，系统重启后任务丢失。
     配置 Quartz 使用数据库持久化（如 MySQL），修改quartz.properties：
     ``` properties
     org.quartz.jobStore.class = org.quartz.impl.jdbcjobstore.JobStoreTX
     org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
     org.quartz.jobStore.dataSource = myDS
     org.quartz.dataSource.myDS.driver = com.mysql.cj.jdbc.Driver
     org.quartz.dataSource.myDS.url = jdbc:mysql://localhost:3306/quartz_db
     ```
     
5. 任务优先级与重试机制
   * 优先级设置：为 “超时违规” 通知配置更高优先级（通过 Trigger 的 setPriority() 方法），确保系统资源紧张时优先执行。
   * 重试逻辑：在 Job 中添加失败重试（结合 SimpleTrigger 的 setRetryCount()。
6. 面试高频问题解答
* 如果交易在 5 分钟内已确认，如何取消后续通知？
   * 在业务逻辑中调用 scheduler.deleteJob() 删除对应 Trigger 和 Job。
   * 或在 Job 执行前检查交易状态（如查询数据库），若状态为 “已确认” 则跳过通知。 
* Quartz 集群环境下如何避免任务重复执行？
  * 通过数据库持久化任务，Quartz 集群会通过锁机制保证同一任务仅在一个节点执行（参考 JobStoreTX 的分布式锁实现）。
* Cron 表达式能否动态生成？如何处理跨天的延迟？
  * 可以。通过 Calendar 类计算触发时间的绝对日期（如交易时间为 23:55，12 分钟后为次日 00:07），动态生成包含正确日期 / 时间的 Cron 表达式。
