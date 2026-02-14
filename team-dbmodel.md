```plantuml
@startuml
Team : String name  
Team : String shortName
Team : String tag
Team : Enum Country
Team : String carImageUrl()
Team -- Engine  
Team -- Contract
Contract -- Person
Person <|-- Driver
Person <|-- Principal
Person : String name
Driver : Integer number
Driver : Enum Nationality
@enduml
