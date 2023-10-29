package com.sankets.expensetracker.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sankets.expensetracker.presentation.ui.theme.Background
import com.sankets.expensetracker.presentation.ui.theme.PrimaryText
import com.sankets.expensetracker.presentation.ui.theme.fonts


@Composable
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier
){

    Scaffold(backgroundColor = Background) {

        Column(
            modifier = modifier
                .fillMaxWidth()
        ) {

            Spacer(modifier = modifier.height(16.dp))
            Text(
                text = "Privacy Policy\n" +
                        "\n" +
                        "At Expense Log, we are committed to protecting your privacy and ensuring the security of your personal information. This Privacy Policy outlines how we collect, use, and safeguard the information you provide when using our Expense Log mobile application (\"the App\").\n" +
                        "\n" +
                        "1. Information Collection: We do not collect any personal information from you when you use the Expense Log App. The App does not require any registration or login, and we do not ask for any personally identifiable information such as your name, address, email, or phone number.\n" +
                        "2. SMS Access: The Expense Log App requires access to your SMS messages in order to analyze and extract relevant information related to your expenses. This includes parsing SMS notifications from your mobile service provider, payment gateways, and other financial institutions. However, please note that we do not store or transmit any of your SMS data. All analysis and tracking of expenses are performed locally on your device.\n" +
                        "3. Data Storage and Security: Expense Log App operates as a 100 percent offline application. This means that all your data, including your expenses and any extracted information from SMS messages, is stored locally on your device and is not shared with any third parties or our servers. We do not have access to your data, and it remains completely under your control.\n" +
                        "4. Third-Party Services: The Expense Log App does not integrate with any third-party services or require you to link your bank accounts or financial institutions. We do not share your information with any third-party advertisers, analytics providers, or other service providers.\n" +
                        "5. Children's Privacy: Expense Log is intended for use by individuals who are 18 years of age or older.\n" +
                        "6. Changes to the Privacy Policy: We reserve the right to modify or amend this Privacy Policy at any time. Any changes will be effective immediately upon posting the updated Privacy Policy within the Expense Log App. Your continued use of the App after any changes indicates your acceptance of the revised Privacy Policy.\n" +
                        "7. Data Accuracy: While we strive to present accurate financial statistics, it is essential to note that our app is currently undergoing active development. As a result, the data displayed may occasionally be inaccurate or incomplete. We encourage users to use this app for informational purposes only and consult with financial experts for more precise and up-to-date information.\n" +
                        "By using the Expense Log App, you acknowledge that you have read and understood this Privacy Policy and agree to the above \n" +
                        "\n" +
                        "Contact Us\n" +
                        "\n" +
                        "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact\n",
                color = PrimaryText,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
            )
        }
    }

}