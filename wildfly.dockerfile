# docker build -t standalone-wildfly -f wildfly.dockerfile .
# docker exec -it standalone-wildfly bash
# docker run --name standalone-wildfly -it -p 8080:8080 standalone-wildfly


FROM quay.io/wildfly/wildfly

RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#007 --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
