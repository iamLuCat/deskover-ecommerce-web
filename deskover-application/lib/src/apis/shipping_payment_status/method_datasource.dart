
import 'package:deskover_develop/src/apis/shipping_payment_status/response/shipping_payment_status.dart';
import 'package:deskover_develop/src/apis/shipping_payment_status/service/method_order_api.dart';
import 'package:injectable/injectable.dart';

abstract class MethodDataSource{

  Future<List<Payment>?> doGetPayment();
  Future<Payment> doGetPaymentById(int id);

  Future<List<Shipping>?> doGetShipping();
  Future<Shipping> doGetShippingById(int id);


}
@LazySingleton(as: MethodDataSource)
class MethodDataSourceImpl extends MethodDataSource{
  final MethodOrderApi _methodOrderApi;

  MethodDataSourceImpl(this._methodOrderApi);

  @override
  Future<List<Payment>?> doGetPayment()
  => _methodOrderApi.doGetAllPayment();

  @override
  Future<Payment> doGetPaymentById(int id)
  => _methodOrderApi.doGetByIdPayment(id);

  @override
  Future<List<Shipping>?> doGetShipping()
  => _methodOrderApi.doGetAllShipping();

  @override
  Future<Shipping> doGetShippingById(int id)
  => _methodOrderApi.doGetByIdShipping(id);



}