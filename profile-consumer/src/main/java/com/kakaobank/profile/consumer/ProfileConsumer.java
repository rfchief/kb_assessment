package com.kakaobank.profile.consumer;

import com.kakaobank.profile.consumer.component.MessageConverter;
import com.kakaobank.profile.consumer.controller.ReadAccountProfileController;
import com.kakaobank.profile.consumer.controller.ReadCustomerProfileController;
import com.kakaobank.profile.consumer.dao.AccountDao;
import com.kakaobank.profile.consumer.dao.CustomerDao;
import com.kakaobank.profile.consumer.factory.BeanFactory;
import com.kakaobank.profile.consumer.service.ReadAccountProfileService;
import com.kakaobank.profile.consumer.service.ReadCustomerProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.exception;
import static spark.Spark.get;

public class ProfileConsumer {
    private final static Logger logger = LoggerFactory.getLogger(ProfileConsumer.class);

    public static void main(String[] args) {
        registerApiUris();

        exception(Exception.class, (e, req, res) -> {
            String message = e.getClass().getName() + ": " + e.getMessage();
            logger.error(message);
            res.type("application/json");
            res.status(500);
            res.body(message);
        });
    }

    private static void registerApiUris() {
        AccountDao accountDao = BeanFactory.createAccountDao();
        CustomerDao customerDao = BeanFactory.createCustomerDao();
        ReadCustomerProfileController readCustomerProfileController = getReadCustomerProfileController(customerDao, accountDao);
        ReadAccountProfileController readAccountProfileController = getReadAccountProfileController(accountDao);

        get("/api/customer/:customerNumber", (req, res) -> {
            long customerNumber = Long.parseLong(req.params(":customerNumber"));

            return readCustomerProfileController.getCustomerProfile(customerNumber);
        });

        get("/api/customer/:customerNumber/account/:accountNumber", (req, res) -> {
            long customerNumber = Long.parseLong(req.params(":customerNumber"));
            String accountNumber = req.params(":accountNumber");
            int pageIndex = Integer.parseInt(req.queryParams("pageIndex"));
            int pageSize = Integer.parseInt(req.queryParams("pageSize"));

            return readAccountProfileController.getAccountProfile(customerNumber, accountNumber, pageIndex, pageSize);
        });
    }

    private static ReadAccountProfileController getReadAccountProfileController(AccountDao accountDao) {
        ReadAccountProfileService service = BeanFactory.createReadAccountProfileService(accountDao);
        MessageConverter messageConverter = BeanFactory.createMessageConverter();

        return BeanFactory.createReadAccountProfileController(service, messageConverter);
    }

    private static ReadCustomerProfileController getReadCustomerProfileController(CustomerDao customerDao, AccountDao accountDao) {
        ReadCustomerProfileService service = BeanFactory.createReadCustomerProfileService(customerDao, accountDao);
        MessageConverter messageConverter = BeanFactory.createMessageConverter();

        return BeanFactory.createReadCustomerProfileController(service, messageConverter);
    }
}
