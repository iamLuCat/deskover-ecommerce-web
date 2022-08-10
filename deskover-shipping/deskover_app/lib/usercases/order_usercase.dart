import 'package:deskover_app/modules/receive_orders/service/service/order_service.dart';
import 'package:injectable/injectable.dart';

import '../entity/order/order_response.dart';
import '../entity/order/order_responses.dart';

@LazySingleton()
class OrderUsercase {
  final OrderService _orderService;

  @factoryMethod
  OrderUsercase(this._orderService);

  Future<OrderReponses> findByOrderCode(String orderCode, String status)
  => _orderService.findByOrderCode(orderCode, status);

  Future<void> doPostPickupOrder(String orderCode, String status,String note)
  => _orderService.doPostPickupOrder(orderCode, status,note);

  Future<DataOrderReponse> doGetAllOrderDelivery(String status)
  => _orderService.getAllOrderDelivery(status);

  Future<DataOrderReponse> getListMyOrders()
  => _orderService.getAllOrderByUser();



}