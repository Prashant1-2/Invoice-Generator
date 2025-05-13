import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InvoiceRequest } from './invoice.model';

@Injectable()
export class InvoiceService {
  private baseUrl = 'http://localhost:8080/api/invoice';

  constructor(private http: HttpClient) {}

  generateInvoice(data: InvoiceRequest) {
    return this.http.post(this.baseUrl + '/generate', data, {
      responseType: 'blob'
    });
  }
}