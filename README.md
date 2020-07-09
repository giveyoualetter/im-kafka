# im-kafka  
真的是一个非常非常简单的所谓即时通讯的例子
使用的技术包括：
netty、kafka、springboot  
基本过程画个图吧：  
![image](https://github.com/giveyoualetter/im-kafka/blob/master/image/1.PNG)  
1、首先一个用户使用 ws://localhost:9000/websocket 和netty服务器建立websocket连接  
2、然后发送 register:2000 表明用户ID是2000注册  
3、另一个用户使用post方法发送信息给用户2000  

json格式消息说明：  
userId:发送用户ID,这是是3000  
peerUserId:接收的用户ID，这里是2000  
groupId：这个本来是用作区分群组，这里没用  
msg：发送的消息  

再说kafka，这里为每一个用户创建一个主题，主题的名字就是用户ID，主题的分区也只设置一个，netty为每个用户创建一个websocket channel(通道)，用户注册成功之后在通道里设置一个while循环以一秒为间隔从kafka指定的主题分区中消费消息

**************************************
netty websocket使用netty源码的例子改动
