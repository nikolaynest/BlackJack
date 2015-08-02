package com.exam.blackjack.card;

/**
 * Created by nikolay on 26.07.15.
 */
public class CardOwner {


    private Card card;
    private boolean visible;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CardOwner(Card card, boolean visible) {
        this.card = card;
        this.visible = visible;
    }
}
