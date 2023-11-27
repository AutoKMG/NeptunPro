import 'package:conditional_builder_null_safety/conditional_builder_null_safety.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/courses/handler.dart';
import 'package:neptunpro/presentation/reusable_widgets/custom_search_bar.dart';
import 'package:neptunpro/presentation/reusable_widgets/no_available_content.dart';
import 'package:neptunpro/presentation/screens/courses/widgets/course_card.dart';

class CoursesScreen extends StatelessWidget {
  const CoursesScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<CoursesHandler, CoursesState>(builder: (context, state) {
            CoursesHandler handler = context.read();
            return Column(
    children: [customeSearchBar(handler), body(handler, state)],
            );
          });
  }
}

CustomSearchBar customeSearchBar(CoursesHandler handler) {
  return CustomSearchBar(
    label: 'Course',
    icon: Icons.all_inbox_rounded,
    onChanged: (value) {
      handler.retrieveCoursesByName(value);
    },
    validator: (value) {
      if (value == null || value.isEmpty) {
        return 'Please enter a name';
      }
      return null;
    },
  );
}

Widget body(CoursesHandler handler, CoursesState state) {
  if (state is CoursesStateInitial || state is CoursesStateLoading) {
    handler.retrieveCourses();
    return const CircularProgressIndicator();
  }
  if (state is CoursesStateLoading) {
    return const CircularProgressIndicator();
  } else if (state is CoursesStateSuccessful) {
    return ConditionalBuilder(
        condition: state.courses.isNotEmpty,
        builder: (context) {
          return Expanded(
            child: ListView.builder(
                itemCount: state.courses.length,
                itemBuilder: (context, index) {
                  return CourseCard(course: state.courses[index]);
                }),
          );
        },
        fallback: (context) {
          return const NoAvailableContent();
        });
  } else {
    return const CircularProgressIndicator();
  }
}
