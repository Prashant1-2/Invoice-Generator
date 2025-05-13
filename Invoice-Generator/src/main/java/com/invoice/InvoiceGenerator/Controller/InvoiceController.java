package com.invoice.InvoiceGenerator.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.invoice.InvoiceGenerator.Model.InvoiceRequest;
import com.invoice.InvoiceGenerator.Service.PdfService;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin
public class InvoiceController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) {
        byte[] pdfBytes = pdfService.generateInvoicePdf(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("Invoice.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}