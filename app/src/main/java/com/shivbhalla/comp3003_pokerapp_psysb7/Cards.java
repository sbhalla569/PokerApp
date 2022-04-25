package com.shivbhalla.comp3003_pokerapp_psysb7;

// class to handle card values, for utility purposes
public final class Cards {

    private static int[] cardValues = {
//              CLUBS
            R.drawable.ace_c,
            R.drawable.two_c,
            R.drawable.three_c,
            R.drawable.four_c,
            R.drawable.five_c,
            R.drawable.six_c,
            R.drawable.seven_c,
            R.drawable.eight_c,
            R.drawable.nine_c,
            R.drawable.ten_c,
            R.drawable.jack_c,
            R.drawable.queen_c,
            R.drawable.king_c,
//              SPADES
            R.drawable.ace_s,
            R.drawable.two_s,
            R.drawable.three_s,
            R.drawable.four_s,
            R.drawable.five_s,
            R.drawable.six_s,
            R.drawable.seven_s,
            R.drawable.eight_s,
            R.drawable.nine_s,
            R.drawable.ten_s,
            R.drawable.jack_s,
            R.drawable.queen_s,
            R.drawable.king_s,
//              HEARTS
            R.drawable.ace_h,
            R.drawable.two_h,
            R.drawable.three_h,
            R.drawable.four_h,
            R.drawable.five_h,
            R.drawable.six_h,
            R.drawable.seven_h,
            R.drawable.eight_h,
            R.drawable.nine_h,
            R.drawable.ten_h,
            R.drawable.jack_h,
            R.drawable.queen_h,
            R.drawable.king_h,
//              DIAMONDS
            R.drawable.ace_d,
            R.drawable.two_d,
            R.drawable.three_d,
            R.drawable.four_d,
            R.drawable.five_d,
            R.drawable.six_d,
            R.drawable.seven_d,
            R.drawable.eight_d,
            R.drawable.nine_d,
            R.drawable.ten_d,
            R.drawable.jack_d,
            R.drawable.queen_d,
            R.drawable.king_d
    };

    // Function to get a specific card
    public static int getCard(int cardID) throws IndexOutOfBoundsException{
        if(cardID < 0 || cardID >= 52){
            throw new IndexOutOfBoundsException();
        }
        return cardValues[cardID];
    }

    public static int cardValue(int cardID) throws IndexOutOfBoundsException{
        if(cardID < 0 || cardID >= 52){
            throw new IndexOutOfBoundsException();
        }
        // Returns a value for which equals to a specific value for the card
        return cardID % 13;
    }

    public static int cardSuit(int cardID) throws IndexOutOfBoundsException{
        if(cardID < 0 || cardID >= 52){
            throw new IndexOutOfBoundsException();
        }
        // Returns a value for which equals to a specific suit for the card
        return cardID/13;
    }
}
