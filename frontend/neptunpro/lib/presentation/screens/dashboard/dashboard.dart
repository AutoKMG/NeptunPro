import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/dashboard/handler.dart';

class DashboardScreen extends StatelessWidget {
  const DashboardScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<DashboardHandler, DashboardState>(
      builder: (context, state) {
        DashboardHandler handler = context.read();
        if (state is DashboardStateInitial) {
          handler.retrieveDashboardInfo();
          return const CircularProgressIndicator();
        } else if (state is DashboardStateLoading) {
          return const CircularProgressIndicator();
        } else if (state is DashboardStateSuccess) {
          return Center(
            child: Container(
              width: 400,
              height: 300,
              decoration:
                  BoxDecoration(borderRadius: BorderRadius.circular(25)),
              child: Text(
                "Students count: ${state.studentsCount}\nTeachers Count: ${state.teachersCout}\nCourses Count: ${state.coursesCount}",
                style: TextStyle(fontSize: 35),
              ),
            ),
          );
        } else {
          return const CircularProgressIndicator();
        }
      },
    );
  }
}
