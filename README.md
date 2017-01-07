# tiny-server
web server in action
## Infrastructure
using the `nio` multiplexing model is to achieve the non-blocking server.<br><br>
For example,one request has arrived the fuckServer,then one thread will accept the request to a `non-blocking queue`,another thread will also listen to the this non-blocking queue and register to the selector and to handle it.<br><br>
In fact,the selector is an another non-blocking queue.

## Progess
### PHP SUPPORT
the server can be used as `the php web server` , <br>just put your php files into the `PhpApp` floder , <br>the route format is limited as `http://localhost/a/b/c/d/e?name=zuston&age=24` , <br>the php entrance file is the `index.php` , just like the way when using the nginx.<br>

### JAVA SERVLET IMPLEMENTATION
coding..................
<br><br>
# 实践 web server
## 进度
### 支持 PHP<br>
实现了 `fastcgi` 协议，直接打开 `php-fpm`，就可以支持了<br>只需要将 `php` 文件放在 `PhpApp` 文件夹下，<br>路由方式 `http://localhost/a/b/c/d/e?name=zuston&age=24`,<br>入口文件为 `index.php`
### JAVA SERVLET 协议实现<br>
协议实现有困难，可以自己实现 `http` 方法实现一个 JAVA 框架
