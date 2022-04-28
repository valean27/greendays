package com.greendays.greendays.report;

import com.greendays.greendays.model.Client;
import com.greendays.greendays.model.DailyReport;
import com.greendays.greendays.model.Garbage;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

@Component
//TODO: Create this after creating the garbage database
public class DatabaseTestDailyReportGenerator {

    public void generateDailyReportsForAnualReport(){

    }

    public void generateDailyReportsForTrimesters(int trimiesterNumber){

    }

    public void generateDailyReportsForMonths(int monthsNumber){

    }

    public DailyReport generateDailyReportBasedOnDate(LocalDate localDate){
//        DailyReport dailyReport = new DailyReport();
//        dailyReport.setDate(Date.valueOf(localDate));
//        dailyReport.setCarNumber("DummyCarNumber");
//        dailyReport.setDestination("DummyDestination");
//        Client client = new Client();
//        int r = (int) (Math.random()*5);
//        String randomClientType = new String [] {"CASNIC","NONCASNIC","MIXT"}[r];
//        client.setClientType(randomClientType);
//        dailyReport.setClient(client);
//        Garbage garbage = new Garbage();
//        String randomGarbageCode = new String[]{"20 01 01"}
//        garbage.setGarbageCode();
//        dailyReport.setGarbage();
return null;
    }

}
