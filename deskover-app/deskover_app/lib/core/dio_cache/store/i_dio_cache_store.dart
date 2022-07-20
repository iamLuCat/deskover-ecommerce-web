
import 'package:deskover_app/core/dio_cache/core/dio_cache_object.dart';
abstract class IDioCacheStore {
  IDioCacheStore();
  Future<DioCacheObject?> getCache(String key, {String? subkey});
  Future<bool> setCache(DioCacheObject dioCacheObject);
  Future<bool> delete(String key, {String? subkey});
  Future<bool> clearExpired();
  Future<bool> clear();
}