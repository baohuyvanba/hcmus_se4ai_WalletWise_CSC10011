package com.finance.android.walletwise
import android.app.Application
import com.finance.android.walletwise.model.Transaction.AppContainer
import com.finance.android.walletwise.model.Transaction.AppDataContainer

class WalletWiseApplicatiom: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}