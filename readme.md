## 简单搜索引擎
- 默认使用知乎作为搜索入口

## 启动服务
#### 初始化数据
调用`POST` `http://localhost:8080/fools/init` 接口，
使用广度优先算法默认爬取100条数据，分析并生成索引。

#### 搜索
接口：  
`GET` `http://localhost:8080/fools/search?query=data`


