# ICS till SQL

Kommandoradsprogram för Java 11 eller senare, utan externa bibliotek. En SQL INSERT
skrivs per VEVENT med kolumnerna `Summary`, `starttime`, `endtime` och `location`.
Programmet ansluter inte till någon databas.

Kör direkt från denna mapp:

```powershell
java IcsToSql.java ..\f12026b.ics
java IcsToSql.java ..\f12026b.ics races
java IcsToSql.java ..\f12026b.ics races output.sql
```

Argument: sökväg till ICS-fil, valfritt tabellnamn (standard `events`), valfri
utfil. Utfilen skrivs som UTF-8 och ersätts om den redan finns. Utan utfil skrivs
SQL till standard output. Hela kalendern tolkas innan något skrivs, så ett
tolkningsfel ger ingen partiell SQL. Fel skrivs till standard error med exitkod 1
(fel antal argument ger exitkod 2).

Exempel på utmatning:

```sql
INSERT INTO races (Summary, starttime, endtime, location) VALUES ('Grand Prix', '2026-11-27 17:00:00', '2026-11-27 18:00:00', 'Qatar');
```

Tabellen behöver finnas. Exempel för SQLite eller PostgreSQL:

```sql
CREATE TABLE races (
    Summary TEXT,
    starttime TIMESTAMP NOT NULL,
    endtime TIMESTAMP,
    location TEXT
);
```

## Tolkning och avgränsningar

- UTF-8, vikta ICS-rader, text-escape (`\n`, `\,`, `\;`, `\\`) och apostrofer
  hanteras. Saknade Summary, DTEND eller LOCATION ger SQL `NULL`.
- DTSTART är obligatoriskt. Datum utan tid (`VALUE=DATE`) skrivs vid midnatt.
  DTEND behåller kalenderns exklusiva sluttid, även för heldagshändelser.
- UTC (`Z`) behålls. Java-kända TZID, exempelvis `Europe/Stockholm`, omvandlas
  till UTC. SQL-tiderna saknar tidszonssuffix. Tider utan tidszon behålls som
  lokala tider; blanda därför inte dessa med UTC utan att bestämma en tidskonvention.
- Okända tidszoner och tvetydiga/obefintliga tider vid sommartidsbyte ger fel.
  Egna tidszonsregler i VTIMEZONE används inte.
- DURATION stöds inte och ger fel; kalendern behöver ange DTEND eller sakna sluttid.
- Återkommande händelser expanderas inte: RRULE, RDATE och EXDATE används inte.
  Varje VEVENT, inklusive eventuella återkomstundantag, blir en separat INSERT.
  Inställda händelser filtreras inte och dubbletter tas inte bort.
- Larm och andra underkomponenter påverkar inte händelsens fält.
- SQL använder vanliga strängliteraler med dubblerade apostrofer, avsedda för
  SQLite/PostgreSQL med standardinställningar. MySQL kräver
  `NO_BACKSLASH_ESCAPES` för att bevara backslash korrekt.
  Tabellnamnet måste vara ett enkelt SQL-namn som inte är ett reserverat ord.

## Kompilera och testa

```powershell
javac -encoding UTF-8 IcsToSql.java IcsToSqlTest.java
java IcsToSqlTest
java IcsToSql ..\f12026b.ics races output.sql
```
