CREATE TABLE ORDERS
(
    cod                     BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    payment_transaction_cod BIGINT                            NOT NULL UNIQUE REFERENCES PAYMENT_TRANSACTION,
    address_type            VARCHAR(7)                        NOT NULL,
    shipping_address_cod    BIGINT                            NOT NULL REFERENCES ADDRESS,
    recipient_cod           BIGINT                            NOT NULL REFERENCES RECIPIENT,
    billing_info_cod        BIGINT REFERENCES BILLING_INFO,
    billing_address_cod     BIGINT REFERENCES ADDRESS,
    invoice_year            NUMBER                            NULL,
    invoice_number          NUMBER                            NULL,
    subtotal                DOUBLE                            NOT NULL,
    shipment                DOUBLE                            NOT NULL,
    UNIQUE (invoice_year, invoice_number)
);

CREATE TABLE ORDER_ELEMENT
(
    cod                BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    order_cod          BIGINT REFERENCES ORDERS          NOT NULL,
    item_cod           BIGINT REFERENCES ITEM            NOT NULL,
    selected_size_cod  BIGINT REFERENCES SIZE,
    selected_color_cod BIGINT REFERENCES COLOR,
    quantity           INT                               NOT NULL
);
