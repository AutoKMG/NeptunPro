// ignore_for_file: public_member_api_docs, sort_constructors_first, must_be_immutable
import 'package:flutter/material.dart';

import 'package:neptunpro/data/models/teacher.dart';
import 'package:neptunpro/presentation/reusable_widgets/person_card.dart';
import 'package:neptunpro/presentation/screens/students/widgets/students_details_popup.dart';

class TeacherCard extends StatelessWidget {
  Teacher teacherInfo;
  BuildContext context;
  TeacherCard({
    Key? key,
    required this.teacherInfo,
    required this.context
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return PersonCard(
        cardContent: cardContent());
  }

  Column cardContent() {
    return Column(
      mainAxisSize: MainAxisSize.max,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            Container(
              width: 100,
              height: 100,
              decoration: const BoxDecoration(
                image: DecorationImage(
                    image: AssetImage("assets/images/teacher.png"),
                    fit: BoxFit.cover),
              ),
            ),
            const SizedBox(
              width: 20,
            ),
            Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "${teacherInfo.firstname} ${teacherInfo.lastname}",
                  style: const TextStyle(
                    fontSize: 32,
                    color: Colors.white,
                  ),
                ),
                Text(
                  "ID: ${teacherInfo.id}",
                  style: const TextStyle(color: Colors.white, fontSize: 20),
                ),
              ],
            )
          ],
        ),
      ],
    );
  }
}
