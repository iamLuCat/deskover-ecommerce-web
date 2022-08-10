import 'package:deskover_develop/src/apis/product/product_datasoure.dart';
import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class ProductUserCase{
  final ProductDataSource _productDataSource;

  @factoryMethod
  ProductUserCase(this._productDataSource);

  Future<DataProductResponse> doGetAllProductNew(int page,int size)
  => _productDataSource.doGetProductNew(page,size);

  Future<DataProductResponse> doGetAllProductSale(int page,int size)
  => _productDataSource.doGetProductSale(page,size);

  Future<DataProductResponse> doGetProductByCategoryId(int categoryId, int page, int size,String keySort)
  => _productDataSource.doGetProductByCategoryId(categoryId, page, size,keySort);

  Future<DataProductResponse> doGetProductBySubId(int subId, int page, int size, String keySort)
  => _productDataSource.doGetProductBySubId(subId, page, size,keySort);

  Future<Product> getById(int id)
  => _productDataSource.getById(id);

  Future<DataProductResponse> getSearch(String search, int page, int size)
  => _productDataSource.getSearch(search, page, size);

}