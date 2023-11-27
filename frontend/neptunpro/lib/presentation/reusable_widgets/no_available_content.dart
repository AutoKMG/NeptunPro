import 'package:flutter/material.dart';

class NoAvailableContent extends StatelessWidget {
  const NoAvailableContent({super.key});

  @override
  Widget build(BuildContext context) {
    return const Column(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Icon(
          Icons.error_outline_sharp,
          size: 100,
          color: Colors.red,
        ),
        Text(
          "There is no available content for this page",
          style: TextStyle(fontSize: 20),
        ),
      ],
    );
  }
}
