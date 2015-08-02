package com.exam.blackjack.rest.container.response;

import com.exam.blackjack.card.Card;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by nikolay on 25.07.15.
 */
@XmlRootElement(name = "firstResponse")
public class FirstCardsResponse {

    List<Card> cards;


}
