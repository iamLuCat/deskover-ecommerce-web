
import 'package:deskover_develop/src/apis/shipping_payment_status/response/shipping_payment_status.dart';
import 'package:json_annotation/json_annotation.dart';
part 'order_resquest.g.dart';

@JsonSerializable()
class OrderResquest{
    Payment? payment;
    Shipping? shipping;
    String? note;
    double? fee;
    OrderResquest({
      this.payment,
      this.shipping,
      this.note,
      this.fee
    });
    factory OrderResquest.fromJson(Map<String, dynamic> json) => _$OrderResquestFromJson(json);
    Map<String, dynamic> toJson() => _$OrderResquestToJson(this);
}