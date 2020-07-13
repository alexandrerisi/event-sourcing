package com.example.eventsourcing.coreapi

import com.example.eventsourcing.domain.AccountCurrency
import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.springframework.context.annotation.Profile

//json wrappers
@Profile("command") data class AccountCreateDTO(val startingBalance: Double, val currency: AccountCurrency)
@Profile("command") data class MoneyCreditDTO(val creditAmount: Double)
@Profile("command") data class MoneyDebitDTO(val debitAmount: Double)

//commands
@Profile("command")
data class CreateAccountCmd(@TargetAggregateIdentifier val id: String,
                            val accountBalance: Double,
                            val currency: AccountCurrency)
@Profile("command") data class DebitMoneyCmd(@TargetAggregateIdentifier val id: String, val debitAmount: Double)
@Profile("command") data class CreditMoneyCmd(@TargetAggregateIdentifier val id: String, val creditAmount: Double)

//events
data class AccountActivatedEvt(val id: String)
data class AccountHeldEvt(val id: String)
data class AccountCreatedEvt(val id: String, val accountBalance: Double, val currency: AccountCurrency)
data class MoneyCreditedEvt(val id: String, val creditedAmount: Double)
data class MoneyDebitedEvt(val id: String, val debitedAmount: Double)

//queries
data class GetBankAccountQuery(val id: String)