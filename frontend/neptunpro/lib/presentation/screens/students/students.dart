import 'package:conditional_builder_null_safety/conditional_builder_null_safety.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/students/handler.dart';
import 'package:neptunpro/presentation/reusable_widgets/custom_search_bar.dart';
import 'package:neptunpro/presentation/reusable_widgets/no_available_content.dart';
import 'package:neptunpro/presentation/screens/students/widgets/student_card.dart';

class StudentsScreen extends StatelessWidget {
  const StudentsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => StudentsHandler(),
      child: BlocBuilder<StudentsHandler, StudentsState>(
        builder: (context, state) {
          StudentsHandler handler = context.read();
          return Column(
            children: [
              customSearchBar(handler),
              body(state),
            ],
          );
        },
      ),
    );
  }

  Widget body(StudentsState state) {
    if (state is StudentsStateInitial || state is StudentsStateLoading) {
      return const CircularProgressIndicator();
    } else if (state is StudentsStateSuccess) {
      return ConditionalBuilder(
          condition: state.students.isNotEmpty,
          builder: (context) {
            return Expanded(
              child: GridView.builder(
                  padding: EdgeInsets.all(20),
                  shrinkWrap: true,
                  gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                    crossAxisCount: 3,
                    mainAxisSpacing: 30.0,
                    crossAxisSpacing: 30.0,
                    childAspectRatio: 2.5,
                  ),
                  itemCount: state.students.length,
                  itemBuilder: (context, index) {
                    return StudentCard(
                      studentInfo: state.students[index],
                      context: context,
                    );
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

  CustomSearchBar customSearchBar(StudentsHandler handler) {
    return CustomSearchBar(
      label: 'Student',
      icon: Icons.person,
      onChanged: (value) {
        handler.retrieveStudentsByName(value);
      },
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Please enter a name';
        }
        return null;
      },
    );
  }
}
