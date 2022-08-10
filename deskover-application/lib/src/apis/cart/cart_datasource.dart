import 'package:deskover_develop/src/apis/cart/response/cart_response.dart';
import 'package:deskover_develop/src/apis/cart/response/fee.dart';
import 'package:deskover_develop/src/apis/cart/service/cart_api.dart';
import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:injectable/injectable.dart';

abstract class CartDataSource{
  Future<MessageResponse> addToCart(int productId, int quantity );
  Future<MessageResponse> minusCart(int productId);
  Future<MessageResponse> deleteCart(int productId);
  Future<List<Cart>?> doGetAllCartOrder();
  Future<FeeGHTK> getFee(dynamic body);
}
@LazySingleton(as: CartDataSource)
class CartDataSourceImpl extends CartDataSource{
  final CartAPI _cartAPI;

  CartDataSourceImpl(this._cartAPI);
  @override
  Future<MessageResponse> addToCart( int productId, int quantity)
  => _cartAPI.addToCart( productId, quantity);

  @override
  Future<List<Cart>?> doGetAllCartOrder()
  => _cartAPI.doGetAllCartOrder();

  @override
  Future<MessageResponse> minusCart( int productId)
  => _cartAPI.minusCart(productId);

  @override
  Future<MessageResponse> deleteCart( int productId)
  => _cartAPI.deleteCart( productId);

  @override
  Future<FeeGHTK> getFee(body)
  =>_cartAPI.getFee(body);


}