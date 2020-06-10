package com.example.demo.controller;

import com.example.demo.entity.RefundInvoiceEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.services.InvoiceDetailService;
import com.example.demo.services.RefundInvoiceService;
import com.example.demo.services.SellingInvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/refund")
public class ApiControllerRefund {
    @Autowired
    private SellingInvoiceService sellingInvoiceService;

    @Autowired
    private RefundInvoiceService refundInvoiceService;

    @Autowired
    private InvoiceDetailService invoiceDetailService;


    //Tìm tất cả các hóa đơn bán
    //Response: List<SellingInvoiceEntity>
    @GetMapping(value = "/selling_invoice",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findSellingAll(){
        List<SellingInvoiceEntity> sellingInvoiceEntityList = sellingInvoiceService.findList();
        return new ResponseEntity<>(sellingInvoiceEntityList, HttpStatus.OK);
    }

    //Tìm kiếm hóa đơn bán theo id
    //Request: id
    //Response: SellingInvoiceEntity
    @GetMapping(value = "/selling_invoice/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> findSellingByID(@PathVariable("id") Integer id){
        try{
            SellingInvoiceEntity sellingInvoiceEntity = sellingInvoiceService.findByID(id);
            return ResponseEntity.ok().body(sellingInvoiceEntity);
        }catch (EntityNotFoundException ex){
            return ResponseEntity.unprocessableEntity().body(ex);
        }
    }
    //Kiểm tra hóa đơn bán có đủ điều kiện trả lại
    //Request: id
    //Response: SellingInvoiceEntity ? SellingInvoiceEntity : Message("Can't not create refund invoice because of expiring")
    //tra 1 lan
    @GetMapping(value = "/create_refund_by/{id}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?> toMakeRefund(@PathVariable("id") Integer id){
        SellingInvoiceEntity sellingInvoiceEntity =  sellingInvoiceService.findByID(id);
        Map<String, String> map = new HashMap<String, String>();
        if(sellingInvoiceEntity == null){
            map.put("Error", "Don't have it");
            return ResponseEntity.badRequest().body(map);
        }else if (!refundInvoiceService.CanCreateRefund(sellingInvoiceEntity)){
            return new ResponseEntity<>(sellingInvoiceEntity, HttpStatus.OK);
        }else if (!refundInvoiceService.CheckIsRefund(sellingInvoiceEntity)){
            map.put("Warning", "Can't not create refund invoice because it already exists");
            return ResponseEntity.ok().body(map);
        }else{
            map.put("Warning", "Can't not create refund invoice because of expiring");
            return ResponseEntity.badRequest().body(map);
        }
    }

    //Tìm kiếm tất cả hóa đơn trả lại
    //Response: List<RefundInvoiceEntity>
    @GetMapping(value = "/refund_invoice",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findRefund(){
        List<RefundInvoiceEntity> refundInvoiceEntityList = refundInvoiceService.findALL();
        return new ResponseEntity<>(refundInvoiceEntityList, HttpStatus.OK);
    }

    //Tạo hóa đơn trả lại
    //Request: RefundInvoiceEntity, List<InvoiceDetailEntity>
    //Response: RefundInvoiceEntity
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRefundInvoice(@RequestBody String jsonString )throws JSONException, JsonProcessingException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject jsonRefund = jsonObject.getJSONObject("refundInvoice");
        JSONArray jsonInvoiceDetail = jsonObject.getJSONArray("invoiceDetail");
        ObjectMapper objectMapper = new ObjectMapper();
        RefundInvoiceEntity refundInvoiceEntity = objectMapper.readValue(jsonRefund.toString(), RefundInvoiceEntity.class);
        try{
            refundInvoiceService.save(refundInvoiceEntity);
            refundInvoiceService.UpdateRefund(jsonInvoiceDetail, refundInvoiceEntity);
            return  ResponseEntity.ok().body(refundInvoiceEntity);
        }catch (DataAccessException e){
            refundInvoiceService.RefreshRefund(jsonInvoiceDetail, refundInvoiceEntity);
            return ResponseEntity.unprocessableEntity().body(e.getCause().getMessage());
        }
    }

    @PostMapping(value = "/demo")
    public ResponseEntity<?> demo(@RequestBody RefundInvoiceEntity refundInvoiceEntity){
        return ResponseEntity.ok().body(refundInvoiceService.save(refundInvoiceEntity));
    }
}
