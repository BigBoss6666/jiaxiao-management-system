# Jiaxiao

一个基于 Spring Boot 的驾校管理系统，面向学员信息维护、学时管理、考试记录与考试场次预约等场景。

## 功能概览

- 管理员注册、登录、退出
- 学员信息增删改查
- 学员学时累积
- 科目进度流转
- 考试记录新增与清空
- 考试场次管理与预约

## 技术栈

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven

## 项目结构

- `src/main/java/com/example/jiaxiao/controller`：页面与接口控制器
- `src/main/java/com/example/jiaxiao/entity`：实体模型
- `src/main/java/com/example/jiaxiao/repository`：数据访问层
- `src/main/java/com/example/jiaxiao/service`：业务层
- `src/main/resources/templates`：页面模板
- `src/main/resources/static`：静态资源

## 本地运行

1. 准备 MySQL 数据库，并创建 `jiaxiao` 库。
2. 通过环境变量配置数据库连接：
   - `DB_URL`
   - `DB_USERNAME`
   - `DB_PASSWORD`
3. 在项目目录执行：

```bash
mvn spring-boot:run
```

默认使用 Spring Boot 内置 Web 服务启动项目。

## 说明

仓库中的配置已做公开脱敏处理，数据库账号密码请通过本地环境变量注入。
