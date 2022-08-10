import 'package:deskover_develop/src/apis/addrees/add_addrees_datasource.dart';
import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class AddAddreesUserCase{
  
  final AddAddreesDataSource _addAddreesDataSource;

  AddAddreesUserCase(this._addAddreesDataSource);
  
  Future<List<District>?> doGetAllDistrict(int provinceId)
  => _addAddreesDataSource.doGetAllDistrict(provinceId);

  
  Future<List<Province>?> doGetAllProVince()
  => _addAddreesDataSource.doGetAllProVince();

  Future<Province> doGetProVinceById(int id)
  => _addAddreesDataSource.doGetProVinceById(id);

  
  Future<List<Ward>?> doGetAllWard(int provinceId, int districtId)
  => _addAddreesDataSource.doGetAllWard(provinceId, districtId);


  Future<District> doGetDistrictById(int id)
  => _addAddreesDataSource.doGetDistrictById(id);

  Future<Ward> doGetWardById(int id)
  => _addAddreesDataSource.doGetWardById(id);
}