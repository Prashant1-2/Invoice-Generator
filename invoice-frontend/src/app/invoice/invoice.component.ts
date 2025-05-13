import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InvoiceService } from './invoice.service';
import { InvoiceItem, InvoiceRequest } from './invoice.model';

@Component({
  selector: 'app-invoice',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.css'],
  providers: [InvoiceService]
})
export class InvoiceComponent {
  invoice: InvoiceRequest = {
    businessName: '',
    clientName: '',
    items: [{ description: '', quantity: 1, rate: 0 }]
  };

  constructor(private service: InvoiceService) {}

  addItem() {
    this.invoice.items.push({ description: '', quantity: 1, rate: 0 });
  }

  removeItem(index: number) {
    this.invoice.items.splice(index, 1);
  }

  submit() {
    this.service.generateInvoice(this.invoice).subscribe(blob => {
      const a = document.createElement('a');
      const objectUrl = URL.createObjectURL(blob);
      a.href = objectUrl;
      a.download = 'invoice.pdf';
      a.click();
      URL.revokeObjectURL(objectUrl);
    });
  }
}