package com.finance.android.walletwise

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.finance.android.walletwise.model.user.UserPreferences
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.activity.*
import com.finance.android.walletwise.ui.activity.category.ScreenAddCategory
import com.finance.android.walletwise.ui.activity.transaction.EditScreenExpense
import com.finance.android.walletwise.ui.activity.transaction.ListExpenseScreen
import com.finance.android.walletwise.ui.activity.transaction.ScreeneAddExpense
import com.finance.android.walletwise.ui.activity.transaction.TransactionEditDestination
import com.finance.android.walletwise.ui.activity.user.EnterPinScreen
import com.finance.android.walletwise.ui.activity.user.LoginScreen
import com.finance.android.walletwise.ui.activity.user.ProfileSetupScreen
import com.finance.android.walletwise.ui.activity.user.SetupPinScreen
import com.finance.android.walletwise.ui.activity.user.SignupScreen
import com.finance.android.walletwise.ui.activity.user.WelcomeScreen
import com.finance.android.walletwise.ui.screen.CategoryListScreen
import com.finance.android.walletwise.ui.viewmodel.transaction.TransactionsScreenViewModel
import com.finance.android.walletwise.ui.viewmodel.user.AuthenticationViewModel
import com.finance.android.walletwise.ui.viewmodel.user.PinViewModel
import com.finance.android.walletwise.ui.viewmodel.user.UserProfileViewModel

//Control application navigation with NavHost
@Composable
fun WalletWiseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authenticationViewModel: AuthenticationViewModel? = null,
    userProfileViewModel: UserProfileViewModel? = null,
    pinViewModel: PinViewModel? = null,)
{
    val context = LocalContext.current
    var startDestination by rememberSaveable { mutableStateOf(
        if (UserPreferences.isFirstTimeLaunch(context)) welcomeScreen.route else pinVerificationScreen.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier, )
    {
        /**
         * WELCOME =================================================================================
         */
        //WelcomeScreen ----------------------------------------------------------------------------
        composable(
            route = welcomeScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))  //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            }, )
        {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigateSingleTopTo(loginScreen.route)
                },
                onSignupClick = {
                    navController.navigateSingleTopTo(signupScreen.route) }
            )
        }
        //LoginScreen ------------------------------------------------------------------------------
        composable(
            route = loginScreen.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300), )     //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Back Animation
            }, )
        {
            LoginScreen(
                onSignUpClick = {
                    navController.navigateSingleTopTo(signupScreen.route)
                },
                navigateToHome = {
                    navController.navigateSingleTopTo(pinSetupScreen.route)
                },
                authenticationViewModel = authenticationViewModel,
            )
        }
        //SignupScreen -----------------------------------------------------------------------------
        composable(
            route = signupScreen.route,
            enterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(300), )     //Open Animation
            },
            exitTransition = {
                return@composable fadeOut(tween(300)) //Close Animation
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Re-open Animation
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300))       //Back Animation
            }, )
        {
            SignupScreen(
                navigateToProfile = {
                    navController.navigateSingleTopTo(profileSetupScreen.route)
                },
                onLoginClick = {
                    navController.navigateSingleTopTo(loginScreen.route)
                },
                authenticationViewModel = authenticationViewModel,
            )
        }

        /**
         * SETUP PROFILE ===========================================================================
         */
        //SetupProfileScreen
        composable(
            route = profileSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, )
        {
            ProfileSetupScreen(
                navigateToHome = {
                    navController.navigateSingleTopTo(pinSetupScreen.route)
                },
                userProfileViewModel = userProfileViewModel,
            )
        }
        //SetupPinScreen
        composable(
            route = pinSetupScreen.route,
            enterTransition = {
                return@composable fadeIn(tween(300))
            },
            exitTransition = {
                return@composable fadeOut(tween(300))
            },
            popEnterTransition = {
                return@composable slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(300)
                )
            }, )
        {
            SetupPinScreen(
                onNavigateHome = {
                    navController.navigateSingleTopTo(pinVerificationScreen.route)
                },
                pinViewModel = pinViewModel,
            )
        }
        /**
         * Enter PIN ===============================================================================
         */
        //EnterPinScreen
        composable(
            route = pinVerificationScreen.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                scaleOut(tween(500))
            }, )
        {
            EnterPinScreen(
                onNavigateHome = {
                    navController.navigate(homeScreen.route){
                        popUpTo(pinVerificationScreen.route) { inclusive = true }
                    }
                },
                pinViewModel = pinViewModel,
                userProfileViewModel = userProfileViewModel,
            )
        }
        /**
         * MAIN APP SCREENS ========================================================================
         */
        //HomeScreen ===============================================================================
        composable(
            route = homeScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            HomeScreen(
                onClickAddManual = {},
                onClickAddOCR = {},
                onClickAddText = {},
                quickAccessOnAnalysisClick = {},
                quickAccessOnAIChatClick = { navController.navigate(chatbotScreen.route) },
                quickAccessOnRemindClick = {}, )
        }

        //TransactionScreen ========================================================================
        //TransactionsListScreen
        composable(
            route = transactionsListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            ListExpenseScreen(navController = navController)
        }
        //TransactionAddScreen
        composable(
            route = addTransactionScreen.route, )
        {
            ScreeneAddExpense(navigateBack = { navController.popBackStack() })
        }
        //TransactionEditScreen
        composable(
            route = TransactionEditDestination.routeWithArgs,
            arguments = listOf(navArgument(TransactionEditDestination.transactionIdArg){ type= NavType.IntType })
        )
        {
            EditScreenExpense(
                onBackClick = { navController.popBackStack() }
            )
        }

        //CategoryScreen ===========================================================================
        //CategoriesListScreen
        composable(
            route = categoriesListScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            CategoryListScreen(
                navController = navController,)
        }
        //CategoryAddScreen
        composable(
            route = addCategoryScreen.route, )
        {
            ScreenAddCategory(navigateBack = { navController.popBackStack() })
        }

        //SettingScreen ============================================================================
        composable(
            route = settingScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            SettingScreen()
        }

        composable(
            route = chatbotScreen.route,
            enterTransition = { expandIn(
                animationSpec = tween(300),
                expandFrom =  Alignment.CenterStart, )
            },
            exitTransition = { fadeOut(
                animationSpec = tween(300), )
            }, )
        {
            ChatScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

//Navigation FUNCTION
fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route)
{
    //Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack as users select items
    popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
    {
        saveState = true
    }
    //Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    //Restore state when re-selecting a previously selected item
    restoreState = true
}

