Einführung:
Im Rahmen des Praktikums (Verteilte Systeme) sollen unterschiedliche Systeme rund um das Thema IoT simuliert werden.
Der Fokus liegt auf der Kommunikation der verteilten Komponenten untereinander.
Jedes Teilsystetem soll unabhängig in einem Docker-Container laufen.

- Es sollen 8 Sensoren (jeder in einem Container) laufen und Daten generieren.
- Das IoT-Gateway sendet ein Pullrequest an die Sensoren. 4 Sensoren schicken über UDP (Request-Reply Protokol) ihre aktuelle Sensordaten an das Gateway
und die anderen 4 Sensoren stellen ihre Daten über ein Message-oriented Middleware-Protokoll (MQTT) zur Verfügung. Hierbei haben wir uns für den Adapter von eclipse-mosquitto entschieden.
- Nun sendet das Gateway mittels TCP einen Http-Post-Request an den Http-Server.
- Der Server überprüft den Header auf seiner korrektheit und ob der Inhalt der Message ein Json-Objekt ist.
Falls ja, wird ein HTTP-Code 200 OK zurückgeschickt, anonsten einen Fehler-Code 404 Not Found.
- Der Server kann mittels Remote Procedure Call (Thrift) mit den Datenbanken kommunizieren.
Die Daten werden redundant über ein Zwei-Phasen-Commit-Protokoll gespeichert.
- Um die Laufzeit eines Zyklus zu messen, bestimmen von jeder Message die RTT und speichern sie in einen shared Folder.

Docker Setup und Deployment:

Installiere die Docker Tools wie hier beschrieben: https://docs.docker.com/engine/install/

Initial Setup

Nachdem Sie das Projekt runtergeladen und entpackt habt, können Sie im Root-Verzeichnis (wo docker-compose.yml liegt) die Powershell starten
und mit folgenden Befehl die Container bauen und deployen: docker-compose up --build

