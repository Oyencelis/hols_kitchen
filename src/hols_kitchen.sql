-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2024 at 06:47 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hols_kitchen`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_id` int(10) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `status` int(2) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category_name`, `status`) VALUES
(1, 'Afforda Meals', 0),
(2, 'Chicken Poppers', 0),
(3, 'Mains', 0),
(4, 'Others', 0),
(5, 'Test', 1);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(10) NOT NULL,
  `reference` int(10) NOT NULL,
  `user_id` int(20) NOT NULL,
  `customer_name` varchar(255) NOT NULL,
  `cash` int(10) NOT NULL,
  `cash_type` varchar(255) NOT NULL,
  `order_type` varchar(255) NOT NULL,
  `Total` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` int(2) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `reference`, `user_id`, `customer_name`, `cash`, `cash_type`, `order_type`, `Total`, `created_at`, `updated_at`, `status`) VALUES
(1, 1000000001, 1, 'Lawrence', 210, 'Cash', 'Dine In', '205.0', '2024-02-29 20:15:58', '2024-05-24 10:12:39', 0),
(2, 1000000002, 1, 'Ange', 300, 'Cash', 'Dine In', '285.0', '2024-04-22 02:08:53', '2024-05-24 10:12:53', 0),
(3, 1000000003, 1, 'Leri', 200, 'Cash', 'Take Out', '192.0', '2024-05-22 21:22:30', '2024-05-24 10:09:42', 0),
(4, 1000000004, 6, 'Randolf', 200, 'Cash', 'Dine In', '190.0', '2024-05-23 21:23:49', '2024-05-24 05:23:49', 0),
(5, 1000000005, 1, 'Random', 150, 'Cash', 'Dine In', '149.0', '2024-05-23 16:00:00', '2024-05-24 10:08:51', 0),
(6, 1000000006, 1, 'Janeth', 150, 'Cash', 'Dine In', '145.0', '2024-05-24 02:08:08', '2024-05-24 10:08:48', 0),
(7, 1000000007, 1, 'Jovel', 190, 'GCash', 'Take Out', '190.0', '2024-05-25 15:26:21', '2024-05-25 23:26:21', 0),
(8, 1000000008, 4, 'Joneeel', 225, 'Cash', 'Dine In', '225.0', '2024-05-26 01:45:41', '2024-05-26 09:45:41', 0),
(9, 1000000009, 1, 'Juss', 360, 'GCash', 'Take Out', '360.0', '2024-05-27 09:19:18', '2024-05-27 17:19:18', 0),
(10, 1000000010, 1, 'Dingdong', 110, 'Cash', 'Take Out', '102.0', '2024-05-27 09:20:28', '2024-05-27 17:20:28', 0),
(11, 1000000011, 1, 'Angas', 1500, 'GCash', 'Take Out', '1340.0', '2024-05-27 18:53:43', '2024-05-28 02:53:43', 0);

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `order_item_id` int(10) NOT NULL,
  `order_reference` int(10) NOT NULL,
  `product_id` int(10) NOT NULL,
  `qty` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`order_item_id`, `order_reference`, `product_id`, `qty`) VALUES
