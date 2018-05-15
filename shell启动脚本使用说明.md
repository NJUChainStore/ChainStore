# shell启动脚本使用说明

## 脚本功能

./master.sh start 后台启动主机
./master.sh stop 关闭主机
./database.sh start {port} 后台启动存储机,port为端口号
./database.sh stop 关闭存储机
./mining.sh start 后台启动挖矿机
./mining.sh stop 关闭挖矿机
./webservice.sh start 后台启动存取服务层
./webservice.sh stop 关闭存取服务层

./run.sh start 后台启动主机，挖矿机，存取服务层
./run.sh stop 关闭主机，挖矿机，存取服务层

## 注意事项

- 在deploy文件夹内运行以上脚本
- deploy文件夹应包括master、database、mining、webservice四个jar包及其对应shell文件以及run.sh文件
- 建议使用./run.sh start一键启动主机，挖矿机，存取服务层，并使用./database.sh start {port}指令启动多个主机以测试
- 重新启动前请务必运行./run.sh stop及./database.sh stop指令关闭主机，挖矿机，存取服务层及存储机