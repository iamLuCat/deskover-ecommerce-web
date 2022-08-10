import 'package:deskover_develop/src/apis/user_addrees/response/user_address.dart';
import 'package:deskover_develop/src/apis/user_addrees/service/user_address_api.dart';
import 'package:injectable/injectable.dart';

import '../message_response.dart';

abstract class UserAddressDataSource{
  Future<List<UserAddress>?> doGetAddress();
  Future<MessageResponse> doPostAddress(UserAddress userAddress);
  Future<MessageResponse> changeActive(int id);
  Future<MessageResponse> changeChoose( int id);
  Future<MessageResponse> doPutAddress(UserAddress userAddress,);
  Future<MessageResponse> changePassword(dynamic body);

}
@LazySingleton(as: UserAddressDataSource)
class UserAddressDataSourceIpml extends UserAddressDataSource{

  final UserAddressApi _userAddressApi;
  UserAddressDataSourceIpml(this._userAddressApi);

  @override
  Future<MessageResponse> changeActive(int id)
  => _userAddressApi.changeActive(id);

  @override
  Future<MessageResponse> changeChoose(int id )
  => _userAddressApi.changeChoose(id);

  @override
  Future<List<UserAddress>?> doGetAddress()
  => _userAddressApi.doGetAddress();
  @override
  Future<MessageResponse> doPostAddress(UserAddress userAddress, )
  => _userAddressApi.doPostAddress(userAddress);

  @override
  Future<MessageResponse> doPutAddress(UserAddress userAddress)
  => _userAddressApi.doPutAddrees(userAddress);

  @override
  Future<MessageResponse> changePassword(body)
  => _userAddressApi.changePassword(body);

}