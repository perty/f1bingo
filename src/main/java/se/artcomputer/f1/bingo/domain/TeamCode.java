package se.artcomputer.f1.bingo.domain;

public enum TeamCode {
    alpine("alpine"),
    astonMartin("aston-martin"),
    audi("audi"),
    cadillac("cadillac"),
    ferrari("ferrari"),
    haas("haas"),
    mclaren("mclaren"),
    mercedes("mercedes"),
    racingBulls("racing-bulls"),
    redBull("red-bull"),
    williams("williams");

    private final String fileName;

    TeamCode(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
