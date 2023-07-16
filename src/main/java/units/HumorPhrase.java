package units;

public enum HumorPhrase {
    phrase1("Нет ничего важнее семьи"),phrase2("Пинту пива мне!"),phrase3("Когда зарплата?"),phrase4("А что ты тут делаешь?"),
    phrase5("Аквадискотека!"),phrase6("Вот ну что ты наделал-то? Опять переделывать всё..."),
    phrase7("Блэкджек и шлюх, пожалуйста!"),phrase8("Вы продоёте рыбов?"),phrase9("Я не могу ходить, у меня стрела в колене!"),
    phrase10("Как тебе такое, Елон Таск??"),phrase11("Я сначала не понял, а потом как снова не понял..."),
    phrase12("Заклинание Го Бухатус!"),phrase13("Мне не понятно! Пойду посплю...");
    String phrase;
    HumorPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getHumorPhrase() {
        return phrase;
    }
}