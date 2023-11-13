import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/courses/handler.dart';
import 'package:neptunpro/presentation/screens/courses/widgets/course_card.dart';

class CoursesScreen extends StatelessWidget {
  const CoursesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => CoursesHandler(),
      child: BlocBuilder<CoursesHandler, CoursesState>(
        builder: (context, state) {
          if (state is CoursesStateInitial || state is CoursesStateLoading) {
            return const CircularProgressIndicator();
          } else if (state is CoursesStateSuccessful) {
            return ListView.builder(
                itemCount: state.courses.length,
                itemBuilder: (context, index) {
                  return CourseCard(course: state.courses[index]);
                });
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }
}