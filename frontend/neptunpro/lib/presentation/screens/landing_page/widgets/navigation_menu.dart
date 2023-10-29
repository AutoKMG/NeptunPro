import 'package:flutter/material.dart';
import 'package:neptunpro/presentation/reusable_widgets/side_navigation_item.dart';

Widget navigationMenu(String selectedPage) {
  return Column(
    children: [
      sideNavigationItem(Icons.dashboard, "Dashboard"),
      const SizedBox(
        height: 15,
      ),
      sideNavigationItem(Icons.school, "Students"),
      const SizedBox(
        height: 15,
      ),
      sideNavigationItem(Icons.person_4_rounded, "Teachers"),
      const SizedBox(
        height: 15,
      ),
      sideNavigationItem(Icons.all_inbox_rounded, "Courses"),
    ],
  );
}
