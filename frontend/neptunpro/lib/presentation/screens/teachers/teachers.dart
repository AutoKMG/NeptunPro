import 'package:conditional_builder_null_safety/conditional_builder_null_safety.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/teachers/handler.dart';
import 'package:neptunpro/presentation/reusable_widgets/custom_search_bar.dart';
import 'package:neptunpro/presentation/reusable_widgets/no_available_content.dart';
import 'package:neptunpro/presentation/screens/teachers/widgets/teacher_card.dart';

class TeachersScreen extends StatelessWidget {
  const TeachersScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => TeachersHandler(),
      child: BlocBuilder<TeachersHandler, TeachersState>(
          builder: (context, state) {
        TeachersHandler handler = context.read();
        return Column(
          children: [
            customSearchBar(handler),
            body(state),
          ],
        );
      }),
    );
  }
}

Widget body(TeachersState state) {
  if (state is TeachersStateInitial || state is TeachersStateLoading) {
    return const CircularProgressIndicator();
  } else if (state is TeachersStateSuccess) {
    return ConditionalBuilder(
        condition: state.teachers.isNotEmpty,
        builder: (context) {
          return GridView.builder(
              padding: const EdgeInsets.all(20),
              shrinkWrap: true,
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                mainAxisSpacing: 30.0,
                crossAxisSpacing: 30.0,
                childAspectRatio: 2.5,
              ),
              itemCount: state.teachers.length,
              itemBuilder: (context, index) {
                return TeacherCard(
                  teacherInfo: state.teachers[index],
                  context: context,
                );
              });
        },
        fallback: (context) {
          return const NoAvailableContent();
        });
  } else {
    return const CircularProgressIndicator();
  }
}

CustomSearchBar customSearchBar(TeachersHandler handler) {
  return CustomSearchBar(
    label: 'Teacher',
    icon: Icons.person,
    onChanged: (value) {
      handler.retrieveTeachersByName(value);
    },
    validator: (value) {
      if (value == null || value.isEmpty) {
        return 'Please enter a name';
      }
      return null;
    },
  );
}
