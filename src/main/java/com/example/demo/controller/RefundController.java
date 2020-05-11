package com.example.demo.controller;


import com.example.demo.entity.*;
import com.example.demo.services.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.Date;
import java.util.Random;


@Controller
public class RefundController {
    @Autowired
    private InvoiceDetailService invoiceDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RefundInvoiceService refundInvoiceService;

    @Autowired
    private ProductBatchesService productBatchesService;

    @Autowired
    private SellingInvoiceService sellingInvoiceService;

    @RequestMapping(value = "/refund", method = RequestMethod.GET)
    public String refundForm(Model model,
                             @RequestParam(defaultValue = "", required = false) Integer ID){
        SellingInvoiceEntity sellingInvoiceEntity = sellingInvoiceService.findByID(ID);
        model.addAttribute("selling_invoice", sellingInvoiceEntity);
        model.addAttribute("detail", invoiceDetailService.findInvoiceDetailEntityList(sellingInvoiceEntity));
        Date date = new Date();
        if((int)((date.getTime() - sellingInvoiceEntity.getDate().getTime())/(24*60*60*1000)) > 2){
            model.addAttribute("message", "Không thực hiện được giao dịch trả lại");
        }
        return "refund";
}

    @RequestMapping(value = "/refund_page", method = RequestMethod.GET)
    public String refundPage(Model model){
        return "refund";
    }

    @RequestMapping(value = "/save/refund/{id}", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("refund") RefundInvoiceEntity refundInvoiceEntity,
                              @PathVariable(name = "id") Integer id,
                              Model model) {
        SellingInvoiceEntity sellingInvoiceEntity = sellingInvoiceService.findByID(id);
        Date date = new Date();
        Random random = new Random();
        int refund_id_random = 1000000 + random.nextInt(9999999);
        refundInvoiceEntity.setId(refund_id_random);
        refundInvoiceEntity.setDate(date);
        refundInvoiceEntity.setSellingInvoice(sellingInvoiceEntity);
        refundInvoiceService.save(refundInvoiceEntity);
        invoiceDetailService.findInvoiceDetailEntityList(sellingInvoiceEntity);
        model.addAttribute("refund_invoice", refundInvoiceEntity);
        return "selling";
    }

    @RequestMapping(value = "/create/refund/{id}", method = RequestMethod.GET)
    public String createRefund(@PathVariable(name = "id") Integer id,
                               Model model) {
        RefundInvoiceEntity refundInvoiceEntity = new RefundInvoiceEntity();
        SellingInvoiceEntity sellingInvoiceEntity = sellingInvoiceService.findByID(id);
        model.addAttribute("selling_invoice", sellingInvoiceEntity);
        model.addAttribute("refund", refundInvoiceEntity);
        return "refund_create";
    }
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public String product(Model model){
        model.addAttribute("product", productService.listProduct());
        return "product";
    }
}
