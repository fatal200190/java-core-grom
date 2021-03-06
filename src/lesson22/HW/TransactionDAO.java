package lesson22.HW;

import lesson22.HW.exception.BadRequestException;
import lesson22.HW.exception.InternalServerException;
import lesson22.HW.exception.LimitExceeded;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 17.11.2017.
 */
public class TransactionDAO {
    private static Transaction[] transactions = new Transaction[10];

    public static Transaction save(Transaction transaction) throws Exception {

        validate(transaction);

        int index = 0;
        for (Transaction tr : transactions){
            if (tr == null){
                transactions[index] = transaction;
                return transactions[index];
            }
            index++;
        }
        throw new InternalServerException("Out of bound. Transaction id "+ transaction.getId()+ " can't save.");
    }


        public static Transaction[] transactionList() {
        int count = 0;
        for (Transaction tr : transactions){
            if (tr != null)
                count++;
        }

        Transaction[]allTransactions = new Transaction[count];

        int index = 0;
        for (Transaction tr : transactions){
            if (tr != null){
                allTransactions[index] = tr;
                index++;
            }
        }

        return allTransactions;
    }

    public static Transaction[] transactionList(String city) {

        int count = 0;
        for (Transaction tr : transactions) {
            if (tr != null && tr.getCity().equals(city))
                count++;
        }

        Transaction[] transactionsPerCity = new Transaction[count];

        int index = 0;
        for (Transaction tr : transactions) {
            if (tr != null && tr.getCity().equals(city)){
                transactionsPerCity[index] = tr;
            index++;
            }
        }

        return transactionsPerCity;
    }

    public static Transaction[] transactionList(int amount) {


        int count = 0;
        for (Transaction tr : transactions) {
            if (tr != null && tr.getAmount() == amount)
            count++;
        }

        Transaction[] transactionsPerAmount = new Transaction[count];

        int index = 0;

        for (Transaction tr : transactions) {
            if (tr != null && tr.getAmount() == amount){
                transactionsPerAmount[index] = tr;
                index++;
            }
        }

        return transactionsPerAmount;
    }

    private static void validate(Transaction transaction) throws Exception {


        for (Transaction tr : transactions){
            if (tr != null && tr.equals(transaction)){
                throw new BadRequestException("Transaction id: "+ transaction.getId() + " is already exist");
            }
        }


        if (transaction.getAmount() > Utils.getLimitSimpleTransactionAmount())
            throw new LimitExceeded("Transaction limit exceeded " + transaction.getId() + ". Cant't be saved.");

        int sum = 0;
        int count = 0;
        for (Transaction tr : getTransactionsPerDey(transaction.getDateCreated())) {
            sum += tr.getAmount();
            count++;
        }
        if ((sum + transaction.getAmount()) > Utils.getLimitTransactionsPerDayAmount()) {
            throw new LimitExceeded("Transaction limit per day amount exceeded " + transaction.getId() + ". Cant't be saved.");
        }

        if (count >= Utils.getLimitTransactionsPerDayCount()) {
            throw new LimitExceeded("Transaction limit per day count exceeded " + transaction.getId() + ". Cant't be saved.");
        }

        int cityCount = 0;
        for (String city : Utils.getCities()) {
            if (city == transaction.getCity())
                cityCount++;
        }
        if (cityCount == 0) {
            throw new BadRequestException("Transaction " + transaction.getId() + " is impossible from this city " + transaction.getCity() + ". Cant't be saved.");
        }

    }


    private static Transaction[] getTransactionsPerDey(Date dateOfCurTransaction) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfCurTransaction);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int count = 0;
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                calendar.setTime(transaction.getDateCreated());
                int trMonth = calendar.get(Calendar.MONTH);
                int trDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (trMonth == month && trDay == day)
                    count++;
            }
        }

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                calendar.setTime(transaction.getDateCreated());
                int trMonth = calendar.get(Calendar.MONTH);
                int trDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (trMonth == month && trDay == day) {
                    result[index] = transaction;
                    index++;
                }
            }
        }
        return result;
    }

    public TransactionDAO(Transaction[] transactions) {
        TransactionDAO.transactions = transactions;
    }

    public static Transaction[] getTransactions() {
        return transactions;
    }

    public TransactionDAO() {

    }
}
