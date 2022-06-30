package abc.kraitax.controller;

import abc.kraitax.HTTPSClient2;
import org.springframework.context.annotation.Import;
import org.springframework.ws.soap.SoapMessage;


public class TransactionParser {

    public static void main (String[] args){
        String kraUrl = "https://196.61.52.40/PaymentGateway/KRAPaymentGateway?wsdl";
        String KRASoapMessage = "<soapenv:Envelope\n" +
                "<s11:Envelope xmlns:s11='http://schemas.xmlsoap.org/soap/envelope/'>" +
                "<s11:Body>" +
                "<ns1:checkEslip xmlns:ns1='http://impl.facade.pg.kra.tcs.com/'>"+
                "<loginId>" + login + "</loginId>"+
                "<password>" + password + "</password>"+
                "<eSlipNumber>" + eslipNumber + "</eSlipNumber>"+
                "</ns1:checkEslip>"+
                "</s11:Body>"+
                "</s11:Envelope>";
        String checkEslipResponse = new HTTPSClient2().sendHttpsRequest(KRASoapMessage, kraUrl);

    }

}
