# fuckServer
web server in action
## Infrastructure
using the `nio` multiplexing model is to achieve the non-blocking server.<br><br>
For example,one request has arrived the fuckServer,then one thread will accept the request to a `non-blocking queue`,another thread will also listen to the this non-blocking queue and register to the selector and to handle it.<br><br>
In fact,the selector is an another non-blocking queue.

## Progess
coding ...<br>实现了解析 PHP ，可直接执行 php web 框架，将文件放入 PhpApp 内即可运行<br>
后期考虑封装一个基于本 SERVER 的 java web 框架<br>压力测试一下，看一下负载多少 
