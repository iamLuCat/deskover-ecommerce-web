import 'package:injectable/injectable.dart';

import '../../../../entity/order/order_response.dart';
import '../../../../entity/order/order_responses.dart';
import '../find_by_order_code_api.dart';

abstract class OrderService{
    Future<OrderReponses> findByOrderCode(String orderCode, String status);

    Future<void> doPostPickupOrder(String orderCode, String status,String note);

    Future<DataOrderReponse> getAllOrderDelivery(String status);

    Future<DataOrderReponse> getAllOrderByUser();
}

@LazySingleton(as:OrderService)
class OrderServiceImpl extends OrderService{

  final OrderApi _orderApi;
  OrderServiceImpl(this._orderApi);

  @override
  Future<OrderReponses> findByOrderCode(String orderCode, String status)
    => _orderApi.getByOrderCodeAndStatusCode(orderCode, status);

  @override
  Future<void> doPostPickupOrder(String orderCode, String status, String note)
  => _orderApi.doPostPickupOrder(orderCode, status, note);

  @override
  Future<DataOrderReponse> getAllOrderDelivery(String status)
  => _orderApi.getAllOrderDelivery(status);

  @override
  Future<DataOrderReponse> getAllOrderByUser()
  => _orderApi.getAllOrderByUser();


}