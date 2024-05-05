-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 05, 2024 at 05:01 PM
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
(1, 'Budget Meal', 0),
(2, 'Drinks', 0),
(3, 'Others', 0),
(4, 'Main', 0),
(5, 'AA', 1),
(6, 'sd', 1),
(7, 'aaa', 1),
(9, 'dsadas', 1),
(10, 'aaaa', 1),
(11, 'aaa', 1),
(12, 'aaa', 1),
(13, 'Poppers', 1),
(14, 'Popper', 1),
(15, 'Poppers', 1),
(16, 'Poppers', 1),
(17, 'ds', 1),
(18, 'A', 1),
(19, 'aa', 1),
(20, 'aa', 1),
(21, 'AA', 1);

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
(1, 1, 'asas', 23, '2024-04-26 15:28:40', '2024-04-30 21:44:49', 1),
(2, 1, 'f', 23.556, '2024-04-26 15:29:26', '2024-04-26 23:29:26', 0),
(3, 2, 'Soda', 45, '2024-04-26 17:06:22', '2024-04-27 01:06:22', 0),
(4, 1, 'Soda', 45, '2024-04-26 17:08:50', '2024-04-27 01:08:50', 0),
(5, 2, 's', 33, '2024-04-26 18:11:15', '2024-04-27 02:11:15', 0),
(6, 1, '22', 32, '2024-04-26 18:47:16', '2024-04-30 20:58:52', 1),
(7, 4, 'qq', 12, '2024-04-26 18:47:54', '2024-04-27 02:47:54', 0),
(8, 1, 'Pork', 1200, '2024-04-26 18:55:44', '2024-04-27 02:55:44', 0),
(9, 2, 'AAA', 1550, '2024-04-26 19:28:51', '2024-04-30 20:59:00', 1),
(10, 2, 'dsa', 1212, '2024-04-26 19:32:05', '2024-04-30 21:46:49', 1),
(11, 3, 'Rice', 50, '2024-04-26 19:32:16', '2024-04-27 03:32:16', 0),
(12, 4, 'Best Seller', 5010, '2024-04-26 19:33:25', '2024-04-27 03:33:25', 0),
(13, 2, 'Pope', 20, '2024-04-26 19:33:41', '2024-04-27 03:33:41', 0),
(14, 2, 'asas', 32.453, '2024-04-27 09:37:07', '2024-04-27 17:37:07', 0),
(15, 2, 'sds', 23, '2024-04-27 09:47:53', '2024-04-27 17:47:53', 0),
(16, 1, 'AAA', 23, '2024-04-27 09:48:06', '2024-04-27 17:48:06', 0),
(17, 2, 'WW', 1222, '2024-04-28 18:33:45', '2024-04-29 02:33:45', 0),
(18, 1, '111', 120002, '2024-04-28 18:34:03', '2024-04-30 20:58:44', 1),
(19, 1, '222', 1221220, '2024-04-28 18:34:24', '2024-04-30 20:58:56', 1);

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
(1, 'Inan', 'Celis', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', '2024-04-24 17:21:11', '2024-04-24 21:32:09', 1, 0),
(2, 'Oyen', 'Celis', 'oyencelis@gmail.com', 'd6af934fc1f0c5d701023205b7dc326cea2dd609', '2024-04-24 17:14:32', '2024-04-25 01:14:32', 0, 0),
(3, 'angelica', 'cuevas', 'angie', 'affd33dec31b0844c378d4ba0506c22f4c3f1ce9', '2024-04-25 02:01:14', '2024-04-25 10:01:14', 0, 0),
(4, 'leri', 'leri', 'leri', '23ed5a31705e5fd974ebfee771a4f2bd72dc8c75', '2024-04-25 02:01:53', '2024-04-25 10:01:53', 0, 0),
(5, 'sedo', 'sedo', 'sedo', 'a547f0f0612504ebf82999cab640224403a6932d', '2024-04-25 07:18:56', '2024-04-25 15:18:56', 0, 0),
(6, 'law', 'law', 'law', 'c660f2f32dafabe4fa05b2bc16c8b6318e19b86a', '2024-04-25 09:49:58', '2024-04-25 17:49:58', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_id`);

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
  MODIFY `category_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `product_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
