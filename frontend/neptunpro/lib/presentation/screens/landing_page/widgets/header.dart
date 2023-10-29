import 'package:flutter/material.dart';
import 'package:neptunpro/data/constants/colors.dart';

SizedBox header() {
    return SizedBox(
            width: double.infinity,
            height: 60,
            child: Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10),
              child: Row(
                children: [
                  Container(
                    width: 40,
                    height: 40,
                    decoration: const BoxDecoration(
                      image: DecorationImage(
                          image: AssetImage("assets/images/neptun-pro.png"),
                          fit: BoxFit.cover),
                    ),
                  ),
                  const SizedBox(
                    width: 10,
                  ),
                  const Text("NeptunPro", style: TextStyle(fontSize: 32)),
                  const Expanded(child: SizedBox()),
                  const Icon(Icons.person, size: 40),
                  const SizedBox(
                    width: 10,
                  ),
                  const Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Teacher",
                        style: TextStyle(
                          fontSize: 20,
                          color: primaryColor
                        ),
                      ),
                      SizedBox(
                        height: 5,
                      ),
                      Text(
                        "Khaled Saleh",
                        style: TextStyle(
                          fontSize: 16,
                          color: primaryColor
                        ),
                      )
                    ],
                  ),
                ],
              ),
            ),
          );
  }
