## Nginx

### Nginx 核心概念与架构
#### 基础原理--Nginx 如何实现高并发？
1. 事件驱动模型：单线程处理多个连接（异步非阻塞），基于 epoll（Linux）或 kqueue（FreeBSD），避免线程切换开销。
2. Master-Worker 架构：
   * Master 进程：读取配置、管理 Worker 进程。
   * Worker 进程：处理实际请求，数量通常等于 CPU 核心数（worker_processes auto）。
3. 反向代理 vs 正向代理：
   * 反向代理：代理服务器接收客户端请求，转发到内部服务器（如 Nginx 代理 Tomcat），客户端无需配置。
   * 正向代理：客户端主动配置代理服务器（如浏览器配置 Proxy），用于访问外部资源。

#### 反向代理配置
```nginx
server {
    listen 80;
    server_name example.com;
    
    # 反向代理配置
    location / {
        proxy_pass http://backend_server;  # 后端服务器地址
        proxy_set_header Host $host;       # 传递原始请求的 Host 头
        proxy_set_header X-Real-IP $remote_addr;  # 传递客户端真实 IP
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;  # 传递完整代理链
        proxy_connect_timeout 60s;         # 连接超时时间
        proxy_send_timeout 60s;            # 发送超时时间
        proxy_read_timeout 60s;            # 读取超时时间
    }
}
```
#### 负载均衡配置
##### 负载均衡算法
* 轮询（默认）：按顺序分发请求。
```nginx
upstream backend {
    server backend1.example.com;
    server backend2.example.com;
}
```
* IP 哈希（ip_hash）：根据客户端 IP 计算哈希值，确保同一客户端请求同一服务器（会话保持）。
```nginx
upstream backend {
    ip_hash;
    server backend1.example.com;
    server backend2.example.com;
}
```
* 加权轮询（weight）：根据服务器性能分配权重。
```nginx
upstream backend {
    server backend1.example.com weight=5;  # 权重 5
    server backend2.example.com weight=3;  # 权重 3
}
```
* 最少连接（least_conn）：将请求分发到当前连接数最少的服务器。
```nginx
upstream backend {
    least_conn;
    server backend1.example.com;
    server backend2.example.com;
}
```