# Appointment Calendar

A calendar application that allows users to add and remove appointments.

The appointments slots are between 8AM and 5PM on weekdays. The slots are 15 minutes, or 1 hour in duration.

# How to checkout #
```
git clone https://github.com/jtoews32/scheduler.git
```
# How to build #
```
cd scheduler/
./gradlew clean build
```
# How to start #
```
java -jar build/libs/scheduler-0.0.1-SNAPSHOT.jar
```
# How to view #
```
http://localhost:8080/

```
# If something goes wrong. System requirements #
```
Have internet access. The webpage uses Javascript libraries like Axios, Bootstrap, and jQuery that are hosted.
Java, and Gradle must be installed.
For H2 database persistence between restarts "~/" must be writable so ~/demo can be written.
See configuration below for in memory option.
```
# How to make  DB persistant 

While typically an in-memory database, H2 can be used in persistance mode.
To switch between in-memory and persistance mode change the below. Remove ";", or add ";".

scheduler/src/main/resources/application.properties
```
;spring.datasource.url=jdbc:h2:file:~/demo;DB_CLOSE_ON_EXIT=FALSE;IFEXISTS=FALSE;DB_CLOSE_DELAY=-1;
;spring.jpa.hibernate.ddl-auto = update
;spring.h2.console.enabled=true
;spring.h2.console.path=/h2-console
;spring.h2.console.settings.trace=false
;spring.h2.console.settings.web-allow-others=false
```
H2 Console URL
```
http://localhost:8080/h2-console
user: sa
password: password
```

# Local environment settings of the original build system #

The frontend was tested using Firefox.
The serverside was built on a Linux machine using the following software.

## Java
```
java -version
java version "1.8.0_201"
Java(TM) SE Runtime Environment (build 1.8.0_201-b09)
Java HotSpot(TM) 64-Bit Server VM (build 25.201-b09, mixed mode)
```

## Gradle
```
gradle -v
------------------------------------------------------------
Gradle 3.3
------------------------------------------------------------

Build time:   2017-01-03 15:31:04 UTC
Revision:     075893a3d0798c0c1f322899b41ceca82e4e134b

Groovy:       2.4.7
Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
JVM:          1.8.0_201 (Oracle Corporation 25.201-b09)
OS:           Linux 4.15.0-99-generic amd64
```

## Maven
```
mvn --version
Apache Maven 3.3.9
Maven home: /usr/share/maven
Java version: 1.8.0_201, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-8-oracle/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "4.15.0-99-generic", arch: "amd64", family: "unix"
```

## OS
```
cat /etc/os-release
NAME="Ubuntu"
VERSION="16.04.6 LTS (Xenial Xerus)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 16.04.6 LTS"
VERSION_ID="16.04"
HOME_URL="http://www.ubuntu.com/"
SUPPORT_URL="http://help.ubuntu.com/"
BUG_REPORT_URL="http://bugs.launchpad.net/ubuntu/"
VERSION_CODENAME=xenial
UBUNTU_CODENAME=xenial

```
