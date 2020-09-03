package com.lambdaschool.countries.controllers;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.lambdaschool.countries.models.Country;
import com.lambdaschool.countries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController
{
    @Autowired
    CountryRepository countryrepos;

    private List<Country> findUCountries(List<Country> myList, CheckCountry tester)
    {
        List<Country> tempList = new ArrayList<>();
        for(Country c : myList)
        {
            if (tester.test(c))
            {
                tempList.add(c);
            }
        }
        return tempList;
    }


    /* http://localhost:2019/names/all */
    /* http://localhost:2019/names/start/u */

    @GetMapping(value = "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries()
    {
        List <Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

//    http://localhost:2019/names/start/U

    @GetMapping(value = "/names/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> listAllByLetter(@PathVariable char letter)
    {
        List<Country> myList = new ArrayList<>();
        countryrepos.findAll().iterator().forEachRemaining(myList::add);
        List<Country> rtnList = findUCountries(myList, c -> c.getName().charAt(0) == letter);

        for (Country c : rtnList)
        {
            System.out.println(c);
        }

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
}