(1, 1000000001, 18, 1),
(2, 1000000001, 24, 1),
(3, 1000000002, 18, 2),
(4, 1000000002, 20, 1),
(5, 1000000003, 29, 1),
(6, 1000000003, 26, 2),
(7, 1000000003, 25, 2),
(8, 1000000004, 18, 1),
(9, 1000000004, 20, 1),
(10, 1000000005, 36, 2),
(11, 1000000005, 31, 2),
(12, 1000000005, 30, 1),
(13, 1000000005, 29, 1),
(14, 1000000006, 3, 1),
(15, 1000000006, 18, 1),
(16, 1000000007, 18, 2),
(17, 1000000008, 7, 1),
(18, 1000000008, 8, 2),
(19, 1000000009, 18, 1),
(20, 1000000009, 20, 1),
(21, 1000000009, 15, 2),
(22, 1000000010, 29, 1),
(23, 1000000010, 26, 1),
(24, 1000000011, 3, 1),
(25, 1000000011, 18, 10),
(26, 1000000011, 15, 4);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `product_id` int(10) NOT NULL,
  `category_id` int(10) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` int(2) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`product_id`, `category_id`, `product_name`, `price`, `created_at`, `updated_at`, `status`) VALUES
(1, 1, 'Steamed Pork Siomai', 45, '2024-05-22 14:18:53', '2024-05-22 22:18:53', 0),
(2, 1, 'TJ Jumbo Cheesedog', 45, '2024-05-22 14:19:29', '2024-05-22 22:19:29', 0),
(3, 1, 'Fried Pork Siomai', 50, '2024-05-22 14:19:51', '2024-05-22 22:19:51', 0),
(4, 1, 'Bacon w/ White Sauce', 75, '2024-05-22 14:20:37', '2024-05-22 22:20:37', 0),
(5, 1, 'Hungarian w/ Hols Sauce', 75, '2024-05-22 14:21:59', '2024-05-22 22:21:59', 0),
(6, 2, 'Ala King', 75, '2024-05-22 14:24:00', '2024-05-22 22:24:00', 0),
(7, 2, 'BBQ', 75, '2024-05-22 14:24:37', '2024-05-22 22:24:37', 0),
(8, 2, 'Cheese', 75, '2024-05-22 14:25:07', '2024-05-22 22:25:07', 0),
(9, 2, 'Chili Cheese', 75, '2024-05-22 14:25:36', '2024-05-22 22:25:36', 0),
(10, 2, 'Gravy', 75, '2024-05-22 14:25:48', '2024-05-22 22:25:48', 0),
(11, 2, 'Honey Glazed', 75, '2024-05-22 14:26:08', '2024-05-22 22:26:08', 0),
(12, 2, 'Soy Garlic', 75, '2024-05-22 14:27:53', '2024-05-22 22:27:53', 0),
(13, 2, 'Spicy Buffalo', 75, '2024-05-22 14:29:26', '2024-05-22 22:29:26', 0),
(14, 2, 'Teriyaki', 75, '2024-05-22 14:29:56', '2024-05-22 22:29:56', 0),
(15, 3, 'Cheesy Hungarian w/ Hols Sauce', 85, '2024-05-22 14:30:34', '2024-05-22 22:30:34', 0),
(16, 3, 'Chicken Sisig', 90, '2024-05-22 14:30:52', '2024-05-22 22:30:52', 0),
(17, 3, 'Bangus Sisig', 95, '2024-05-22 14:31:31', '2024-05-22 22:31:31', 0),
(18, 3, 'Beef Tapa', 95, '2024-05-22 14:33:29', '2024-05-22 22:33:29', 0),
(19, 3, 'Chicken Fingers w/ Gravy', 95, '2024-05-22 14:35:57', '2024-05-22 22:35:57', 0),
(20, 3, 'Chicken Fingers w/ White Sauce', 95, '2024-05-22 14:36:14', '2024-05-22 22:36:14', 0),
(21, 3, 'Chicken Katsu w/ Gravy', 105, '2024-05-22 14:37:34', '2024-05-22 22:37:34', 0),
(22, 3, 'Chicken Katsu w/ Hols Sauce', 105, '2024-05-22 14:38:16', '2024-05-22 22:38:16', 0),
(23, 3, 'Garlic Pepper Beef', 105, '2024-05-22 14:38:38', '2024-05-22 22:38:38', 0),
(24, 3, 'Creamy Beef', 110, '2024-05-22 14:39:14', '2024-05-22 22:39:14', 0),
(25, 4, 'Juice Solo', 10, '2024-05-22 14:39:35', '2024-05-22 22:39:35', 0),
(26, 4, 'Juice Pitcher', 70, '2024-05-22 14:39:51', '2024-05-22 22:39:51', 0),
(27, 4, 'Coke, Royal, Sprite', 25, '2024-05-22 14:40:45', '2024-05-22 23:45:34', 1),
(28, 4, 'Other Drinks', 20, '2024-05-22 14:41:07', '2024-05-22 22:41:07', 0),
(29, 4, '4pcs Steamed Siomai', 32, '2024-05-22 14:41:37', '2024-05-22 22:41:37', 0),
(30, 4, '4pcs Fried Siomai', 37, '2024-05-22 14:41:58', '2024-05-22 22:41:58', 0),
(31, 4, 'Extra Sauce', 15, '2024-05-22 14:42:22', '2024-05-22 22:42:22', 0),
(32, 4, 'Plain Rice', 15, '2024-05-22 14:42:40', '2024-05-22 22:42:40', 0),
(33, 4, 'Java Rice', 20, '2024-05-22 14:42:57', '2024-05-22 22:42:57', 0),
(34, 4, 'Fried Egg', 15, '2024-05-22 14:43:21', '2024-05-22 22:43:21', 0),
(35, 4, 'Salted Egg', 20, '2024-05-22 14:43:33', '2024-05-22 22:43:33', 0),
(36, 4, 'Coke', 25, '2024-05-22 15:45:50', '2024-05-22 23:45:50', 0),
(37, 4, 'Royal', 25, '2024-05-22 15:45:59', '2024-05-22 23:45:59', 0),
(38, 4, 'Sprite', 25, '2024-05-22 15:46:17', '2024-05-22 23:46:17', 0),
(39, 4, 'Test', 12, '2024-05-25 15:24:33', '2024-05-25 23:24:57', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `role` int(2) DEFAULT 0,
  `status` int(2) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `firstname`, `lastname`, `email`, `password`, `created_at`, `updated_at`, `role`, `status`) VALUES
(1, 'Inan', 'Celis', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', '2024-04-24 17:21:11', '2024-05-28 02:49:13', 1, 0),
(2, 'Oyen', 'Celis', 'oyencelis@gmail.com', 'd6af934fc1f0c5d701023205b7dc326cea2dd609', '2024-04-24 17:14:32', '2024-05-29 00:18:37', 0, 1),
(3, 'Angelica', 'Cuevas', 'Diu', 'affd33dec31b0844c378d4ba0506c22f4c3f1ce9', '2024-04-25 02:01:14', '2024-05-27 21:02:43', 0, 0),
(4, 'Leriraine', 'Borje', 'leri', '23ed5a31705e5fd974ebfee771a4f2bd72dc8c75', '2024-04-25 02:01:53', '2024-05-29 00:46:44', 0, 2),
(6, 'lawrence', 'pogi', 'law', 'c660f2f32dafabe4fa05b2bc16c8b6318e19b86a', '2024-04-25 09:49:58', '2024-05-29 00:18:56', 1, 0),
(7, 'Tests', 'Last', 'Test', '8cb2237d0679ca88db6464eac60da96345513964', '2024-05-26 08:14:19', '2024-05-28 23:38:50', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`order_item_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `category_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `order_item_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
