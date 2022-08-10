import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/apis/notify/notify_response.dart';
import 'package:deskover_develop/src/apis/notify/service/notify_api.dart';
import 'package:deskover_develop/src/apis/order/request/order/order_response.dart';
import 'package:deskover_develop/src/apis/order/request/order_resquest.dart';
import 'package:deskover_develop/src/apis/order/service/order_api.dart';
import 'package:deskover_develop/src/modules/notification/notification.dart';
import 'package:injectable/injectable.dart';

abstract class OrderDataSource{
  Future<MessageResponse> addOrder(OrderResquest orderResquest);
  Future<OrderReponse> getOrderByUser(String orderCode);
  Future<List<NotifyResponse>?> doGetAllNotify(String orderCode);
  Future<List<OrderReponse>?> getAllOrderByStatusCode( String statusCode);
  Future<MessageResponse> canCelOrder(String orderCode,String statusOrder);

}
@LazySingleton(as: OrderDataSource)
class OrderDataSourceImpl extends OrderDataSource{
  final OrderAPI _orderAPI;
  final NotifyApi _notifyApi;

  OrderDataSourceImpl(this._orderAPI, this._notifyApi);

  @override
  Future<MessageResponse> addOrder(OrderResquest orderResquest)
  => _orderAPI.addOrder(orderResquest);

  @override
  Future<OrderReponse> getOrderByUser(String orderCode)
  => _orderAPI.getOrderByUser(orderCode);

  @override
  Future<List<NotifyResponse>?> doGetAllNotify(String orderCode)
  => _notifyApi.doGetNotifyByUserAndOrderCode(orderCode);

  @override
  Future<List<OrderReponse>?> getAllOrderByStatusCode(String statusCode)
  => _orderAPI.getAllOrderByStatusCode(statusCode);

  @override
  Future<MessageResponse> canCelOrder(String orderCode,statusOrder)
  => _orderAPI.canCelOrder(orderCode,statusOrder);
}