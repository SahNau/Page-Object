package data;

public class CardsInfo {
    private CardsInfo() {
    }

    public static String cardNumber(int number) {
        String[] cards = new String[2];
        cards[0] = "5559 0000 0000 0001";
        cards[1] = "5559 0000 0000 0002";

        return cards[number];
    }

    public static String unavailableCardNumber(int number) {
        String[] cards = new String[2];
        cards[0] = "1111 1111 1111 1111";
        cards[1] = "2222 3333 4444";

        return cards[number];
    }
}