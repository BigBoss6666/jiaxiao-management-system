# 驾校管理系统 Jiaxiao

一个基于 Spring Boot 的驾校管理系统，覆盖管理员登录、学员档案管理、学时累计、考试记录维护和考试场次预约等常见业务流程，适合作为中小型业务系统练习项目或后端接口实践项目。

## 项目亮点

- 同时包含页面访问与 REST API 两种交互方式
- 围绕学员培训过程设计了相对完整的业务闭环
- 采用 `controller / entity / repository / service` 分层结构
- 使用 JPA 快速完成数据持久化与基础 CRUD

## 核心功能

- 管理员注册、登录、退出
- 学员信息增删改查
- 学员学时累积
- 科目进度流转
- 考试记录新增与清空
- 考试场次创建、分配学员、查看场次学员

## 技术栈

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven

## 项目结构

- `src/main/java/com/example/jiaxiao/controller`：页面控制器与接口控制器
- `src/main/java/com/example/jiaxiao/entity`：实体模型
- `src/main/java/com/example/jiaxiao/repository`：数据访问层
- `src/main/java/com/example/jiaxiao/service`：业务层及实现
- `src/main/resources/templates`：登录、注册、学员管理页面
- `src/main/resources/static`：样式等静态资源

## 页面与接口

### 页面入口

- `GET /login`：登录页
- `GET /register`：注册页
- `GET /students`：学员管理页

### 主要接口

- `GET /api/students`：获取学员列表
- `POST /api/students`：新增学员
- `PUT /api/students/{id}`：更新学员
- `POST /api/students/{id}/next-subject`：推进学员科目进度
- `POST /api/students/{id}/add-hours`：累积培训学时
- `POST /api/students/{id}/exams`：新增考试记录
- `GET /api/exam-slots`：获取考试场次
- `POST /api/exam-slots/{id}/assign`：给场次分配学员
- `GET /api/exam-slots/{id}/students`：查询场次学员

## 本地运行

### 环境要求

- JDK 17
- Maven 3.9+
- MySQL 8

### 启动步骤

1. 创建数据库 `jiaxiao`。
2. 通过环境变量配置数据库连接：
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
3. 在项目目录执行：

```bash
mvn spring-boot:run
```

项目默认使用 Spring Boot 内置 Web 服务启动。

## 仓库说明

- 当前公开仓库已完成基础脱敏处理
- 数据库账号密码不存放在仓库中，请通过本地环境变量注入
- 当前代码以本地开发与学习演示为主
