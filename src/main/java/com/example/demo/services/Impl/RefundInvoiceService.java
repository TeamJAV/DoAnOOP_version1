package com.example.demo.services.Impl;


import com.example.demo.entity.InvoiceDetailEntity;
import com.example.demo.entity.RefundInvoiceEntity;
import com.example.demo.entity.SellingInvoiceEntity;
import com.example.demo.repository.RefundInvoiceRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RefundInvoiceService{
    @Autowired
    private RefundInvoiceRepository refundInvoiceRepository;

    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private SellingInvoiceService sellingInvoiceService;

    @Autowired
    private ImportInvoiceService importInvoiceService;

    public RefundInvoiceEntity save(RefundInvoiceEntity refundInvoiceEntity){
         return refundInvoiceRepository.save(refundInvoiceEntity);
    }
    public List<RefundInvoiceEntity> findALL(){
        return refundInvoiceRepository.findAll();
    }
//    public RefundInvoiceEntity findByID(Integer id){
//        return refundInvoiceRepository.findById(id).orElse(null);
//    }
//    public void delete(RefundInvoiceEntity reuRefundInvoiceEntity){ refundInvoiceRepository.delete(reuRefundInvoiceEntity);}

    public void UpdateRefund(JSONArray jsonInvoiceDetail, RefundInvoiceEntity refundInvoiceEntity) throws JSONException {
        for(int i=0; i<jsonInvoiceDetail.length(); i++) {
            int id_of_invoiceDetail = (int) jsonInvoiceDetail.getJSONObject(i).get("id");
            int quantity_refund = (int) jsonInvoiceDetail.getJSONObject(i).get("quantityRefund");
            InvoiceDetailEntity invoiceDetailEntity = invoiceDetailService.findById(id_of_invoiceDetail);
            invoiceDetailEntity.setRefundInvoice(refundInvoiceEntity);
            invoiceDetailEntity.setQuantityRefund(quantity_refund);
            invoiceDetailService.save(invoiceDetailEntity);
            invoiceDetailEntity.getSellingInvoice().setRefunded(true);
            sellingInvoiceService.updateTotalPrice(invoiceDetailEntity.getSellingInvoice().getId());
        }
    }
    public void RefreshRefund(JSONArray jsonInvoiceDetail, RefundInvoiceEntity refundInvoiceEntity) throws JSONException{
        for(int i=0; i<jsonInvoiceDetail.length(); i++){
            int id_of_invoiceDetail = (int) jsonInvoiceDetail.getJSONObject(i).get("id");
            InvoiceDetailEntity invoiceDetailEntity =  invoiceDetailService.findById(id_of_invoiceDetail);
            invoiceDetailEntity.setRefundInvoice(null);
            invoiceDetailEntity.setQuantityRefund(0);
            invoiceDetailEntity.getSellingInvoice().setRefunded(false);
            invoiceDetailService.save(invoiceDetailEntity);
        }
            refundInvoiceRepository.delete(refundInvoiceEntity);
    }
    public boolean CanCreateRefund(SellingInvoiceEntity sellingInvoiceEntity){
        Date date = new Date();
        return (int) ((date.getTime() - sellingInvoiceEntity.getDate().getTime()) / (24 * 60 * 60 * 1000)) <= 2;
    }
    public boolean CheckIsRefund(SellingInvoiceEntity sellingInvoiceEntity){
        return !sellingInvoiceEntity.isRefunded();
    }
    public List<?> RefundTransService(String time, String type){
        switch (time){
            case "today":
                switch (type){
                    case "selling":
                        return sellingInvoiceService.SellingTransToday();
                    case "refund":
                        return refundInvoiceRepository.RefundTransToday();
                    case "import":
                        return importInvoiceService.ImportTransToday();
                }
            case "this_week":
                switch (type){
                    case "selling":
                        return sellingInvoiceService.SellingTransThisWeek();
                    case "refund":
                        return refundInvoiceRepository.RefundTransThisWeek();
                    case "import":
                        return importInvoiceService.ImportTransThisWeek();
                }
            case "this_month":
                switch (type){
                    case "selling":
                        return sellingInvoiceService.SellingTransThisMonth();
                    case "refund":
                        return  refundInvoiceRepository.RefundTransThisMonth();
                    case "import":
                        return importInvoiceService.ImportTransThisMonth();
                }

        }
        return null;
    }
    public List<Map<String, Object>> RevenueInTime(String time){
        switch (time){
            case "today":
                    return sellingInvoiceService.RevenueToday();
            case "this_week":
                    return sellingInvoiceService.RevenueThisWeek();
            case "this_month":
                    return sellingInvoiceService.RevenueThisMonth();
        }
        return null;
    }
}
