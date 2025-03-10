CREATE TABLE productservice.category
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NOT NULL,
    is_active     BIT NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    created_at    datetime NOT NULL,
    updated_at    datetime NOT NULL,
    is_active     BIT NOT NULL DEFAULT TRUE,
    `description` VARCHAR(255) NULL,
    image_url     VARCHAR(255) NULL,
    price         DOUBLE NOT NULL,
    category_id   BIGINT NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);