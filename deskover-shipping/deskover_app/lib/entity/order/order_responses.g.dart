// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_responses.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DataOrderReponse _$DataOrderReponseFromJson(Map<String, dynamic> json) =>
    DataOrderReponse()
      ..data = (json['data'] as List<dynamic>?)
          ?.map((e) => OrderReponses.fromJson(e as Map<String, dynamic>))
          .toList();

Map<String, dynamic> _$DataOrderReponseToJson(DataOrderReponse instance) =>
    <String, dynamic>{
      'data': instance.data,
    };

OrderReponses _$OrderReponsesFromJson(Map<String, dynamic> json) =>
    OrderReponses(
      id: json['id'] as int?,
      orderCode: json['orderCode'] as String?,
      fullName: json['fullName'] as String?,
      email: json['email'] as String?,
      address: json['address'] as String?,
      province: json['province'] as String?,
      district: json['district'] as String?,
      ward: json['ward'] as String?,
      tel: json['tel'] as String?,
      orderItem: (json['orderItem'] as List<dynamic>?)
          ?.map((e) => OrderItem.fromJson(e as Map<String, dynamic>))
          .toList(),
      createdAt: json['createdAt'] as String?,
      code: json['code'] as String?,
      status: json['status'] as String?,
      totalPrice: json['totalPrice'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
    )
      ..qrCode = json['qrCode'] as String?
      ..note = json['note'] as String?
      ..shipping_note = json['shipping_note'] as String?;

Map<String, dynamic> _$OrderReponsesToJson(OrderReponses instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orderCode': instance.orderCode,
      'qrCode': instance.qrCode,
      'fullName': instance.fullName,
      'email': instance.email,
      'address': instance.address,
      'province': instance.province,
      'district': instance.district,
      'ward': instance.ward,
      'tel': instance.tel,
      'orderItem': instance.orderItem,
      'createdAt': instance.createdAt,
      'note': instance.note,
      'shipping_note': instance.shipping_note,
      'code': instance.code,
      'status': instance.status,
      'totalPrice': instance.totalPrice,
      'modifiedBy': instance.modifiedBy,
    };

OrderItem _$OrderItemFromJson(Map<String, dynamic> json) => OrderItem(
      name: json['name'] as String?,
      quantity: json['quantity'] as int?,
      price: json['price'] as String?,
      img: json['img'] as String?,
    );

Map<String, dynamic> _$OrderItemToJson(OrderItem instance) => <String, dynamic>{
      'name': instance.name,
      'quantity': instance.quantity,
      'img': instance.img,
      'price': instance.price,
    };
