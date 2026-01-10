-- =========================================================
-- Pizzeria di Roma (schema) - MySQL 5.5
-- =========================================================

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;

DROP TABLE IF EXISTS cart_items;
DROP TABLE IF EXISTS carts;

DROP TABLE IF EXISTS pizza_tags;
DROP TABLE IF EXISTS pizza_ingredients;
DROP TABLE IF EXISTS pizza_available_sizes;

DROP TABLE IF EXISTS drinks;
DROP TABLE IF EXISTS pizzas;
DROP TABLE IF EXISTS pizza_sizes;
DROP TABLE IF EXISTS ingredients;
DROP TABLE IF EXISTS tags;

DROP TABLE IF EXISTS user_addresses;
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS company_info;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- USERS
-- ----------------------------
CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       first_name VARCHAR(20) NOT NULL,
                       last_name VARCHAR(80) NOT NULL,

                       email VARCHAR(255) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       password VARCHAR(255) NOT NULL,

                       role VARCHAR(10) NOT NULL DEFAULT 'USER',
                       status VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',

                       hired_at DATETIME NULL,
                       is_on_shift TINYINT(1) NOT NULL DEFAULT 0,

                       last_login_at DATETIME NULL,
                       deactivated_at DATETIME NULL,

                       verification_token VARCHAR(120) NULL,
                       reset_token VARCHAR(120) NULL,

                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,

                       PRIMARY KEY (id),
                       UNIQUE KEY uk_users_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- USER ADDRESSES
