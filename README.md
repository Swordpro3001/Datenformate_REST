### Wahl-Daten-Anzeige mit Spring Boot

---

### Architekturübersicht

1. **Spring Boot Backend**:
    - Stellt eine REST-API zur Verfügung, die Wahldaten aus einer Datenquelle oder durch eine Simulation bereitstellt.
    - Die API kann sowohl **XML** als auch **JSON** ausliefern, wobei in diesem Fall das **XML-Format** verwendet wird.
2. **Frontend**:
    - Nutzt das **Bulma CSS-Framework** für die Gestaltung und **jQuery** für das Abrufen und Anzeigen der Wahldaten.

### Technologien

- **Spring Boot**: Java-Framework für die schnelle Entwicklung von Anwendungen mit eingebetteten Webservern und REST-APIs.
- **RESTful API**: Bereitstellung von Wahldaten als XML.
- **Bulma**: CSS-Framework für responsives Design.
- **jQuery**: AJAX für die dynamische Datenanzeige auf der Webseite.

---

### Backend-Architektur mit Spring Boot

### 1. **API-Endpunkt zur Bereitstellung von Wahldaten**

Die REST-API in der Spring Boot Anwendung dient dazu, die Wahldaten basierend auf der übergebenen **regionalID** als XML zu liefern. Hierfür habe ich einen Controller implementiert, der die Anfragen an diesen Endpunkt verarbeitet.

### **Beispiel-Controller (ElectionDataController.java)**

```java
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wahlen/data")
public class ElectionDataController {

    private final ElectionDataService electionDataService;

    public ElectionDataController(ElectionDataService electionDataService) {
        this.electionDataService = electionDataService;
    }

    // API-Endpunkt zum Abrufen von Wahldaten im XML-Format
    @GetMapping(value = "/{regionID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ElectionData getElectionDataInXml(@PathVariable("regionID") String regionID) {
        return electionDataService.getElectionDataByRegion(regionID);
    }
}
```

- **`@RestController`**: Stellt sicher, dass der Controller als REST-Endpunkt dient.
- **`@RequestMapping("/wahlen/data")`**: Definiert den Basis-Endpunkt für alle Anfragen, die Wahldaten betreffen.
- **`@GetMapping("/{regionID}/xml")`**: Dieser Endpunkt liefert die Wahldaten im XML-Format, wobei die **regionID** als Pfadvariable übergeben wird.
- **`produces = MediaType.APPLICATION_XML_VALUE`**: Die API gibt die Daten als **XML** zurück.

### 2. **Datenmodell**

- **`ElectionData`**: Repräsentiert die Wahldaten einer bestimmten Region.
- **`CountingData`**: Repräsentiert die Daten für eine einzelne Partei, einschließlich der **Vorzugsstimmen**.
- **`PreferenceVotes`**: Repräsentiert die Vorzugsstimmen für einzelne Kandidaten.

---

### API-Aufruf im XML-Format

Die API gibt die Wahldaten im XML-Format aus, z. B. durch den Aufruf:

```
GET <http://localhost:8080/wahlen/data/33123/xml>
```

---

### 3. **AJAX-Request zur API**

In der Anwendung wird **jQuery** verwendet, um einen **AJAX-Request** an den Endpunkt **`/wahlen/data/{regionID}/xml`** zu senden. Hierdurch wird sichergestellt, dass die Wahldaten der entsprechenden **regionID** vom Server abgerufen werden, ohne dass die Seite neu geladen werden muss.

### Code-Snippet: AJAX-Request für den Datenaustausch

- **`$.ajax`**: Eine jQuery-Methode für asynchrone HTTP-Anfragen. In diesem Fall wird eine GET-Anfrage an den angegebenen **URL-Endpunkt** gesendet.
- **`dataType: "xml"`**: Gibt an, dass die erwartete Antwort im **XML-Format** zurückgegeben wird.
- **`success`**: Wird ausgeführt, wenn der Server die Anfrage erfolgreich beantwortet. Hier wird die Antwort (die XML-Daten) verarbeitet und in die Tabelle eingefügt.
- **`error`**: Wird ausgeführt, wenn ein Fehler bei der Anfrage auftritt (z. B. wenn die regionalID nicht existiert).

---

### 4. **Verarbeitung der XML-Daten und DOM-Manipulation**

Sobald die XML-Daten von der API empfangen wurden, muss der Inhalt der Antwort in einer HTML-Tabelle angezeigt werden. Hierbei wird die XML-Struktur mit jQuery durchlaufen und in **`<tr>`-Elemente** (Tabellenzeilen) umgewandelt.

### Code-Snippet: Datenverarbeitung und Tabellenmanipulation

```jsx
$(data).find("countingData > countingData").each(function () {
    const partyID = $(this).find("partyID").text();  // Partei-ID
    const amountVotes = $(this).find("amountVotes").text();  // Stimmenanzahl
    let preferenceVotes = [];

    // Schleife über Vorzugsstimmen
    $(this).find("preferenceVotes > preferenceVotes").each(function () {
        const candidateName = $(this).find("candidateName").text();
        const votes = $(this).find("votes").text();
        preferenceVotes.push(candidateName + " (" + votes + ")");
    });

    // Neue Tabellenzeile erstellen
    const row = `<tr>
        <td>${partyID}</td>
        <td>${amountVotes}</td>
        <td>${preferenceVotes.join(", ")}</td>
    </tr>`;

    // Zeile in die Tabelle einfügen
    tableBody.append(row);
});
```

- **`$(data).find("countingData > countingData")`**: Durchläuft die XML-Struktur, um jedes **`countingData`**Element zu finden, das Informationen über eine Partei enthält.
- **`partyID`** und **`amountVotes`**: Entnimmt die Partei-ID und die Anzahl der Stimmen aus der XML-Antwort.
- **`preferenceVotes.push()`**: Fügt die Vorzugsstimmen eines Kandidaten dem Array hinzu, das später in einer Tabelle angezeigt wird.
- **`tableBody.append(row)`**: Fügt die erstellte Zeile in die Tabelle ein.

### 5 **Suchfunktion für die Tabelle**

Eine wichtige Funktion der Anwendung ist die Möglichkeit, die Daten in der Tabelle anhand von Suchbegriffen zu filtern. Dies geschieht, ohne eine neue Anfrage an den Server zu senden, sondern nur durch Filtern der bereits angezeigten Daten.

### Code-Snippet: Suchfunktion

```jsx
$("#searchInput").on("keyup", function () {
    const value = $(this).val().toLowerCase();  // Benutzereingabe in Kleinbuchstaben umwandeln
    $("#electionTable tbody tr").filter(function () {
        // Die Tabelle wird nach Zeilen durchsucht, die den Suchbegriff enthalten
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1);
    });
});
```

- **`$("#searchInput").on("keyup")`**: Überwacht das **Eingabefeld**, sodass bei jedem Tastendruck die Tabelle neu gefiltert wird.
- **`filter()`**: Iteriert durch jede Zeile der Tabelle und überprüft, ob der Suchbegriff in der Zeile vorkommt.
- **`toggle()`**: Blendet die Zeile ein oder aus, basierend darauf, ob der Suchbegriff enthalten ist.
