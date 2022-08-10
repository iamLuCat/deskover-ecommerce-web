import 'dart:async';

import '../core/dio_cache_object.dart';
import 'i_dio_cache_store.dart';


class DioCacheMemory extends IDioCacheStore {

  Map<String, DioCacheObject> cacheMap = Map.identity();

  @override
  Future<bool> clear() {
    cacheMap.clear();
    return Future<bool>.value(cacheMap.isEmpty);
  }

  @override
  Future<bool> clearExpired() {
    bool result = false;
    for (var key in cacheMap.keys) {
      bool remove = (cacheMap[key]?.maxExpirationDate ?? 0) < DateTime.now().millisecondsSinceEpoch;
      if (remove) {
        result = null != cacheMap.remove(key);
      }
    }
    return Future<bool>.value(result);
  }

  @override
  Future<bool> delete(String key, {String? subkey}) {
    return Future<bool>.value(null != cacheMap.remove('$key$subkey'));
  }

  @override
  Future<DioCacheObject?> getCache(String key, {String? subkey}) {
    DioCacheObject? result;
    List<String> keyList = cacheMap.keys.toList();
    int index = keyList.indexOf('$key$subkey');
    if (index >= 0) {
      result = cacheMap[keyList[index]];
    }
    return Future<DioCacheObject?>.value(result);
  }

  @override
  Future<bool> setCache(DioCacheObject dioCacheObject) {
    cacheMap.update(
      '${dioCacheObject.key}${dioCacheObject.subKey}',
      (value) => dioCacheObject,
      ifAbsent: () => dioCacheObject,
    );
    return Future<bool>.value(true);
  }

}
