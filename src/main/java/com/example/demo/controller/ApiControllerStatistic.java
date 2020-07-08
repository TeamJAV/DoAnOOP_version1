package com.example.demo.controller;

import com.example.demo.services.RefundInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/statistic")
public class ApiControllerStatistic {
    @Autowired
    private RefundInvoiceService refundInvoiceService;


    //Tìm các giao dịch trong 1 khoảng thời gian lựa chọn
    //Request: time = {"today", "this_week", "this_month"}
    //         type = {"selling", "refund", "import"}
    //Response: ListObject
    @GetMapping(value = "/trans",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetTransaction(@RequestParam(name = "time", required = false) String time,
                                            @RequestParam(name = "type", defaultValue = "selling", required = false) String type){
        if (time == null){
            return null;
        }
        return ResponseEntity.ok().body(refundInvoiceService.RefundTransService(time, type));
    }
    //Tìm tổng số tiền thu, chi, lãi trong 1 khoảng thời gian,( lãi: số tiền sẽ >0, lỗ: số tiền <=0)
    //Request: time = {"today", "this_week", "this_month"}
    //          from = '2020-07-01'
    //          to = '2020-07-08'
    //Response: Object
    @GetMapping(value = "/money",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Money(@RequestParam(name = "time", required = false) String time,
                                   @RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                   @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate){
        Map<String, String> map = new HashMap<String, String>();
        if(fromDate == null && toDate == null && time == null){
            return null;
        }
        else if (fromDate == null && toDate == null){
            return ResponseEntity.ok().body(refundInvoiceService.RevenueInTime(time));
        }
        else if (time ==  null){
            return ResponseEntity.ok().body(refundInvoiceService.RevenueInSpecificTime(fromDate, toDate));
        }
        return ResponseEntity.badRequest().body(map.put("messages", "Error"));
    }

    //Tìm các giao dịch trong 1 khoảng thời gian lựa chọn cụ thể
    //Request: from
    //         to
    //Response: Object
    @GetMapping(value = "/specific/trans",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> GetTransactionInSpecific(@RequestParam(name = "from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                                      @RequestParam(name = "to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                                                      @RequestParam(name = "type") String  type){
        if (fromDate != null && toDate != null){
            return ResponseEntity.ok().body(refundInvoiceService.RefundTransServiceInSpecificTime(fromDate, toDate, type));
        }
        return null;
    }
}
