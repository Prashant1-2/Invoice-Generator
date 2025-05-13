export interface InvoiceItem {
  description: string;
  quantity: number;
  rate: number;
}

export interface InvoiceRequest {
  businessName: string;
  clientName: string;
  items: InvoiceItem[];
}