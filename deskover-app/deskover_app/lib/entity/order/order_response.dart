import 'package:json_annotation/json_annotation.dart';

part 'order_response.g.dart';

@JsonSerializable()
class OrderReponseData{

  @JsonKey(name: 'data')
  OrderReponse? data;

  OrderReponseData({this.data});

  factory OrderReponseData.fromJson(Map<String, dynamic> json) =>
      _$OrderReponseDataFromJson(json);
  Map<String, dynamic> toJson() => _$OrderReponseDataToJson(this);

}

@JsonSerializable()
class OrderReponse {

  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'orderCode')
  String? orderCode;

  @JsonKey(name: 'user')
  User? user;

  @JsonKey(name: 'payment')
  Payment? payment;

  @JsonKey(name: 'shipping')
  Shipping? shipping;

  @JsonKey(name: 'orderStatus')
  OrderStatus? orderStatus;

  @JsonKey(name: 'fullName')
  String? fullName;

  @JsonKey(name: 'email')
  String? email;

  @JsonKey(name: 'createdAt')
  String? createdAt;

  @JsonKey(name: 'modifiedBy')
  String? modifiedBy;

  OrderReponse(
      {this.id,
        this.orderCode,
        this.user,
        this.payment,
        this.shipping,
        this.orderStatus,
        this.fullName,
        this.email,
        this.createdAt,
        this.modifiedBy});

  factory OrderReponse.fromJson(Map<String, dynamic> json) =>
      _$OrderReponseFromJson(json);
  Map<String, dynamic> toJson() => _$OrderReponseToJson(this);

}

@JsonSerializable()
class User {

  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'username')
  String? username;

  @JsonKey(name: 'fullname')
  String? fullname;

  @JsonKey(name: 'avatar')
  String? avatar;

  @JsonKey(name: 'modifiedAt')
  String? modifiedAt;

  @JsonKey(name: 'lastLogin')
  String? lastLogin;

  @JsonKey(name: 'actived')
  bool? actived;

  @JsonKey(name: 'verify')
  bool? verify;

  @JsonKey(name: 'modifiedBy')
  String? modifiedBy;


  User(
      {this.id,
        this.username,
        this.fullname,
        this.avatar,
        this.modifiedAt,
        this.lastLogin,
        this.actived,
        this.verify,
        this.modifiedBy});

  factory User.fromJson(Map<String, dynamic> json) =>
      _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);

}

@JsonSerializable()
class Payment {
  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'payment_id')
  String? paymentId;

  @JsonKey(name: 'name_payment')
  String? namePayment;

  Payment({this.id, this.paymentId, this.namePayment});

  factory Payment.fromJson(Map<String, dynamic> json) =>
      _$PaymentFromJson(json);
  Map<String, dynamic> toJson() => _$PaymentToJson(this);
}

@JsonSerializable()
class Shipping {
  @JsonKey(name: 'id')
  int? id;
  @JsonKey(name: 'shipping_id')
  String? shippingId;
  @JsonKey(name: 'name_shipping')
  String? nameShipping;

  Shipping({this.id, this.shippingId, this.nameShipping});

  factory Shipping.fromJson(Map<String, dynamic> json) =>
      _$ShippingFromJson(json);
  Map<String, dynamic> toJson() => _$ShippingToJson(this);
}

@JsonSerializable()
class OrderStatus {
  @JsonKey(name: 'id')
  int? id;
  @JsonKey(name: 'code')
  String? code;
  @JsonKey(name: 'status')
  String? status;

  OrderStatus({this.id, this.code, this.status});

  factory OrderStatus.fromJson(Map<String, dynamic> json) =>
      _$OrderStatusFromJson(json);
  Map<String, dynamic> toJson() => _$OrderStatusToJson(this);

}



