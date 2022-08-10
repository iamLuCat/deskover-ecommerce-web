// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'shipping_payment_status.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Payment _$PaymentFromJson(Map<String, dynamic> json) => Payment(
      id: json['id'] as int?,
      payment_id: json['payment_id'] as String?,
      name_payment: json['name_payment'] as String?,
    );

Map<String, dynamic> _$PaymentToJson(Payment instance) => <String, dynamic>{
      'id': instance.id,
      'payment_id': instance.payment_id,
      'name_payment': instance.name_payment,
    };

Shipping _$ShippingFromJson(Map<String, dynamic> json) => Shipping(
      id: json['id'] as int?,
      shippingId: json['shippingId'] as String?,
      name_shipping: json['name_shipping'] as String?,
    );

Map<String, dynamic> _$ShippingToJson(Shipping instance) => <String, dynamic>{
      'id': instance.id,
      'shippingId': instance.shippingId,
      'name_shipping': instance.name_shipping,
    };
