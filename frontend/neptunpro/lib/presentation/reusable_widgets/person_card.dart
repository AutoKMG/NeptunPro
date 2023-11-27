// ignore_for_file: public_member_api_docs, sort_constructors_first, must_be_immutable
import 'package:flutter/material.dart';

import 'package:neptunpro/data/constants/colors.dart';

class PersonCard extends StatelessWidget {
  Column cardContent;
  PersonCard({
    Key? key,
    required this.cardContent,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.only(
        top: 40,
        bottom: 5,
        right: 25,
        left: 25,
      ),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30), color: primaryColor),
      child: cardContent,
    );
  }
}
