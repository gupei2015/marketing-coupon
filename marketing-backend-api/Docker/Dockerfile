FROM 10.19.248.200:30100/g_gov/centos_jdk:1.8.0_131
MAINTAINER luchaob <luchaob@ennew.cn>
ADD target/LK-market-center-backend-api.jar /opt/
WORKDIR /opt/
CMD java -Dfile.encoding=utf-8  -jar -Duser.timezone="Asia/Shanghai" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -server -Xmx1g -Xms128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=4m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 LK-market-center-backend-api.jar
