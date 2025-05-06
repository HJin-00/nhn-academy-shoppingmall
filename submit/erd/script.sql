CREATE TABLE `users` (
                         `user_id` varchar(50) NOT NULL COMMENT '아이디',
                         `user_name` varchar(50) NOT NULL COMMENT '이름',
                         `user_password` varchar(200) NOT NULL COMMENT 'mysql password 사용',
                         `user_birth` varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                         `user_auth` varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                         `user_point` int NOT NULL DEFAULT '1000000' COMMENT 'default : 1000000',
                         `created_at` datetime NOT NULL COMMENT '가입 일자',
                         `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
                         PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE `products` (
                            `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품ID',
                            `product_name` varchar(200) NOT NULL COMMENT '상품이름',
                            `price` int NOT NULL COMMENT '상품가격',
                            `thumbnail_image` varchar(500) DEFAULT NULL,
                            `detail_image` varchar(500) DEFAULT NULL,
                            `product_description` text NOT NULL COMMENT '상품 설명',
                            `created_at` datetime NOT NULL COMMENT '등록일',
                            `updated_at` datetime DEFAULT NULL COMMENT '상품수정일',
                            `stock` int NOT NULL DEFAULT '0' COMMENT '재고 수량',
                            PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품'

CREATE TABLE `orders` (
                          `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '주문ID',
                          `user_id` varchar(50) NOT NULL COMMENT '회원ID',
                          `used_point` int NOT NULL COMMENT '결재금액(사용포인트)',
                          `earn_point` int NOT NULL COMMENT '적립포인트',
                          `order_status` varchar(20) NOT NULL COMMENT '주문상태( 주문완료,배송대기중...)',
                          `created_at` datetime NOT NULL COMMENT '주문일',
                          PRIMARY KEY (`order_id`),
                          KEY `FK_users_TO_orders_1` (`user_id`),
                          CONSTRAINT `FK_users_TO_orders_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';

CREATE TABLE `address` (
                           `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '주소ID',
                           `user_id` varchar(50) NOT NULL COMMENT '회원ID',
                           `address_name` varchar(100) NOT NULL COMMENT '주소이름',
                           `created_at` datetime DEFAULT NULL COMMENT '등록일',
                           `address_detail` varchar(200) DEFAULT NULL COMMENT '상세주소',
                           PRIMARY KEY (`address_id`),
                           KEY `FK_users_TO_address_1` (`user_id`),
                           CONSTRAINT `fk_address_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주소'

CREATE TABLE `point_history` (
                                 `point_history_id` bigint NOT NULL AUTO_INCREMENT COMMENT '포인트 ID',
                                 `user_id` varchar(50) NOT NULL COMMENT '아이디',
                                 `change_point` int NOT NULL COMMENT '변동포인트(+10000,-50000)',
                                 `point_description` enum('적립','사용') NOT NULL COMMENT '설명(적립, 사용)',
                                 `created_at` datetime NOT NULL COMMENT '포인트이력생성일',
                                 PRIMARY KEY (`point_history_id`),
                                 KEY `FK_users_TO_point_history_1` (`user_id`),
                                 CONSTRAINT `fk_point_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='포인트내역'

CREATE TABLE `cart` (
                        `cart_id` bigint NOT NULL AUTO_INCREMENT COMMENT '장바구니ID',
                        `user_id` varchar(50) NOT NULL COMMENT '회원ID',
                        `created_at` datetime NOT NULL COMMENT '생성일',
                        PRIMARY KEY (`cart_id`),
                        KEY `FK_users_TO_cart_1` (`user_id`),
                        CONSTRAINT `FK_users_TO_cart_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니';

CREATE TABLE `cart_items` (
                              `cart_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '장바구니상품ID',
                              `cart_id` bigint NOT NULL COMMENT '장바구니ID',
                              `product_id` bigint NOT NULL COMMENT '상품ID',
                              `quantity` int NOT NULL COMMENT '수량',
                              PRIMARY KEY (`cart_item_id`,`cart_id`),
                              KEY `FK_cart_TO_cart_items_1` (`cart_id`),
                              KEY `FK_products_TO_cart_items_1` (`product_id`),
                              CONSTRAINT `FK_cart_TO_cart_items_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
                              CONSTRAINT `FK_products_TO_cart_items_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니상품';

CREATE TABLE `order_detail` (
                                `order_detail_id` bigint NOT NULL AUTO_INCREMENT COMMENT '주문상세ID',
                                `order_id` bigint NOT NULL COMMENT '주문ID',
                                `product_id` bigint NOT NULL COMMENT '상품ID',
                                `quantity` int NOT NULL COMMENT '수량',
                                `price` int NOT NULL COMMENT '상품가격',
                                PRIMARY KEY (`order_detail_id`,`order_id`),
                                KEY `FK_orders_TO_order_detail_1` (`order_id`),
                                KEY `FK_products_TO_order_detail_1` (`product_id`),
                                CONSTRAINT `FK_orders_TO_order_detail_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
                                CONSTRAINT `FK_products_TO_order_detail_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문상세';

CREATE TABLE `category` (
                            `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '카테고리ID',
                            `category_name` varchar(100) NOT NULL COMMENT '카테고리이름',
                            PRIMARY KEY (`category_id`),
                            UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리';

CREATE TABLE `product_category` (
                                    `product_id` bigint NOT NULL COMMENT '상품ID',
                                    `category_id` bigint NOT NULL COMMENT '카테고리ID',
                                    PRIMARY KEY (`product_id`,`category_id`),
                                    KEY `FK_category_TO_product_category_1` (`category_id`),
                                    CONSTRAINT `fk_product_category_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE,
                                    CONSTRAINT `fk_product_category_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='카테고리,상품매핑테이블'