import 'package:flutter/material.dart';
import 'package:neptunpro/logic/handlers/students/handler.dart';

class AddStudentDialog extends StatefulWidget {
  final StudentsHandler handler;

  const AddStudentDialog({required this.handler});

  @override
  _AddStudentDialogState createState() => _AddStudentDialogState();
}

class _AddStudentDialogState extends State<AddStudentDialog> {
  final TextEditingController firstNameController = TextEditingController();
  final TextEditingController lastNameController = TextEditingController();
  final TextEditingController programIdController = TextEditingController();
  final TextEditingController gpaController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text('Add New Student'),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextFormField(
            controller: firstNameController,
            decoration: const InputDecoration(labelText: 'First Name'),
            style: const TextStyle(color: Colors.black),
          ),
          TextFormField(
            controller: lastNameController,
            decoration: const InputDecoration(labelText: 'Last Name'),
            style: const TextStyle(color: Colors.black),
          ),
          TextFormField(
            controller: programIdController,
            decoration: const InputDecoration(labelText: 'Program ID'),
            style: const TextStyle(color: Colors.black),
            keyboardType: TextInputType.number,
          ),
        ],
      ),
      actions: [
        ElevatedButton(
          onPressed: () {
            widget.handler.addNewStudent({
              "firstname": firstNameController.text,
              "lastname": lastNameController.text,
              "programId": int.parse(programIdController.text),
            });

            // Close the dialog
            Navigator.of(context).pop();
          },
          child: const Text('Add'),
        ),
        TextButton(
          onPressed: () {
            // Close the dialog without adding a student
            Navigator.of(context).pop();
          },
          child: const Text('Cancel'),
        ),
      ],
    );
  }
}
