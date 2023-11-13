// ignore_for_file: public_member_api_docs, sort_constructors_first, must_be_immutable
import 'package:flutter/material.dart';

import 'package:neptunpro/data/models/student_info.dart';
import 'package:neptunpro/presentation/reusable_widgets/person_card.dart';
import 'package:neptunpro/presentation/screens/students/widgets/students_details_popup.dart';

class StudentCard extends StatelessWidget {
  StudentInfo studentInfo;
  BuildContext context;
  StudentCard({
    Key? key,
    required this.studentInfo,
    required this.context
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return PersonCard(
        imageUrl: "assets/images/student.png", cardContent: cardContent());
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
                    image: AssetImage("assets/images/student.png"),
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
                  studentInfo.firstname,
                  style: const TextStyle(
                    fontSize: 32,
                    color: Colors.white,
                  ),
                ),
                Text(
                  "ID: ${studentInfo.id}",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
              ],
            )
          ],
        ),
        Container(
          alignment: Alignment.bottomRight,
          width: double.infinity,
          child: TextButton(
            onPressed: () {
              showStudentDetailsPopup(context, studentInfo);
            },
            child: const Text(
              "See more..",
              style: TextStyle(
                color: Colors.white,
                fontSize: 20,
                fontStyle: FontStyle.italic,
                fontWeight: FontWeight.w100,
              ),
            ),
          ),
        ),
      ],
    );
  }
}
