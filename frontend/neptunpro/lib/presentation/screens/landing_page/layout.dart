import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/data/constants/colors.dart';
import 'package:neptunpro/logic/handlers/courses/handler.dart';
import 'package:neptunpro/logic/handlers/landing/handler.dart';
import 'package:neptunpro/logic/handlers/students/handler.dart';
import 'package:neptunpro/logic/handlers/teachers/handler.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/body.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/header.dart';

class LandingPageScreen extends StatelessWidget {
  const LandingPageScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(
          create: (context) => LandingPageHandler(),
        ),
        BlocProvider(
          create: (context) => TeachersHandler(),
        ),
        BlocProvider(
          create: (context) => StudentsHandler(),
        ),
        BlocProvider(
          create: (context) => CoursesHandler(),
        ),
      ],
      child: Scaffold(
        backgroundColor: Colors.white,
        body: Column(
          mainAxisSize: MainAxisSize.max,
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            header(),
            Container(
              width: double.infinity,
              height: 2,
              decoration: const BoxDecoration(color: primaryColor),
            ),
            body(),
          ],
        ),
      ),
    );
  }
}
