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
    return BlocProvider(
      create: (context) => CoursesHandler(),
      child:
          BlocBuilder<CoursesHandler, CoursesState>(builder: (context, state) {
        CoursesHandler handler = context.read();
        return Column(
          children: [
            customeSearchBar(handler),
            body(state)],
        );
      }),
    );
  }
}

CustomSearchBar customeSearchBar(CoursesHandler handler) {
  return CustomSearchBar(
    label: 'Course',
    icon: Icons.person,
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

Widget body(CoursesState state) {
  if (state is CoursesStateInitial || state is CoursesStateLoading) {
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
