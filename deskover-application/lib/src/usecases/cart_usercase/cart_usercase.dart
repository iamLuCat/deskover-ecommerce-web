import 'package:deskover_develop/src/apis/cart/cart_datasource.dart';
import 'package:deskover_develop/src/apis/cart/response/cart_response.dart';
import 'package:deskover_develop/src/apis/cart/response/fee.dart';
import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/apis/shipping_payment_status/method_datasource.dart';
import 'package:deskover_develop/src/apis/shipping_payment_status/response/shipping_payment_status.dart';
import 'package:deskover_develop/src/apis/user_addrees/response/user_address.dart';
import 'package:deskover_develop/src/apis/user_addrees/user_address_datasource.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class CartUserCase{
  final CartDataSource _cartDataSource;
  final UserAddressDataSource _userAddressDataSource;
  final MethodDataSource _methodDataSource;

  @factoryMethod
  CartUserCase(this._cartDataSource, this._userAddressDataSource, this._methodDataSource);

  Future<MessageResponse> addToCart( int productId, int quantity)
  => _cartDataSource.addToCart( productId, quantity);

  Future<MessageResponse> minusCart( int productId)
  => _cartDataSource.minusCart( productId);

  Future<MessageResponse> deleteCart( int productId)
  => _cartDataSource.deleteCart( productId);

  Future<List<Cart>?> doGetAllCartOrder()
  => _cartDataSource.doGetAllCartOrder();

  Future<FeeGHTK> getFee(body)
  =>_cartDataSource.getFee(body);

 
  Future<MessageResponse> changeActive(int id)
  => _userAddressDataSource.changeActive(id);

 
  Future<MessageResponse> changeChoose(int id)
  => _userAddressDataSource.changeChoose(id);

 
  Future<List<UserAddress>?> doGetAddress()
  => _userAddressDataSource.doGetAddress();
 
  Future<MessageResponse> doPostAddress(UserAddress userAddress)
  => _userAddressDataSource.doPostAddress(userAddress);

  Future<MessageResponse> doPutAddress(UserAddress userAddress)
  => _userAddressDataSource.doPutAddress(userAddress);

  //GetAllShipping Method
 
  Future<List<Payment>?> doGetPayment()
  => _methodDataSource.doGetPayment();
  
  Future<Payment> doGetPaymentById(int id)
  => _methodDataSource.doGetPaymentById(id);

 
  Future<List<Shipping>?> doGetShipping()
  => _methodDataSource.doGetShipping();

 
  Future<Shipping> doGetShippingById(int id)
  => _methodDataSource.doGetShippingById(id);





}