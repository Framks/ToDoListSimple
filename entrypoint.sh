#!/bin/bash

# Garante que o diretório do PID do Zabbix Agent existe e tem permissão correta
mkdir -p /var/run/zabbix
chown zabbix:zabbix /var/run/zabbix 2>/dev/null || true

# Garante que o diretório de log do Zabbix Agent existe e tem permissão correta
mkdir -p /var/log/zabbix
chown zabbix:zabbix /var/log/zabbix 2>/dev/null || true

# inicia o app java em background
java \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.port=9010 \
  -Dcom.sun.management.jmxremote.rmi.port=9010 \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -Dcom.sun.management.jmxremote.host=0.0.0.0 \
  -Djava.rmi.server.hostname=localhost \
  -jar /app/app.jar &

# inicia o zabbix agent
zabbix_agentd -f