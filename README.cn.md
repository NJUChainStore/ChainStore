# ChainStore

一个基于区块链的分布式存储解决方案。

## 一分钟之内部署ChainStore！

保证装有JRE 8。

1. 进入 `./deploy`
2. `./run.sh run` 启动主机(端口8080), 矿机(端口8079) and 管理平台(端口8070)
3. `./database.sh run {portNum, 8081 for example}` 于指定端口启动一台存储机
4. 启动至少2台存储机；
5. 你已经可以使用这个系统了！ 访问 `http://localhost:8070` 打开管理平台
6. 要停止这个系统, 执行`./database.sh stop`停止所有存储机并`./run.sh stop`以停止主机等。

## Features

1. 简单易用，快速部署，环境要求低，适合小型项目；
2. 可扩展，支持多个存储机。

## 技术详情

关于项目架构和实现技术细节，参考[这个文档](./doc/doc.md)

关于自带Bash脚本使用教程（位于./deploy）, 参考[这个文档](./ShellScriptsInstruction.md) (cn only)

关于管理平台API规范, 参考[这个文档](./WebService/README.md)

## License

MIT
