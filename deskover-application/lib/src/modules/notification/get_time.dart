import 'package:intl/intl.dart';


class GetTime {
  static String parse(DateTime dateTime, {String? locale, String? pattern}) {
    final _pattern = pattern ?? "dd MMM, yyyy hh:mm aa";
    final date = DateFormat(_pattern).format(dateTime);
    var elapsed = DateTime.now().millisecondsSinceEpoch - dateTime.millisecondsSinceEpoch;

    final num seconds = elapsed / 1000;
    final num minutes = seconds / 60;
    final num hours = minutes / 60;
    final num days = hours / 24;

    String result;
    if (seconds < 59) {
      result = ('${seconds.toInt().toString()} giây trước');
    } else if (seconds < 119) {
      result = ('${minutes.toInt().toString()} phút trước');
    } else if (minutes < 59) {
      result =  ('${minutes.toInt().toString()} phút trước');
    } else if (minutes < 119) {
      result = '${hours.toInt().toString()} giờ trước';
    } else if (hours < 24) {
      result = '${hours.toInt().toString()} giờ trước';
    } else if (hours < 48) {
      result = '${days.toInt().toString()} ngày trước';
    } else if (days < 8) {
      result = '${days.toInt().toString()} ngày trước';
    } else {
      result = date;
    }
    return result;
  }
}
