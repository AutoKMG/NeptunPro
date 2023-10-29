import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/courses.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/dashboard.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/students.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/teachers.dart';

part 'state.dart';

class LandingPageHandler extends Cubit<LandingPageState> {
  LandingPageHandler() : super(LandingPageStateInitial());

  String selectedPageLabel = "Dashboard";
  Widget selectedPageWidget = dashboard();

  void changePage(String page) {
    selectedPageLabel = page;
    switch (page) {
      case "Dashboard":
        selectedPageWidget = dashboard();
        break;
      case "Students":
        selectedPageWidget = students();
        break;
      case "Teachers":
        selectedPageWidget = teachers();
        break;
      case "Courses":
        selectedPageWidget = courses();
        break;
    }
    emit(LandingPageStatePageChanged());
  }
}
