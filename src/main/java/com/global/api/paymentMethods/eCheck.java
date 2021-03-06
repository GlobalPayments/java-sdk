package com.global.api.paymentMethods;

import com.global.api.builders.AuthorizationBuilder;
import com.global.api.builders.ManagementBuilder;
import com.global.api.entities.Transaction;
import com.global.api.entities.enums.*;
import com.global.api.entities.exceptions.ApiException;
import com.global.api.entities.exceptions.GatewayException;
import com.global.api.entities.exceptions.UnsupportedTransactionException;

import java.math.BigDecimal;

public class eCheck implements IPaymentMethod, IChargable, ITokenizable {
    private String accountNumber;
    private AccountType accountType;
    private boolean achVerify;
    private int birthYear;
    private String checkHolderName;
    private String checkName;
    private String checkNumber;
    private CheckType checkType;
    private boolean checkVerify;
    private String driversLicenseNumber;
    private String driversLicenseState;
    private EntryMethod entryMode = EntryMethod.Manual;
    private String micrNumber;
    private PaymentMethodType paymentMethodType = PaymentMethodType.ACH;
    private String phoneNumber;
    private String routingNumber;
    private SecCode secCode;
    private String ssnLast4;
    private String token;
    private String bankName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isAchVerify() {
        return achVerify;
    }

    public void setAchVerify(boolean achVerify) {
        this.achVerify = achVerify;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getCheckHolderName() {
        return checkHolderName;
    }

    public void setCheckHolderName(String checkHolderName) {
        this.checkHolderName = checkHolderName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public boolean isCheckVerify() {
        return checkVerify;
    }

    public void setCheckVerify(boolean checkVerify) {
        this.checkVerify = checkVerify;
    }

    public String getDriversLicenseNumber() {
        return driversLicenseNumber;
    }

    public void setDriversLicenseNumber(String driversLicenseNumber) {
        this.driversLicenseNumber = driversLicenseNumber;
    }

    public String getDriversLicenseState() {
        return driversLicenseState;
    }

    public void setDriversLicenseState(String driversLicenseState) {
        this.driversLicenseState = driversLicenseState;
    }

    public EntryMethod getEntryMode() {
        return entryMode;
    }

    public void setEntryMode(EntryMethod entryMode) {
        this.entryMode = entryMode;
    }

    public String getMicrNumber() {
        return micrNumber;
    }

    public void setMicrNumber(String micrNumber) {
        this.micrNumber = micrNumber;
    }

    public PaymentMethodType getPaymentMethodType() {
        return paymentMethodType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public SecCode getSecCode() {
        return secCode;
    }

    public void setSecCode(SecCode secCode) {
        this.secCode = secCode;
    }

    public String getSsnLast4() {
        return ssnLast4;
    }

    public void setSsnLast4(String ssnLast4) {
        this.ssnLast4 = ssnLast4;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String value) {
        this.bankName = value;
    }

    public AuthorizationBuilder charge() {
        return charge(null);
    }

    public AuthorizationBuilder charge(BigDecimal amount) {
        return new AuthorizationBuilder(TransactionType.Sale, this).withAmount(amount);
    }

    public boolean deleteToken() {
        return deleteToken("default");
    }

    public boolean deleteToken(String configName) {
        try {
            new ManagementBuilder(TransactionType.TokenDelete)
                .withPaymentMethod(this)
                .execute(configName);
            return true;
        } catch (ApiException ex) {
            return false;
        }
    }

    @Override
    public ITokenizable detokenize(String configName) throws UnsupportedTransactionException {
        throw new UnsupportedTransactionException();
    }

    public AuthorizationBuilder verify() {
        return new AuthorizationBuilder(TransactionType.Verify, this)
            .withRequestMultiUseToken(true);
    }

    public String tokenize() {
        return tokenize(true, "default");
    }

    public String tokenize(boolean verifyAccount) {
        return tokenize(verifyAccount, "default");
    }

    public String tokenize(String configName) {
        return tokenize(true, configName);
    }

    public String tokenize(boolean verifyAccount, String configName) {
        try {
            Transaction result = verify().execute(configName);
            return result.getToken();
        } catch (ApiException ex) {
            return null;
        }
    }

    public boolean updateTokenExpiry() throws UnsupportedTransactionException {
        return updateTokenExpiry("default");
    }
    
    public boolean updateTokenExpiry(String configName) throws UnsupportedTransactionException {
        throw new UnsupportedTransactionException();
    }
}
