import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/data/constants/colors.dart';
import 'package:neptunpro/logic/handlers/landing/handler.dart';

Widget sideNavigationItem(IconData icon, String label) {
  return BlocBuilder<LandingPageHandler, LandingPageState>(
    builder: (context, state) {
      LandingPageHandler handler = BlocProvider.of(context);
      bool selected = label == handler.selectedPageLabel ? true : false;
      return InkWell(
        onTap: () {
          handler.changePage(label);
        },
        child: Container(
          width: 177,
          height: 38,
          alignment: Alignment.center,
          padding: const EdgeInsets.all(5),
          decoration: BoxDecoration(
            color: selected ? primaryColor : Colors.white,
            borderRadius: BorderRadius.circular(5),
          ),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Icon(
                icon,
                color: selected ? Colors.white : Colors.grey,
              ),
              const SizedBox(
                width: 10,
              ),
              Text(
                label,
                style: TextStyle(
                  fontSize: 16,
                  color: selected ? Colors.white : Colors.grey,
                ),
              ),
            ],
          ),
        ),
      );
    },
  );
}
