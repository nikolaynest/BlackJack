package com.exam.blackjack.rest.controller;

import com.exam.blackjack.core.CoreManager;
import com.exam.blackjack.rest.container.request.*;
import com.exam.blackjack.rest.container.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created on 25.07.15.
 */
@RestController
@RequestMapping(value = "/blackjack",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class AppController {

    private CoreManager manager;

    @Autowired
    public AppController(CoreManager manager) {
        this.manager = manager;
    }

    @RequestMapping(value = "/account", method = POST)
    public AccountResponse accountAccess(@RequestBody AccountRequest request) {
        return manager.getAccount(request);
    }

    @RequestMapping(value = "/recharge", method = POST)
    public AvailableCashResponse rechargeBalance(@RequestBody RechargeRequest request) {
        return manager.recharge(request);
    }

    //делать ставку
    @RequestMapping(value = "/parlay", method = POST)
    public ParlayResponse makeRate(@RequestBody ParlayRequest request) {
        return manager.makeRate(request);
    }

//    @RequestMapping(value = "/blackjack", method = POST)
//    public AvailableCashResponse blackJack(BlackJackRequest request) {
//        return manager.blackJack(request);
//    }

    @RequestMapping(value = "/hit", method = POST)
    public HitResponse hit(HitRequest request) {
        return manager.hit(request);
    }

    @RequestMapping(value = "/bust", method = POST)
    public BustResponse bust(SubtractRequest request) {
        return manager.bust(request);
    }

}
