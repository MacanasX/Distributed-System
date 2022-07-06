Einführung:
Im Rahmen des Praktikums (Verteilte Systeme) sollen unterschiedliche Systeme rund um das Thema IoT simuliert werden.
Der Fokus liegt auf der Kommunikation der verteilten Komponenten untereinander.
Jedes Teilsystetem soll unabhängig in einem Docker-Container laufen.


- Es sollen 8 Sensoren (jeder in einem Container) laufen und Daten wie z.B. Temperatur oder Niederschlag generieren.
Die Informationen über den Sensor und die generierten Daten werden in einem Json-String verpackt.
- Sobald das IoT-Gateway ein Pullrequest mittels UDP an die Sensoren sendet, schicken 4 Sensoren ebenfalls über UDP ihre aktuelle Sensordaten an das Gateway (Request-Reply Protokol).
Die anderen 4 Sensoren stellen ihre Daten über ein Message-oriented Middleware-Protokoll (MQTT) zur Verfügung. Hierbei haben wir uns für den Broker von eclipse-mosquitto entschieden.
- Das Gateway sendet mittels TCP einen Http-Post-Request an den Http-Server inkl. Sensordaten.
- Der Server überprüft den Header auf seine Korrektheit und ob der Inhalt der Message ein Json-Objekt ist.
Falls ja, wird der HTTP-Code "200 OK" zurückgeschickt, anonsten der Fehler-Code "404 Not Found".
- Der Server kann mittels Remote Procedure Call (Thrift) mit den Datenbanken kommunizieren.
Bei erfolgreicher Http-Post-Anfrage werden die Daten redundant über ein Zwei-Phasen-Commit-Protokoll gespeichert.

- Um die Laufzeit eines Zyklus zu messen, wird von jeder Message die Round Trip Time auf dem IoT-Gateway berechnet, sowie der Mittelwert über die letzten 10 Messungen.
 Das Log-File wird in einemn shared Folder gespeichert.
- Auf dem IoT-Gateway wird jede 10. und 15. Message eine falsche Http-Post-Anfrage versendet. Als Rückmeldung vom Server erhalten wir den Error-Code 404 Not Found.
Die mitgeschickten Daten werden nicht in der Datenbank gespeichert.


Docker Setup und Deployment:
Installiere die Docker Tools wie hier beschrieben: https://docs.docker.com/engine/install/

Initial Setup
Nachdem Sie das Projekt runtergeladen und entpackt haben, können Sie im Root-Verzeichnis (wo docker-compose.yml liegt) die Powershell starten
und mit folgenden Befehl die Container bauen und deployen: docker-compose up --build

