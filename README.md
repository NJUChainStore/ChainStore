# ChainStore

A distributed, Blockchain-Based Storage Solution by NJUChainStore.

[中文](./README.cn.md)

## Deploy in less than 1 minute!

JRE 8 is required.

1. Enter ./deploy
2. `./run.sh run` to start Master(port 8080), Miner(port 8079) and WebService(port 8070)
3. `./database.sh run {portNum, 8081 for example}` to start a Database in specified port
4. Start as many databases as you want. At least 2 is recommended.
5. You are ready to use the system! Access `http://localhost:8070` for Web Management.
6. To stop the system, execute `./database.sh stop` to stop databases and `./run.sh stop` to stop the rest components.

## Features

1. Simple to use, quick to deploy, practical for small application.
2. Scalable, use HTTP for intra-system communication. Multiple databases are allowed (and recommended).

## Technical Detail

For in-depth technical detail of this project, refer to [this doc](./doc/doc.md)

For Bash script instruction (in ./deploy), refer to [this doc](./ShellScriptsInstruction.md) (cn only)

For WebService API, refer to [this doc](./WebService/README.md)

## License

MIT