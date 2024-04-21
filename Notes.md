# Notes

## To Do

- [x] Hamilton kraschar inte in i Verstappen.
- [x] Typo, "starter".
- [ ] Chrome pops up Google search when touching text. Add to user manual how to turn off.
- [ ] Check how it looks on iPhone.
- [ ] Fall back for failing to support websocket.
- [X] Admin can disable a statement.

## Concepts

| Swedish     | English         | Comment                                    |
|-------------|-----------------|--------------------------------------------|
| Racehelg    | Race weekend    |                                            |
| Aktivitet   | Session         |                                            |
| Helgtyp     | Weekend type    | Classic or Sprint                          |
| Bingobricka | Bingo card      | One per session                            |
| Helgpalett  | Weekend palette | A set of cards for a weekend               |
| Påstående   | Statement       | Something that can happen during a session |
| Utfall      | Result          |                                            |
| Användare   | Fan             |                                            |

A user can have one palette per race weekend.
A palette can have one or more bingo cards,
depending on the type of race weekend.

A statement can either be true or false for a given
part of a race weekend, that is called a result.

A statement is suitable for one or more of the following sessions:
qualification, race, sprint shootout and sprint race.

A bingo card can have 16 statements, arranged in a
4x4 grid.

Given a user and a race weekend, there is a palette of bingo cards.

No login is required as a user can have only one palette. 
