package se.artcomputer.f1.bingo.controller;

import se.artcomputer.f1.bingo.domain.Session;

public record ResetConfirmData(ResetState state,
                               String gp,
                               Session session,
                               long cards) {
}
