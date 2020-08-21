package com.example.eventsourcing.coreapi

import com.example.eventsourcing.domain.AccountCurrency
import org.axonframework.modelling.command.TargetAggregateIdentifier

//json wrappers
data class AccountCreateDTO(val startingBalance: Double, val currency: AccountCurrency)
data class MoneyCreditDTO(val creditAmount: Double)
data class MoneyDebitDTO(val debitAmount: Double)

//commands
data class CreateAccountCmd(@TargetAggregateIdentifier val id: String,
                            val accountBalance: Double,
                            val currency: AccountCurrency)
data class DebitMoneyCmd(@TargetAggregateIdentifier val id: String, val debitAmount: Double)
data class CreditMoneyCmd(@TargetAggregateIdentifier val id: String, val creditAmount: Double)

//events
data class AccountActivatedEvt(val id: String)
data class AccountHeldEvt(val id: String)
data class AccountCreatedEvt(val id: String, val accountBalance: Double, val currency: AccountCurrency)
data class MoneyCreditedEvt(val id: String, val creditedAmount: Double)
data class MoneyDebitedEvt(val id: String, val debitedAmount: Double)

//queries
data class GetBankAccountQuery(val id: String)
data class GetAccountAggregateQuery(val id: String)