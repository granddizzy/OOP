package units;

public enum NormalPhrase {
    nphrase1("В бой!"), nphrase2("Надо подумать..."),nphrase3("Я знаю короткую дорогу!"),nphrase4("Сейчас тебе будет больно"),
    nphrase5("Я бегу!"),nphrase6("Отряд, держись!"),nphrase7("Я умру героем!"),nphrase8("Не отступать!"),
    nphrase9("Под ногами не мешайся!"),nphrase10("Моя честь всегда со мной!");
    String phrase;
    NormalPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getNormalPhrase() {
        return phrase;
    }
}
