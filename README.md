## kms

密钥管理系统

###1.模块说明

####1.1.kms_api：api接口层

####1.2.kms_service：业务服务层

####1.3.kms_web：web处理层

###2.开发配置说明

###2.1.下载gradle2.10

    \\192.168.1.212\share\gradle-2.10-bin.zip

###2.2.配置项目

###2.2.1.kms_service：配置

    1. 配置文件在kms_service/src/main/resources/config.properties

    2. dubbo配置文件在kms_service/src/main/resources/dubbo.properties

###2.2.1.kms_web：配置

    2. dubbo配置文件在kms_web/src/main/resources/dubbo.properties

###2.3.启动项目

    1.kms_service:MainClass:org.ca.kms.common.main.Startup

    2.kms_web:配置到tomcat里即可
