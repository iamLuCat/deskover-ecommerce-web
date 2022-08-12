export class OrderContants {
  static readonly PENDING_CONFIRM = 'C-XN';
  static readonly PENDING_CANCEL = 'C-HUY';
  static readonly CANCELED = 'HUY';
  static readonly REFUNDED = 'D-HT';
  static readonly NOT_REFUNDED = 'C-HT';
  static readonly UNPAID = 'C-TT';
  static readonly PAID = 'D-TT';
  static readonly DELIVERED = 'GH-TC';
}
