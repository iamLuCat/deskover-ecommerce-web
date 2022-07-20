import 'dart:async';
import 'dart:math';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

import '../../themes/dialogs/loading_dialog.dart';
import 'dashboard_model.dart';

class BarChartSample extends StatefulWidget {
  final List<Color> availableColors = const [
    Colors.purpleAccent,
    Colors.yellow,
    Colors.lightBlue,
    Colors.orange,
    Colors.pink,
    Colors.redAccent,
  ];

  const BarChartSample({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => BarChartSample1State();
}

class BarChartSample1State extends ViewWidget<BarChartSample,DashBoardModel> {
  final Color barBackgroundColor = const Color(0xb802a983);
  final Duration animDuration = const Duration(milliseconds: 250);
  final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");
  int touchedIndex = -1;
  bool isPlaying = false;


  @override
  Widget build(BuildContext context) {
    return AspectRatio(
      aspectRatio: 1,
      child: Card(
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(18)),
        color: UIColors.white,
        // const Color(0xff81e5cd),
        child: Stack(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(16),
              child: ListView(
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: <Widget>[
                      const Text(
                        'Tổng tiền 7 ngày',
                        style: TextStyle(
                            color: UIColors.black,
                            fontSize: 14,
                            fontWeight: FontWeight.bold),
                      ),
                      const SizedBox(
                        height: 38,
                      ),
                      Obx(()=> SizedBox(
                        height: MediaQuery.of(context).size.height *0.33,
                        child: Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 8.0),
                          child: Visibility(
                            visible: viewModel.totalPrice7DaysAgo.isEmpty,
                            child:  const LoadingDialog(
                              backgroundColor: Colors.transparent,
                              elevation: 0,
                              message: 'Đang tìm thống kê...',
                            ),
                            replacement: BarChart(
                              mainBarData(),
                              swapAnimationDuration: animDuration,
                            ) ,

                          )

                        ),
                      ),),
                      // const SizedBox(
                      //   height: 12,
                      // ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );

  }

  BarChartGroupData makeGroupData(
    int x,
    double y, {
    bool isTouched = false,
    Color barColor = Colors.blueAccent,
    double width = 22,
    List<int> showTooltips = const [],
  }) {
    return BarChartGroupData(
      x: x,
      barRods: [
        BarChartRodData(
          toY: isTouched ? y + 1 : y,
          color: isTouched ? Colors.yellow : barColor,
          width: width,
          borderSide: isTouched
              ? BorderSide(color: Colors.yellow, width: 1)
              : const BorderSide(color: Colors.blueAccent, width: 0),
          backDrawRodData: BackgroundBarChartRodData(
              show: true, toY: 20, color: UIColors.black10
              // barBackgroundColor,
              ),
        ),
      ],
      showingTooltipIndicators: showTooltips,
    );
  }

  List<BarChartGroupData> showingGroups() => List.generate(7, (i) {
        switch (i) {
          case 0:
            return makeGroupData(0,viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[6].totalPrice! : 0, isTouched: i == touchedIndex);
          case 1:
            return makeGroupData(1, viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[5].totalPrice! : 0, isTouched: i == touchedIndex);
          case 2:
            return makeGroupData(2, viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[4].totalPrice! : 0, isTouched: i == touchedIndex);
          case 3:
            return makeGroupData(3, viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[3].totalPrice! : 0, isTouched: i == touchedIndex);
          case 4:
            return makeGroupData(4, viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[2].totalPrice! : 0, isTouched: i == touchedIndex);
          case 5:
            return makeGroupData(5, viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[1].totalPrice! : 0, isTouched: i == touchedIndex);
          case 6:
            return makeGroupData(6,viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[0].totalPrice! : 0, isTouched: i == touchedIndex);
          default:
            return throw Error();
        }
      });
  List<BarChartGroupData> showingGroups1() => List.generate(7, (i) {
    switch (i) {
      case 0:
        return makeGroupData(0,  0, isTouched: i == touchedIndex);
      case 1:
        return makeGroupData(1,  0, isTouched: i == touchedIndex);
      case 2:
        return makeGroupData(2,  0, isTouched: i == touchedIndex);
      case 3:
        return makeGroupData(3,  0, isTouched: i == touchedIndex);
      case 4:
        return makeGroupData(4,  0, isTouched: i == touchedIndex);
      case 5:
        return makeGroupData(5,  0, isTouched: i == touchedIndex);
      case 6:
        return makeGroupData(6,  0, isTouched: i == touchedIndex);
      default:
        return throw Error();
    }
  });

  BarChartData mainBarData() {

    return BarChartData(
      barTouchData: BarTouchData(
        touchTooltipData: BarTouchTooltipData(
            tooltipBgColor: Colors.blueGrey,
            getTooltipItem: (group, groupIndex, rod, rodIndex) {
              String? weekDay;
              switch (group.x.toInt()) {
                case 0:
                  weekDay = viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[6].priceFormat : 'Ngày 1';
                  break;
                case 1:
                  weekDay =viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[5].date : 'Ngày 2';
                  break;
                case 2:
                  weekDay =viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[4].date : 'Ngày 3';
                  break;
                case 3:
                  weekDay =viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[3].date : 'Ngày 4';
                  break;
                case 4:
                  weekDay =viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[2].date : 'Ngày 5';
                  break;
                case 5:
                  weekDay = viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[1].date : 'Ngày 6';
                  break;
                case 6:
                  weekDay = viewModel.totalPrice7DaysAgo.isNotEmpty ? viewModel.totalPrice7DaysAgo[0].date : 'Hôm nay';
                  break;
                default:
                  throw Error();
              }
              return BarTooltipItem(
                weekDay.toString() + '\n',
                const TextStyle(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 18,
                ),
                children: <TextSpan>[
                  TextSpan(
                    text:
                    // (rod.toY - 1).toInt(),
                    formatCurrency.format((rod.toY - 1).toInt()) ,
                    style: const TextStyle(
                      color: Colors.yellow,
                      fontSize: 16,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                ],
              );
            }),
        touchCallback: (FlTouchEvent event, barTouchResponse) {
          setState(() {
            if (!event.isInterestedForInteractions ||
                barTouchResponse == null ||
                barTouchResponse.spot == null) {
              touchedIndex = -1;
              return;
            }
            touchedIndex = barTouchResponse.spot!.touchedBarGroupIndex;
          });
        },
      ),
      titlesData: FlTitlesData(
        show: true,
        rightTitles: AxisTitles(
          sideTitles: SideTitles(showTitles: false),
        ),
        topTitles: AxisTitles(
          sideTitles: SideTitles(showTitles: false),
        ),
        bottomTitles: AxisTitles(
          sideTitles: SideTitles(
            showTitles: true,
            getTitlesWidget: getTitles,
            reservedSize: 38,
          ),
        ),
        leftTitles: AxisTitles(
          sideTitles: SideTitles(
            showTitles: false,
          ),
        ),
      ),
      borderData: FlBorderData(
        show: false,
      ),
      barGroups: showingGroups(),
      gridData: FlGridData(show: false),
    );
  }

  Widget getTitles(double value, TitleMeta meta) {
    const style = TextStyle(
      color: Colors.black,
      fontWeight: FontWeight.bold,
      fontSize: 14,
    );
    Widget text;
    switch (value.toInt()) {
      case 0:
        text = const Text('1', style: style);
        break;
      case 1:
        text = const Text('2', style: style);
        break;
      case 2:
        text = const Text('3', style: style);
        break;
      case 3:
        text = const Text('4', style: style);
        break;
      case 4:
        text = const Text('5', style: style);
        break;
      case 5:
        text = const Text('6', style: style);
        break;
      case 6:
        text = const Text('Hôm nay', style: style);
        break;
      default:
        text = const Text('8', style: style);
        break;
    }
    return SideTitleWidget(
      axisSide: meta.axisSide,
      space: 16,
      child: text,
    );
  }

  Future<dynamic> refreshState() async {
    setState(() {});
    await Future<dynamic>.delayed(
        animDuration + const Duration(milliseconds: 50));
    if (isPlaying) {
      await refreshState();
    }
  }

  @override
  DashBoardModel createViewModel() =>  getIt<DashBoardModel>();
}