-- ----------------------------
CREATE TABLE user_addresses (
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                user_id BIGINT NULL,

                                label VARCHAR(80) NULL,
                                street VARCHAR(160) NOT NULL,
                                house_number VARCHAR(40) NOT NULL,
                                city VARCHAR(120) NOT NULL,
                                postal_code VARCHAR(10) NOT NULL,
                                country VARCHAR(80) NOT NULL,

                                is_default TINYINT(1) NOT NULL DEFAULT 0,

                                created_at DATETIME NOT NULL,
                                updated_at DATETIME NOT NULL,

                                PRIMARY KEY (id),
                                KEY idx_user_addresses_user_id (user_id),
                                CONSTRAINT fk_user_addresses_user
                                    FOREIGN KEY (user_id) REFERENCES users(id)
                                        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- COMPANY INFO
-- ----------------------------
CREATE TABLE company_info (
                              id INT NOT NULL AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL,
                              description VARCHAR(2000) NOT NULL,

                              phone VARCHAR(30) NULL,
                              email VARCHAR(100) NULL,
                              address VARCHAR(100) NULL,

                              facebook_url VARCHAR(200) NULL,
                              instagram_url VARCHAR(200) NULL,
                              twitter_url VARCHAR(200) NULL,

                              dph_rate DECIMAL(10,2) NOT NULL,
                              free_delivery_from DECIMAL(10,2) NOT NULL,
                              delivery_price DECIMAL(10,2) NOT NULL,
                              extra_ingredient_price DECIMAL(10,2) NOT NULL,

                              created_at DATETIME NOT NULL,
                              updated_at DATETIME NOT NULL,

                              PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- TAGS
-- ----------------------------
CREATE TABLE tags (
                      id INT NOT NULL AUTO_INCREMENT,
                      name VARCHAR(50) NOT NULL,
                      emoji VARCHAR(50) NOT NULL,
                      PRIMARY KEY (id),
                      UNIQUE KEY uk_tags_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- PIZZA SIZES
-- ----------------------------
CREATE TABLE pizza_sizes (
                             id INT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(255) NOT NULL,
                             size_label VARCHAR(255) NOT NULL,
                             price_extra DECIMAL(5,2) NOT NULL,
                             description_en TEXT NULL,
                             description_sk TEXT NUll,
                             description_it TEXT NULL,
                             created_at DATETIME NOT NULL,
                             updated_at DATETIME NOT NULL,

                             PRIMARY KEY (id),
                             UNIQUE KEY uk_pizza_sizes_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- INGREDIENTS
-- ----------------------------
CREATE TABLE ingredients (
                             id INT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(200) NOT NULL,
                             emoji VARCHAR(50) NOT NULL,
                             extra TINYINT(1) NOT NULL,

                             created_at DATETIME NOT NULL,
                             updated_at DATETIME NOT NULL,

                             PRIMARY KEY (id),
                             UNIQUE KEY uk_ingredients_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- PIZZAS
-- ----------------------------
CREATE TABLE pizzas (
                        id INT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        slug VARCHAR(255) NOT NULL,
                        description VARCHAR(2000) NOT NULL,

                        image_url VARCHAR(500) NULL,

                        base_price DECIMAL(10,2) NOT NULL,
                        is_available TINYINT(1) NOT NULL DEFAULT 1,
                        is_featured TINYINT(1) NOT NULL DEFAULT 0,

                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NOT NULL,

                        PRIMARY KEY (id),
                        UNIQUE KEY uk_pizzas_slug (slug)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- DRINKS
-- ----------------------------
CREATE TABLE drinks (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        name VARCHAR(255) NULL,
                        volume_ml INT NULL,
                        is_alcoholic TINYINT(1) NULL,
                        price DECIMAL(10,2) NULL,
                        image_url VARCHAR(255) NULL,
                        is_available TINYINT(1) NULL,

                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NOT NULL,

                        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- JOIN TABLES
-- ----------------------------
CREATE TABLE pizza_tags (
                            pizza_id INT NOT NULL,
                            tag_id INT NOT NULL,
                            PRIMARY KEY (pizza_id, tag_id),
                            KEY idx_pizza_tags_tag_id (tag_id),
                            CONSTRAINT fk_pizza_tags_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE,
                            CONSTRAINT fk_pizza_tags_tag FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE pizza_ingredients (
                                   pizza_id INT NOT NULL,
                                   ingredient_id INT NOT NULL,
                                   PRIMARY KEY (pizza_id, ingredient_id),
                                   KEY idx_pizza_ingredients_ingredient_id (ingredient_id),
                                   CONSTRAINT fk_pizza_ing_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE,
                                   CONSTRAINT fk_pizza_ing_ing FOREIGN KEY (ingredient_id) REFERENCES ingredients(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE pizza_available_sizes (
                                       pizza_id INT NOT NULL,
                                       size_id INT NOT NULL,
                                       PRIMARY KEY (pizza_id, size_id),
                                       KEY idx_pizza_available_sizes_size_id (size_id),
                                       CONSTRAINT fk_pizza_sizes_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE CASCADE,
                                       CONSTRAINT fk_pizza_sizes_size FOREIGN KEY (size_id) REFERENCES pizza_sizes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- CARTS + CART ITEMS
-- ----------------------------
CREATE TABLE carts (
                       id INT NOT NULL AUTO_INCREMENT,
                       user_id BIGINT NULL,
                       created_at DATETIME NOT NULL,
                       updated_at DATETIME NOT NULL,

                       PRIMARY KEY (id),
                       KEY idx_carts_user_id (user_id),
                       CONSTRAINT fk_carts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE cart_items (
                            id INT NOT NULL AUTO_INCREMENT,
                            cart_id INT NOT NULL,

                            pizza_id INT NULL,
                            drink_id BIGINT NULL,
                            size_id INT NULL,

                            item_name VARCHAR(255) NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,
                            total_price DECIMAL(10,2) NOT NULL,
                            quantity INT NOT NULL DEFAULT 1,

                            extra_ingredients VARCHAR(500) NULL,

                            PRIMARY KEY (id),
                            KEY idx_cart_items_cart_id (cart_id),
                            KEY idx_cart_items_pizza_id (pizza_id),
                            KEY idx_cart_items_drink_id (drink_id),

                            CONSTRAINT fk_cart_items_cart FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
                            CONSTRAINT fk_cart_items_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE SET NULL,
                            CONSTRAINT fk_cart_items_drink FOREIGN KEY (drink_id) REFERENCES drinks(id) ON DELETE SET NULL,
                            CONSTRAINT fk_cart_items_size FOREIGN KEY (size_id) REFERENCES pizza_sizes(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- ORDERS + ORDER ITEMS
-- ----------------------------
CREATE TABLE orders (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        order_number VARCHAR(60) NOT NULL,
                        user_id BIGINT NULL,

                        customer_first_name VARCHAR(80) NOT NULL,
                        customer_last_name VARCHAR(80) NOT NULL,
                        customer_phone VARCHAR(40) NULL,

                        delivery_street VARCHAR(160) NULL,
                        delivery_city VARCHAR(120) NULL,
                        delivery_postal_code VARCHAR(20) NULL,
                        delivery_instructions TEXT NULL,

                        delivery_method VARCHAR(20) NOT NULL DEFAULT 'DELIVERY',
                        order_source VARCHAR(20) NOT NULL DEFAULT 'ONLINE',
                        payment_method VARCHAR(20) NOT NULL DEFAULT 'CASH',
                        status VARCHAR(30) NOT NULL DEFAULT 'PENDING',

                        assigned_chef_id BIGINT NULL,
                        assigned_courier_id BIGINT NULL,

                        subtotal_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        delivery_fee_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        discount_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                        dph_percent DECIMAL(5,2) NOT NULL DEFAULT 20.00,
                        total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,

                        loyalty_points_earned INT NULL DEFAULT 0,

                        estimated_preparation_time INT NOT NULL DEFAULT 15,
                        estimated_delivery_time INT NOT NULL DEFAULT 30,

                        created_at DATETIME NOT NULL,
                        updated_at DATETIME NOT NULL,

                        preparation_start_at DATETIME NULL,
                        ready_at DATETIME NULL,
                        out_for_delivery_at DATETIME NULL,
                        delivered_at DATETIME NULL,

                        PRIMARY KEY (id),
                        UNIQUE KEY uk_orders_order_number (order_number),

                        KEY idx_orders_user_id (user_id),
                        KEY idx_orders_status (status),

                        CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
                        CONSTRAINT fk_orders_assigned_chef FOREIGN KEY (assigned_chef_id) REFERENCES users(id) ON DELETE SET NULL,
                        CONSTRAINT fk_orders_assigned_courier FOREIGN KEY (assigned_courier_id) REFERENCES users(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE order_items (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             order_id BIGINT NOT NULL,

                             item_type VARCHAR(10) NOT NULL,

                             pizza_id INT NULL,
                             drink_id BIGINT NULL,
                             size_id INT NULL,

                             item_name VARCHAR(200) NOT NULL,
                             quantity INT NOT NULL DEFAULT 1,

                             unit_price DECIMAL(10,2) NOT NULL,
                             total_price DECIMAL(10,2) NOT NULL,

                             PRIMARY KEY (id),

                             KEY idx_order_items_order_id (order_id),
                             KEY idx_order_items_pizza_id (pizza_id),
                             KEY idx_order_items_drink_id (drink_id),

                             CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
                             CONSTRAINT fk_order_items_pizza FOREIGN KEY (pizza_id) REFERENCES pizzas(id) ON DELETE SET NULL,
                             CONSTRAINT fk_order_items_drink FOREIGN KEY (drink_id) REFERENCES drinks(id) ON DELETE SET NULL,
                             CONSTRAINT fk_order_items_size FOREIGN KEY (size_id) REFERENCES pizza_sizes(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
