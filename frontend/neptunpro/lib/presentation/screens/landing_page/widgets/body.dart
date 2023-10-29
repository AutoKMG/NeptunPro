import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/data/constants/colors.dart';
import 'package:neptunpro/logic/handlers/landing/handler.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';
import 'package:neptunpro/presentation/screens/landing_page/widgets/navigation_menu.dart';
import 'package:neptunpro/presentation/screens/signin.dart';

BlocBuilder<LandingPageHandler, LandingPageState> body() {
    return BlocBuilder<LandingPageHandler, LandingPageState>(
            builder: (context, state) {
              LandingPageHandler handler = BlocProvider.of(context);
              return Expanded(
                child: Row(
                  children: [
                    Container(
                      width: MediaQuery.of(context).size.width * 0.12,
                      padding: const EdgeInsets.symmetric(vertical: 20),
                      child: Column(
                        mainAxisSize: MainAxisSize.max,
                        children: [
                          navigationMenu(handler.selectedPageLabel),
                          const Expanded(
                            child: SizedBox(),
                          ),
                          Padding(
                            padding:
                                const EdgeInsets.symmetric(horizontal: 25),
                            child: Container(
                              height: 2,
                              decoration: const BoxDecoration(
                                color: primaryColor,
                              ),
                            ),
                          ),
                          Container(
                            alignment: Alignment.centerLeft,
                            padding: const EdgeInsets.only(left: 25),
                            child: TextButton.icon(
                              label: const Text(
                                "Sign out",
                                style: TextStyle(color: primaryColor, fontSize: 16,),
                              ),
                              icon: const Icon(
                                Icons.exit_to_app_outlined,
                                color: primaryColor,
                                size: 25,
                              ),
                              onPressed: () {
                                CacheHelper.removeData(key: "token");
                                Navigator.pushReplacement(context,
                                    MaterialPageRoute(builder: (context) {
                                  return const SigninScreen();
                                }));
                              },
                              style: TextButton.styleFrom(
                                  padding: EdgeInsets.zero),
                            ),
                          ),
                        ],
                      ),
                    ),
                    Container(
                      width: 2,
                      decoration: const BoxDecoration(
                        color: primaryColor,
                      ),
                    ),
                    Expanded(
                        child: Center(
                      child: handler.selectedPageWidget,
                    ))
                  ],
                ),
              );
            },
          );
  }