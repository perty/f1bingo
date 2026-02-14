package se.artcomputer.f1.bingo.domain;

public enum Country {
    abuDhabi("abu-dhabi"),
    argentina("argentina"),
    australia("australia"),
    austria("austria"),
    azerbaijan("azerbaijan"),
    bahrain("bahrain"),
    belgium("belgium"),
    brazil("brazil"),
    canada("canada"),
    china("china"),
    denmark("denmark"),
    finish("finish"),
    finland("finland"),
    france("france"),
    germany("germany"),
    greatBritain("great-britain"),
    hungary("hungary"),
    italy("italy"),
    japan("japan"),
    mexico("mexico"),
    monaco("monaco"),
    netherlands("netherlands"),
    newZealand("new-zealand"),
    qatar("qatar"),
    saudiArabia("saudi-arabia"),
    singapore("singapore"),
    spain("spain"),
    switzerland("switzerland"),
    thailand("thailand"),
    usa("usa");

    private final String fileName;

    Country(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
