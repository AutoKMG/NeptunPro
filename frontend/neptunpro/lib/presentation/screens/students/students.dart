import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/students/handler.dart';
import 'package:neptunpro/presentation/screens/students/widgets/student_card.dart';

class StudentsScreen extends StatelessWidget {
  const StudentsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => StudentsHandler(),
      child: BlocBuilder<StudentsHandler, StudentsState>(
        builder: (context, state) {
          if (state is StudentsStateInitial) {
            return const CircularProgressIndicator();
          } else if (state is StudentsStateSuccess) {
            return GridView.builder(
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
                  return StudentCard(studentInfo: state.students[index], context: context,);
                });
          } else {
            return const CircularProgressIndicator();
          }
        },
      ),
    );
  }
}
