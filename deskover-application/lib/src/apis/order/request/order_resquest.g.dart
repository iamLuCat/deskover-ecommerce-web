// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_resquest.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderResquest _$OrderResquestFromJson(Map<String, dynamic> json) =>
    OrderResquest(
      payment: json['payment'] == null
          ? null
          : Payment.fromJson(json['payment'] as Map<String, dynamic>),
      shipping: json['shipping'] == null
          ? null
          : Shipping.fromJson(json['shipping'] as Map<String, dynamic>),
      note: json['note'] as String?,
      fee: (json['fee'] as num?)?.toDouble(),
    );

Map<String, dynamic> _$OrderResquestToJson(OrderResquest instance) =>
    <String, dynamic>{
      'payment': instance.payment,
      'shipping': instance.shipping,
      'note': instance.note,
      'fee': instance.fee,
    };
