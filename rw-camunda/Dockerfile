FROM camunda/camunda-bpm-platform:7.5.0
MAINTAINER Masaaki Inaba

# Tomcat User Configuration
ADD ./tomcat-users.xml /camunda/conf/

# Tomcat web.xml Configuration
ADD ./web-camunda.xml      /camunda/webapps/camunda/WEB-INF/web.xml
ADD ./web-engine-rest.xml  /camunda/webapps/engine-rest/WEB-INF/web.xml

# Add MariaDB JDBC Driver
ADD http://downloads.mariadb.com/enterprise/22bq-phpn/connectors/java/connector-java-1.4.6/mariadb-java-client-1.4.6.jar /camunda/lib/

# Tomcat server.xml Configuration
RUN xmlstarlet ed -L -a "//Resource[@name='jdbc/ProcessEngine']" -t attr -n "defaultAutoCommit"      -v "false"    /camunda/conf/server.xml
RUN xmlstarlet ed -L -a "//Resource[@name='jdbc/ProcessEngine']" -t attr -n "testOnBorrow"           -v "true"     /camunda/conf/server.xml
RUN xmlstarlet ed -L -a "//Resource[@name='jdbc/ProcessEngine']" -t attr -n "validationQuery"        -v "SELECT 1" /camunda/conf/server.xml
RUN xmlstarlet ed -L -a "//Resource[@name='jdbc/ProcessEngine']" -t attr -n "removeAbandonedTimeout" -v "60"       /camunda/conf/server.xml
RUN xmlstarlet ed -L -a "//Resource[@name='jdbc/ProcessEngine']" -t attr -n "removeAbandoned"        -v "true"     /camunda/conf/server.xml

# Delete Trash
RUN rm -rf /camunda/webapps/examples/ \
           /camunda/webapps/h2/ \
           /camunda/webapps/docs/ \
           /camunda/webapps/ROOT/ \
           /camunda/webapps/host-manager/ \
           /camunda/webapps/camunda-welcome/ \
           /camunda/lib/mysql-connector-java-*.jar \
           /camunda/lib/postgresql-*-jdbc4.jar \
           /camunda/lib/h2-*.jar \
           /camunda/camunda-h2-dbs/

EXPOSE 8080
