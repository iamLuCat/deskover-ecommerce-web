import 'package:deskover_app/core/dio_cache/core/dio_cache_object.dart';
import 'package:deskover_app/core/dio_cache/store/i_dio_cache_store.dart';

class DioCacheDisk extends IDioCacheStore {
  @override
  Future<bool> clear() {
    // TODO: implement clear
    throw UnimplementedError();
  }

  @override
  Future<bool> clearExpired() {
    // TODO: implement clearExpired
    throw UnimplementedError();
  }

  @override
  Future<bool> delete(String key, {String? subkey}) {
    // TODO: implement delete
    throw UnimplementedError();
  }

  @override
  Future<DioCacheObject> getCache(String key, {String? subkey}) {
    // TODO: implement getCache
    throw UnimplementedError();
  }

  @override
  Future<bool> setCache(DioCacheObject dioCacheObject) {
    // TODO: implement setCache
    throw UnimplementedError();
  }

}