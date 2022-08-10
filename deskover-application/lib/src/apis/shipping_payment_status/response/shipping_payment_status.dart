import 'package:json_annotation/json_annotation.dart';
part 'shipping_payment_status.g.dart';

@JsonSerializable()
class Payment {
  int? id;
  String? payment_id;
  String? name_payment;

  Payment({this.id, this.payment_id, this.name_payment});

  factory Payment.fromJson(Map<String, dynamic> json) => _$PaymentFromJson(json);
  Map<String, dynamic> toJson() => _$PaymentToJson(this);

}

@JsonSerializable()
class Shipping {
  int? id;
  String? shippingId;
  String? name_shipping;

  Shipping({this.id, this.shippingId, this.name_shipping});

  factory Shipping.fromJson(Map<String, dynamic> json) => _$ShippingFromJson(json);
  Map<String, dynamic> toJson() => _$ShippingToJson(this);

}