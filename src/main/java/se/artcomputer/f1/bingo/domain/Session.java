package se.artcomputer.f1.bingo.domain;

public enum Session {
    QUALIFYING(3),
    RACE(4),
    SPRINT_RACE(2),
    SPRINT_SHOOTOUT(1);

    private final int sortOrder;

    Session(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getSortOrder() {
        return sortOrder;
    }
}
