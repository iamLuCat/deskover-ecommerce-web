

import '../core/dio_cache_object.dart';
import 'i_dio_cache_store.dart';

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