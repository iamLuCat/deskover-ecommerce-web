import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/apis/shipping_payment_status/response/shipping_payment_status.dart';
import 'package:deskover_develop/src/apis/user/response/user_response.dart';
import 'package:json_annotation/json_annotation.dart';
part 'order_response.g.dart';

@JsonSerializable()
class OrderReponse {
  int? id;
  String? orderCode;
  String? qrCode;
  String? note;
  String? shippingNote;
  String? fullName;
  String? email;
  String? createdAt;
  String? modifiedBy;
  double? unitPrice;
  int? orderQuantity;
  String? label;
  double? fee;
  String? estimatedPickTime;
  String? estimatedDeliverTime;
  User? user;
  Payment? payment;
  Shipping? shipping;
  OrderStatus? orderStatus;
  OrderDetail? orderDetail;
  List<Products>? products;
  OrderStatusPayment? statusPayment;

  OrderReponse(
      {this.id,
        this.orderCode,
        this.qrCode,
        this.note,
        this.shippingNote,
        this.fullName,
        this.email,
        this.createdAt,
        this.modifiedBy,
        this.unitPrice,
        this.orderQuantity,
        this.label,
        this.fee,
        this.estimatedPickTime,
        this.estimatedDeliverTime,
        this.user,
        this.payment,
        this.shipping,
        this.orderStatus,
        this.orderDetail,
        this.products,
        this.statusPayment});

  factory OrderReponse.fromJson(Map<String, dynamic> json) => _$OrderReponseFromJson(json);
  Map<String, dynamic> toJson() => _$OrderReponseToJson(this);
}


@JsonSerializable()
class OrderStatus {
  int? id;
  String? code;
  String? status;

  OrderStatus({this.id, this.code, this.status});

  factory OrderStatus.fromJson(Map<String, dynamic> json) => _$OrderStatusFromJson(json);
  Map<String, dynamic> toJson() => _$OrderStatusToJson(this);

}

@JsonSerializable()
class OrderStatusPayment {
  int? id;
  String? code;
  String? status;

  OrderStatusPayment({this.id, this.code, this.status});

  factory OrderStatusPayment.fromJson(Map<String, dynamic> json) => _$OrderStatusPaymentFromJson(json);
  Map<String, dynamic> toJson() => _$OrderStatusPaymentToJson(this);
}

@JsonSerializable()
class OrderDetail {
  int? id;
  String? address;
  String? province;
  String? district;
  String? ward;
  String? tel;

  OrderDetail(
      {this.id,
        this.address,
        this.province,
        this.district,
        this.ward,
        this.tel});

  factory OrderDetail.fromJson(Map<String, dynamic> json) => _$OrderDetailFromJson(json);
  Map<String, dynamic> toJson() => _$OrderDetailToJson(this);
}
@JsonSerializable()
class Products {
  int? id;
  int? quantity;
  double? price;
  Product? product;
  Products({this.id, this.quantity, this.price, this.product});
  factory Products.fromJson(Map<String, dynamic> json) => _$ProductsFromJson(json);
  Map<String, dynamic> toJson() => _$ProductsToJson(this);

}
