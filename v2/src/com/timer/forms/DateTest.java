/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timer.forms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 * @author User
 */
public class DateTest {

    public static boolean isValidTouristSimExpiredDate(String registrationDate, String expiredDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date startDatevalue = (Date) dateFormat.parse(registrationDate);
            Date endDateValue = (Date) dateFormat.parse(expiredDate);
            LocalDate from = startDatevalue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = endDateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            boolean checkAfterReg = from.isBefore(to);
            if (checkAfterReg) {
                Long compareDateDifference = ChronoUnit.DAYS.between(from, to);
                System.out.println("compareDateDifference: " + compareDateDifference);
                if (compareDateDifference <= 30L) {
                    return true;
                }
            }
        } catch (ParseException pe) {
            pe.getMessage();
            return false;
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(isValidTouristSimExpiredDate("16/07/2023 11:17:02", "15/08/2023 10:16:01"));
//    }
}
