# fuckServer
web server in action
## Infrastructure
using the `nio` multiplexing model is to achieve the non-blocking server.<br><br>
For example,one request has arrived the fuckServer,then one thread will accept the request to a `non-blocking queue`,another thread will also listen to the this non-blocking queue and register to the selector and to handle it.<br><br>
In fact,the selector is an another non-blocking queue.

## Progess
coding ...
