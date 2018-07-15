package com.kakaobank.profile.consumer.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaobank.profile.consumer.model.EventType;
import com.kakaobank.profile.consumer.model.dto.log.AccountLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageConverter {
    private final Logger logger = LoggerFactory.getLogger(MessageConverter.class);
    private final ObjectMapper objectMapper;

    public MessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String writeJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public <T> T read(String jsonEventLog, Class<T> valueType) throws IOException {
        return objectMapper.readValue(jsonEventLog, valueType);
    }

    public AccountLogDTO read(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            return convertJsonToAccountLogDTO(jsonNode);
        } catch (IOException ioe) {
            logger.error("JSON parsing error!!! [json : " + json + "]");
        } catch (NullPointerException npe) {
            logger.error("JSON parsing error!!! [json : " + json + "]");
        }

        return null;
    }

    private AccountLogDTO convertJsonToAccountLogDTO(JsonNode jsonNode) {
        if(jsonNode == null)
            return null;

        EventType eventType = EventType.valueOf(getJsonNodeValue(jsonNode, "eventType"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(eventType == EventType.JOIN)
            return getJoinEventLog(jsonNode, eventType, dateTimeFormatter);

        if(eventType == EventType.CREATE)
            return getCreateEventLog(jsonNode, eventType, dateTimeFormatter);

        if(eventType == EventType.DEPOSIT)
            return getDepositOrWithdrawalEventLog(jsonNode, eventType, dateTimeFormatter);

        if(eventType == EventType.WITHDRAWAL)
            return getDepositOrWithdrawalEventLog(jsonNode, eventType, dateTimeFormatter);

        if(eventType == EventType.TRANSFER)
            return getTransferEventLog(jsonNode, eventType, dateTimeFormatter);

        return null;
    }

    private AccountLogDTO getTransferEventLog(JsonNode jsonNode, EventType eventType, DateTimeFormatter dateTimeFormatter) {
        return new AccountLogDTO.AccountLogDTOBuilder()
                .eventType(eventType)
                .customerNumber(Long.parseLong(getJsonNodeValue(jsonNode, "customer_number")))
                .accountNumber(getJsonNodeValue(jsonNode, "transfer_account_number"))
                .receiveBank(getJsonNodeValue(jsonNode, "receive_bank"))
                .receiveAccountNumber(getJsonNodeValue(jsonNode, "receive_account_number"))
                .receiveCustomerName(getJsonNodeValue(jsonNode, "receive_customer_name"))
                .amount(Integer.parseInt(getJsonNodeValue(jsonNode, "amount")))
                .datatime(parseStringToLocalDateTime(dateTimeFormatter, getJsonNodeValue(jsonNode, "datetime")))
                .build();
    }

    private AccountLogDTO getDepositOrWithdrawalEventLog(JsonNode jsonNode, EventType eventType, DateTimeFormatter dateTimeFormatter) {
        return new AccountLogDTO.AccountLogDTOBuilder()
                .eventType(eventType)
                .customerNumber(Long.parseLong(getJsonNodeValue(jsonNode, "customer_number")))
                .accountNumber(getJsonNodeValue(jsonNode, "account_number"))
                .amount(Integer.parseInt(getJsonNodeValue(jsonNode, "amount")))
                .datatime(parseStringToLocalDateTime(dateTimeFormatter, getJsonNodeValue(jsonNode, "datetime")))
                .build();
    }

    private AccountLogDTO getCreateEventLog(JsonNode jsonNode, EventType eventType, DateTimeFormatter dateTimeFormatter) {
        return new AccountLogDTO.AccountLogDTOBuilder()
                .eventType(eventType)
                .customerNumber(Long.parseLong(getJsonNodeValue(jsonNode, "customer_number")))
                .accountNumber(getJsonNodeValue(jsonNode, "account_number"))
                .datatime(parseStringToLocalDateTime(dateTimeFormatter, getJsonNodeValue(jsonNode, "create_dt")))
                .build();
    }

    private AccountLogDTO getJoinEventLog(JsonNode jsonNode, EventType eventType, DateTimeFormatter dateTimeFormatter) {
        return new AccountLogDTO.AccountLogDTOBuilder()
                    .eventType(eventType)
                    .customerNumber(Long.parseLong(getJsonNodeValue(jsonNode, "customer_number")))
                    .customerName(getJsonNodeValue(jsonNode, "customer_name"))
                    .datatime(parseStringToLocalDateTime(dateTimeFormatter, getJsonNodeValue(jsonNode, "join_dt")))
                    .build();
    }

    private LocalDateTime parseStringToLocalDateTime(DateTimeFormatter dateTimeFormatter, String join_dt) {
        return LocalDateTime.parse(join_dt, dateTimeFormatter);
    }

    private String getJsonNodeValue(JsonNode jsonNode, String field) {
        return jsonNode.get(field).asText();
    }
}
