# bt-txchat-boot

### 介绍
企业微信-会话存档-spring-boot版本

##一、 部署说明

### 1. Jdk安装：
卸载自带jdk
	```shell script
	rpm -qa | grep jdk
    ```
	使用命令逐个删除
	```shell script
	yum -y remove java-1.7.0-openjdk-headless-1.7.0.191-2.6.15.5.el7.x86_64
	```
	直到java -version没有提示安装
	/home下创建jdk目录
	上传jdk-8u201-linux-x64.tar.gz文件到/home
	上传完成解压
	```shell script
	tar -zxvf jdk-8u201-linux-x64.tar.gz
    ```
	配置环境变量
	```shell script
    vi /etc/profile
	```
	最后添加
	``` shell
	export JAVA_HOME=/home/jdk/jdk1.8.0_201
	export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar 
	export PATH=$PATH:$JAVA_HOME/bin
	```
	执行
	```shell script
	source /etc/profile
    ```
	验证
	```shell script
	java -version
    ```
### 2. 日志分割工具安装Cronolog安装：
   将下载好的安装包减压
   ```shell script
   tar zxvf cronolog-1.6.2.tar.gz
   ```
   进入cronolog安装文件所在目录
   ```shell script
   cd cronolog-1.6.2
   ```
   运行安装
   ```shell script
       ./configure
       make
       make install
   ```
   查看cronolog安装后所在目录
    ```shell script
        which cronolog
    ```
### 3. 微信企业会话sdk系统文件放置
   将libWeWorkFinanceSdk_Java.so上传到/usr/lib64/下
   
### 4.项目部署
   在idea中通过maven的install发布项目 bt-txchat-boot-1.0.0.jar、start.sh、stop.sh复制到对应的要不是的目录下；
   启动项目：`./start.sh`，
   停止项目：`./stop.sh`
   端口等参加在 yml文件中配置
   
# 二、接口说明
接口对接请参考企业微信接口流程开发,code错误码请参考企业微信文档
### 1.初始化sdk

    `/txchat/initSdk`
    
	示例地址：`http://localhost:8085/txchat/initSdk`
    
POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 否   | 初始化sdkKey(可以指定) | 
| corpid | Stirng | 是   | 企业ID | 
| secrectkey | String | 是   | 会话的secrectkey |

响应内容：

	{
	    "msg": "init sdk success",
	    "code": 0,
	    "data": "140710444556672" //无sdkId 返回自动生成的sdkId,有skdId返回当前sdk
	}

### 2.销毁skd
	`txchat/destroySdk`

POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 

### 3.获取会话信息不解密
	`/txchat/getChatData`

POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 
| seq | int | 是   | seq 会话seq | 
| limit | int | 是   | 会话条数 | 
| timeout | int | 是   | 超时时间(秒) | 
| proxy | String | 否   | 代理名 | 
| passwd | String | 否   | 代理密码 | 

响应示例
	
	{
	    "msg": "getchatdata success",
	    "code": 0,
	    "data": "{\"errcode\":301042,\"errmsg\":\"get chatdata whiteip not match, hint: [1620120567_187_5cf66668a5018886c111a2d02f4b902e], from ip: 116.113.165.71, more info at https://open.work.weixin.qq.com/devtool/query?e=301042\",\"chatdata\":[]}"
	}

### 4.获取会话信息解密
	`/txchat/getDecryptChatData`
	
POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 
| seq | int | 是   | seq 会话seq | 
| limit | int | 是   | 会话条数 | 
| timeout | int | 是   | 超时时间(秒) | 
| pk | String | 是   | 私钥，在后台配的 | 
| proxy | String | 否   | 代理名 | 
| passwd | String | 否   | 代理密码 | 
	
### 5.通过encryptKey解密数据
	`/txchat/decryptData`
	
POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 
| encryptKey | String | 是   | 参考企业微信文档 | 
| encryptChatMsg | String | 是   | 加密数据 | 
	
### 6.通过私钥解密数据
	`/txchat/decryptDataByPk`
	
POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 
| pk | String | 是   | 私钥，在后台配的 | 
| encryptRandomKey | String | 是   | 返回数据包含encrypt_random_key | 
| encryptChatMsg | String | 是   | 加密数据 | 


### 7.拉取媒体文件信息
	`/txchat/getMediaData`
	
POST参数：
	
| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| sdkId | String | 是   | skdId | 
| indexbuf | String | 是   | 分片开始点 | 
| sdkfileid | String | 是   | 文件ID | 
| timeout | int | 是   | 超时时间（秒） | 

响应参数：数据咋data中

| 参数 	| 类型 	| 是否必填 | 描述   |
| :-  	| :-   	| :-	  | :-    |
| data | String | 是   | byte[]数据，需要字符串转字节 | 
| isEnd | boolean | 是   | 是否分片结束，true结束，false未结束 | 
| indexbuf | String | 是   | 下次拉取需要使用的indexbuf |
 
示例：
	
	{
	    "msg": "getmediadata success",
	    "code": 0,
		"data": {
			"data":"iuyequeyoicvh...",
			"isEnd":false,
			"indexbuf": "Range:bytes=1048576-1572863"
		}
	}

