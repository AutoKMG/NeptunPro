import 'package:flutter/material.dart';
import 'package:neptunpro/data/constants/colors.dart';
import 'package:neptunpro/data/models/student_info.dart';

class StudentDetailsPopup extends StatelessWidget {
  final Student studentInfo;

  const StudentDetailsPopup({super.key, required this.studentInfo});

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(16.0),
      ),
      elevation: 0.0,
      backgroundColor: Colors.transparent,
      child: contentBox(context),
    );
  }

  Widget contentBox(BuildContext context) {
    return Stack(
      children: <Widget>[
        Container(
          padding: const EdgeInsets.all(16),
          decoration: BoxDecoration(
            shape: BoxShape.rectangle,
            color: primaryColor,
            borderRadius: BorderRadius.circular(16),
            boxShadow: const [
              BoxShadow(
                color: Colors.black,
                offset: Offset(0, 10),
                blurRadius: 10,
              ),
            ],
          ),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              Text(
                "Student Details",
                style: Theme.of(context).textTheme.titleLarge,
              ),
              const CustomSizedBox(),
              Text(
                "Name: ${studentInfo.firstname} ${studentInfo.lastname}",
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const CustomSizedBox(),
              Text(
                "Program: ${studentInfo.programInfo.name}",
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const CustomSizedBox(),
              Text(
                "GPA: ${studentInfo.gpa}",
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const CustomSizedBox(),
              Text(
                "Created By: ${studentInfo.createdByInfo.firstname} ${studentInfo.createdByInfo.lastname}",
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const CustomSizedBox(),
              Text(
                "Modified By: ${studentInfo.modifiedByInfo.firstname} ${studentInfo.modifiedByInfo.lastname}",
                style: Theme.of(context).textTheme.bodyLarge,
              ),
              const CustomSizedBox(),
              ElevatedButton(
                onPressed: () {
                  Navigator.of(context).pop();
                },
                child: Text("Close"),
              ),
            ],
          ),
        ),
      ],
    );
  }
}

class CustomSizedBox extends StatelessWidget {
  const CustomSizedBox({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(height: 16);
  }
}

void showStudentDetailsPopup(BuildContext context, Student studentInfo) {
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return StudentDetailsPopup(studentInfo: studentInfo);
    },
  );
}
