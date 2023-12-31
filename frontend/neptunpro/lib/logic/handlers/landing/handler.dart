import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:neptunpro/presentation/screens/courses/courses.dart';
import 'package:neptunpro/presentation/screens/dashboard/dashboard.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/courses.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/dashboard.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/sections/teachers.dart';
import 'package:neptunpro/presentation/screens/students/students.dart';
import 'package:neptunpro/presentation/screens/teachers/teachers.dart';

part 'state.dart';

class LandingPageHandler extends Cubit<LandingPageState> {
  LandingPageHandler() : super(LandingPageStateInitial());

  String selectedPageLabel = "Dashboard";
  Widget selectedPageWidget = const DashboardScreen();

  void changePage(String page) {
    selectedPageLabel = page;
    switch (page) {
      case "Dashboard":
        selectedPageWidget = const DashboardScreen();
        break;
      case "Students":
        selectedPageWidget = const StudentsScreen();
        break;
      case "Teachers":
        selectedPageWidget = const TeachersScreen();
        break;
      case "Courses":
        selectedPageWidget = const CoursesScreen();
        break;
    }
    emit(LandingPageStatePageChanged());
  }
}
