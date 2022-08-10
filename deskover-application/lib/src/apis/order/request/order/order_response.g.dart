// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

OrderReponse _$OrderReponseFromJson(Map<String, dynamic> json) => OrderReponse(
      id: json['id'] as int?,
      orderCode: json['orderCode'] as String?,
      qrCode: json['qrCode'] as String?,
      note: json['note'] as String?,
      shippingNote: json['shippingNote'] as String?,
      fullName: json['fullName'] as String?,
      email: json['email'] as String?,
      createdAt: json['createdAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
      unitPrice: (json['unitPrice'] as num?)?.toDouble(),
      orderQuantity: json['orderQuantity'] as int?,
      label: json['label'] as String?,
      fee: (json['fee'] as num?)?.toDouble(),
      estimatedPickTime: json['estimatedPickTime'] as String?,
      estimatedDeliverTime: json['estimatedDeliverTime'] as String?,
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
      orderDetail: json['orderDetail'] == null
          ? null
          : OrderDetail.fromJson(json['orderDetail'] as Map<String, dynamic>),
      products: (json['products'] as List<dynamic>?)
          ?.map((e) => Products.fromJson(e as Map<String, dynamic>))
          .toList(),
      statusPayment: json['statusPayment'] == null
          ? null
          : OrderStatusPayment.fromJson(
              json['statusPayment'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$OrderReponseToJson(OrderReponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'orderCode': instance.orderCode,
      'qrCode': instance.qrCode,
      'note': instance.note,
      'shippingNote': instance.shippingNote,
      'fullName': instance.fullName,
      'email': instance.email,
      'createdAt': instance.createdAt,
      'modifiedBy': instance.modifiedBy,
      'unitPrice': instance.unitPrice,
      'orderQuantity': instance.orderQuantity,
      'label': instance.label,
      'fee': instance.fee,
      'estimatedPickTime': instance.estimatedPickTime,
      'estimatedDeliverTime': instance.estimatedDeliverTime,
      'user': instance.user,
      'payment': instance.payment,
      'shipping': instance.shipping,
      'orderStatus': instance.orderStatus,
      'orderDetail': instance.orderDetail,
      'products': instance.products,
      'statusPayment': instance.statusPayment,
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

OrderStatusPayment _$OrderStatusPaymentFromJson(Map<String, dynamic> json) =>
    OrderStatusPayment(
      id: json['id'] as int?,
      code: json['code'] as String?,
      status: json['status'] as String?,
    );

Map<String, dynamic> _$OrderStatusPaymentToJson(OrderStatusPayment instance) =>
    <String, dynamic>{
      'id': instance.id,
      'code': instance.code,
      'status': instance.status,
    };

OrderDetail _$OrderDetailFromJson(Map<String, dynamic> json) => OrderDetail(
      id: json['id'] as int?,
      address: json['address'] as String?,
      province: json['province'] as String?,
      district: json['district'] as String?,
      ward: json['ward'] as String?,
      tel: json['tel'] as String?,
    );

Map<String, dynamic> _$OrderDetailToJson(OrderDetail instance) =>
    <String, dynamic>{
      'id': instance.id,
      'address': instance.address,
      'province': instance.province,
      'district': instance.district,
      'ward': instance.ward,
      'tel': instance.tel,
    };

Products _$ProductsFromJson(Map<String, dynamic> json) => Products(
      id: json['id'] as int?,
      quantity: json['quantity'] as int?,
      price: (json['price'] as num?)?.toDouble(),
      product: json['product'] == null
          ? null
          : Product.fromJson(json['product'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$ProductsToJson(Products instance) => <String, dynamic>{
      'id': instance.id,
      'quantity': instance.quantity,
      'price': instance.price,
      'product': instance.product,
    };
