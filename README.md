学习最好的方式是去官网学习
Log4j2 configuration website 
http://logging.apache.org/log4j/2.x/index.html

maven dependency 
http://logging.apache.org/log4j/2.x/maven-artifacts.html

IDEA JVM DEFAULT CONFIGURATION  PARAMETERS

-Xms128m
-Xmx750m
-XX:ReservedCodeCacheSize=240m
-XX:+UseConcMarkSweepGC
-XX:SoftRefLRUPolicyMSPerMB=50
-ea
-XX:CICompilerCount=2
-Dsun.io.useCanonPrefixCache=false
-Djdk.http.auth.tunneling.disabledSchemes=""
-XX:+HeapDumpOnOutOfMemoryError
-XX:-OmitStackTraceInFastThrow
-Djdk.attach.allowAttachSelf=true
-Dkotlinx.coroutines.debug=off
-Djdk.module.illegalAccess.silent=true
-Dfile.encoding=UTF-8

SpringSecurity 
官网地址：https://docs.spring.io/spring-security/site/docs/5.3.8.RELEASE/reference/html5/#servlet-hello

默认密码会打印出来
用户名信息在 org.springframework.boot.autoconfigure.security.SecurityProperties
默认用户名：user
默认密码控制台随机的： Using generated security password: d2069859-59ee-4cf6-aafc-eb901e83351a