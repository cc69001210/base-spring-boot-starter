# Getting Started

## 项目介绍
1. 项目基于springboot2.3.3.RELEASE版本进行开发
2. 项目集成了lombok，version: 1.16.18
3. 项目集成了mybatis-plus，version：3.3.2，使用mybatis的版本为3.5.4
4. 项目集成了swagger，版本为2.9.2，
    1. 在application.yml中配置 web.swagger.enable：true 即可生效swagger的配置。
    2. 通过@Api和ApiOperation进行文档生成。