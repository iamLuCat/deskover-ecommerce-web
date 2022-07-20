// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderReponseData _$OrderReponseDataFromJson(Map<String, dynamic> json) =>
    OrderReponseData(
      data: json['data'] == null
          ? null
          : OrderReponse.fromJson(json['data'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$OrderReponseDataToJson(OrderReponseData instance) =>
    <String, dynamic>{
      'data': instance.data,
    };

OrderReponse _$OrderReponseFromJson(Map<String, dynamic> json) => OrderReponse(
      id: json['id'] as int?,
      orderCode: json['orderCode'] as String?,
      user: json['user'] == null
          ? null
          : User.fromJson(json['user'] as Map<String, dynamic>),
      payment: json['payment'] == null
          ? null
          : Payment.fromJson(json['payment'] as Map<String, dynamic>),
      shipping: json['shipping'] == null
          ? null
          : Shipping.fromJson(json['shipping'] as Map<String, dynamic>),
      orderStatus: json['orderStatus'] == null
          ? null
          : OrderStatus.fromJson(json['orderStatus'] as Map<String, dynamic>),
      fullName: json['fullName'] as String?,
      email: json['email'] as String?,
      createdAt: json['createdAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$OrderReponseToJson(OrderReponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orderCode': instance.orderCode,
      'user': instance.user,
      'payment': instance.payment,
      'shipping': instance.shipping,
      'orderStatus': instance.orderStatus,
      'fullName': instance.fullName,
      'email': instance.email,
      'createdAt': instance.createdAt,
      'modifiedBy': instance.modifiedBy,
    };

User _$UserFromJson(Map<String, dynamic> json) => User(
      id: json['id'] as int?,
      username: json['username'] as String?,
      fullname: json['fullname'] as String?,
      avatar: json['avatar'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      lastLogin: json['lastLogin'] as String?,
      actived: json['actived'] as bool?,
      verify: json['verify'] as bool?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'id': instance.id,
      'username': instance.username,
      'fullname': instance.fullname,
      'avatar': instance.avatar,
      'modifiedAt': instance.modifiedAt,
      'lastLogin': instance.lastLogin,
      'actived': instance.actived,
      'verify': instance.verify,
      'modifiedBy': instance.modifiedBy,
    };

Payment _$PaymentFromJson(Map<String, dynamic> json) => Payment(
      id: json['id'] as int?,
      paymentId: json['payment_id'] as String?,
      namePayment: json['name_payment'] as String?,
    );

Map<String, dynamic> _$PaymentToJson(Payment instance) => <String, dynamic>{
      'id': instance.id,
      'payment_id': instance.paymentId,
      'name_payment': instance.namePayment,
    };

Shipping _$ShippingFromJson(Map<String, dynamic> json) => Shipping(
      id: json['id'] as int?,
      shippingId: json['shipping_id'] as String?,
      nameShipping: json['name_shipping'] as String?,
    );

Map<String, dynamic> _$ShippingToJson(Shipping instance) => <String, dynamic>{
      'id': instance.id,
      'shipping_id': instance.shippingId,
      'name_shipping': instance.nameShipping,
    };

OrderStatus _$OrderStatusFromJson(Map<String, dynamic> json) => OrderStatus(
      id: json['id'] as int?,
      code: json['code'] as String?,
      status: json['status'] as String?,
    );

Map<String, dynamic> _$OrderStatusToJson(OrderStatus instance) =>
    <String, dynamic>{
      'id': instance.id,
      'code': instance.code,
      'status': instance.status,
    };
