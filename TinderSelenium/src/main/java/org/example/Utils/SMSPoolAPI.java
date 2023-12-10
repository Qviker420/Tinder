package org.example.Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Data.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSPoolAPI {
    Data data = new Data();
    Response resp;

    public SMSPoolAPI makeOrder()
    {
        resp = RestAssured.get(String.format("https://daisysms.com/stubs/handler_api.php?api_key=%s&action=getNumber&service=oi&max_price=1.0", data.apiKey));
        if(resp.toString().contains("ACCESS_NUMBER"))
        {
            System.out.println("Creating Order");
            responseParser();
            System.out.println("Order Created Successfully");
        }
        else
        {
            System.out.println("Something wrong with making Order");
            System.out.println(resp.asString());
        }
        return this;
    }
    public SMSPoolAPI getBalance()
    {
        resp = RestAssured.get(String.format("https://daisysms.com/stubs/handler_api.php?api_key=%s&action=getBalance", data.apiKey));
        System.out.println(resp.asString());
        return this;
    }

    public SMSPoolAPI responseParser()
    {
        Pattern pattern = Pattern.compile("ACCESS_NUMBER:(\\d+):(\\d+)");

        Matcher matcher = pattern.matcher(resp.asString());
        if (matcher.find()) {
            data.setID(matcher.group(1));

            data.setPhoneNumber(matcher.group(2));

            System.out.println("Phone Number: " + data.phoneNumber);
            System.out.println("ID: " + data.ID);
        } else {
            System.out.println("Invalid Response");
        }
        return this;
    }
    public SMSPoolAPI cancelOrder()
    {
        resp = RestAssured.get(String.format("https://daisysms.com/stubs/handler_api.php?api_key=%s&action=setStatus&id=%s&status=6", data.apiKey, data.ID));
        System.out.println("canceling order:" + resp.asString());
        return this;
    }
    public SMSPoolAPI waitForPinCode() throws InterruptedException {
        int timeCounter=0;
        resp = RestAssured.get(String.format("https://daisysms.com/stubs/handler_api.php?api_key=%s&action=getStatus&id=%s", data.apiKey, data.ID));
        while (resp.asString().contains(" STATUS_OK")==false)
        {
            if(timeCounter<10)
            {
                resp = RestAssured.get(String.format("https://daisysms.com/stubs/handler_api.php?api_key=%s&action=getStatus&id=%s", data.apiKey, data.ID));
                System.out.println(resp.asString());
                Thread.sleep(3000);
                timeCounter++;
            }
            else
            {
                System.out.println("Time Out Canceling Order");
                cancelOrder();
                break;
            }
        }
        data.pinCode = resp.asString().split(":")[1];
        System.out.println(resp.asString());
        return this;
    }
}
