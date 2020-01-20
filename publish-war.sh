cd /Users/lbx/dev/server/project/doraemon
./gradlew bootWar
mv ./app/build/libs/app-0.0.1-SNAPSHOT.war ./app/build/libs/doraemon-0.0.1-SNAPSHOT.war
scp -P 22 ./app/build/libs/doraemon-0.0.1-SNAPSHOT.war root@49.235.52.159:/opt/apache-tomcat-8.5.45/webapps/doraemon.war
