package com.example.demo.controller;

import com.example.demo.services.Impl.RefundInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    //Tìm tổng số tiền thu, chi, lãi trong 1 khoảng thời gian
    //Request: time = {"today", "this_week", "this_month"}
    //Response: Object
    @GetMapping(value = "/money",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Money(@RequestParam(name = "time", required = false) String time){
        return ResponseEntity.ok().body(refundInvoiceService.RevenueInTime(time));
    }

}