//@Composable
//fun homescreen(
//    viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
//    navController: NavController
//) {
//    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
//    var totalIncome = 0.0
//    var totalExpense = 0.0
//
//    transactionScreenUiState.transactionList.forEach { transaction ->
//        if (transaction.type == "Expense") {
//            totalExpense += transaction.amount
//        } else {
//            totalIncome += transaction.amount
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFFFF7F2))
//    ) {
//        Column {
//            BalanceCard(
//                currency = "VND",
//                totalIncome = totalIncome,
//                totalExpense = totalExpense,
//            )
//            //topbox(totalExpense = totalExpense, totalIncome = totalIncome, navController = navController)
//            LazyColumn(modifier = Modifier, )
//            {
//                //QUICK ACCESS BAR
//                item {
//                    QuickAccessBar(
//                        onAnalysisClick = { },
//                        onAIChatClick   = { },
//                        onRemindClick   = { },
//                    )
//                }
//                //Divider
//                item {
//                    HorizontalDivider(
//                        modifier = Modifier
//                            .padding(start = 8.dp, end = 8.dp)
//                            .padding(top = 16.dp, bottom = 16.dp),
//                        thickness = 1.dp, color = Color.Gray.copy(alpha = 0.5f)
//                    )
//                }
//                //QUICK VIEW OF TRANSACTIONS LIST
//                //item {
//                //    ExpenseList(expenses = listOf("Coffee - $5", "Groceries - $30", "Dinner - $20")) // Replace with actual data
//                //}
//
//                //QUICK VIEW OF CATEGORIES LIST
//                //item {
//                //    CategoryList(categories = listOf("Food", "Entertainment", "Transport")) // Replace with actual data
//                //}
//            }
//        }
//
//
//    }
//}
//
//@Composable
//fun topbox(totalExpense:Double, totalIncome:Double, navController: NavController)
//{
//    Box(
//        modifier = Modifier
//            .clip(
//                RoundedCornerShape(
//                    bottomStart = 32.dp,
//                    bottomEnd = 32.dp
//                )
//            )
//            .height(312.dp)
//            .fillMaxWidth()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFECD8B2),
//                        Color(0x73F8EDD8)
//                    )
//                )
//            )
//
//    ) {
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//
//            Text(
//                text = "Account Balance",
//                fontSize = 17.sp,
//                color = Color(0xFF91919F)
//            )
//            Text(
//                text = (totalIncome - totalExpense).toInt().toString(),
//                fontSize = 48.sp,
//                modifier = Modifier.padding(bottom = 5.dp),
//                fontWeight = FontWeight.Bold,
//                color = Color(0xFF161719)
//
//            )
//
//
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier
//                    .padding(15.dp)
//                    .fillMaxWidth()
//            ) {
//                Box(
//                    modifier = Modifier
//                        .height(80.dp)
//                        .width(155.dp)
//                        .clip(RoundedCornerShape(25.dp))
//                        .background(Color(0xFF00A86B))
//                        .padding(6.dp)
//                        .clickable {
//                            navController.navigate("IncomeScreen")
//                        }
//
//
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.Start
//                    ) {
//
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_analytics_filled),
//                            contentDescription = "Profit Icon",
//                            modifier = Modifier
//                                .padding(10.dp)
//                        )
//
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Text(
//                                text = "Income",
//                                color = Color(0xFfFCFCFC),
//                                fontSize = 18.sp,
//                                modifier = Modifier.padding(top = 10.dp)
//                            )
//                            Text(
//                                text = totalIncome.toInt().toString(),
//                                color = Color(0xFfFCFCFC),
//                                //fontSize = 22.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//
//
//                    }
//                }
//                Box(
//                    modifier = Modifier
//                        .height(80.dp)
//                        .width(155.dp)
//                        .clip(RoundedCornerShape(25.dp))
//                        .background(Color(0xFFFD3C4A))
//                        .padding(6.dp)
//                        .clickable {
//                            navController.navigate("ExpenseScreen")
//                        }
//                ) {
//                    Row(
//                        horizontalArrangement = Arrangement.Start
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_remind_event),
//                            contentDescription = "Loss Icon",
//                            modifier = Modifier
//                                .padding(10.dp)
//                        )
//
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Text(
//                                text = "Expense",
//                                color = Color(0xFfFCFCFC),
//                                fontSize = 18.sp,
//                                modifier = Modifier.padding(top = 10.dp)
//                            )
//                            Text(
//                                text = totalExpense.toInt().toString(),
//                                color = Color(0xFfFCFCFC),
//                                //fontSize = 22.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//
//                    }
//                }
//            }
//        }
//    }
//}