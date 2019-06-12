# School-TimeTable
基于Springboot的Android课程表


# 技术栈
> 1. Spring Boot： Jackson, Shiro, Druid, MyBatis,Maven
> 2. Vue ：          axios，element ui，vue-router，vuex
> 3. Android ：   Gradle,Gson,Okhttp3,Fastjson



## 功能

### 课程表功能

* 课表
* 个人中心
* 查看课程的分数/出勤/课堂作业等信息


## 启动

1. 开发环境：
    * MySQL
    * JDK1.8或以上
    * Maven
    * Nodejs
    * idea
    * Android Studio
    
2. 数据库导入\src\main\java\com\android\backend\db下的数据库文件
    * school_table.sql

3. 启动后端服务

    打开命令行，输入命令
     ```bash
    cd school timetable
    mvn install
    mvn clean package
    java -jar school timetable/target/Java –jar backend-0.0.1-SNAPSHOT
    进入http://localhost:8080/swagger-ui.html/  查看相应的接口
    ```
    
4. 启动vue前端

    打开命令行，输入命令#
     ```bash
    npm install -g cnpm --registry=https://registry.npm.taobao.org
    cd school timetable/admin-vue
    cnpm install
    cnpm run dev
    ```
    `http://localhost:8081`
    
5. 启动Android端
    ```bash
    进入frontend文件夹里
    gradle编译
    运行
    ```
  
