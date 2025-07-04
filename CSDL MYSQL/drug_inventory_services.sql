-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2025 at 08:36 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `drug_inventory_services`
--

-- --------------------------------------------------------

--
-- Table structure for table `drug_inventory`
--

CREATE TABLE `drug_inventory` (
  `medicine_id` binary(16) NOT NULL,
  `stock_quantity` int(11) NOT NULL,
  `available_quantity` int(11) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `drug_inventory`
--

INSERT INTO `drug_inventory` (`medicine_id`, `stock_quantity`, `available_quantity`, `updated_at`) VALUES
(0xa1b2c3d4e5f678901234567890abcdef, 1000, 722, '2025-07-03 21:11:46'),
(0xb2c3d4e5f6a78901234567890abcdef1, 500, 389, '2025-06-24 07:06:48'),
(0xc3d4e5f6a7b8901234567890abcdef12, 2000, 1885, '2025-06-24 07:13:27'),
(0xd4e5f6a7b8c901234567890abcdef123, 750, 700, '2025-06-15 15:12:03'),
(0xe5f6a7b8c9d01234567890abcdef1234, 300, 278, '2025-06-24 07:13:07');

-- --------------------------------------------------------

--
-- Table structure for table `medicines`
--

CREATE TABLE `medicines` (
  `id` binary(16) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicines`
--

INSERT INTO `medicines` (`id`, `code`, `name`, `price`, `description`, `created_at`, `updated_at`) VALUES
(0xa1b2c3d4e5f678901234567890abcdef, 'PARA001', 'Paracetamol 500mg', 15000, 'Thuốc hạ sốt, giảm đau thông thường.', '2025-06-15 15:12:03', '2025-06-15 15:12:03'),
(0xb2c3d4e5f6a78901234567890abcdef1, 'AMOX002', 'Amoxicillin 250mg', 25000.5, 'Kháng sinh phổ rộng.', '2025-06-15 15:12:03', '2025-06-15 15:12:03'),
(0xc3d4e5f6a7b8901234567890abcdef12, 'VITC003', 'Vitamin C 1000mg', 10000.8, 'Bổ sung Vitamin C, tăng cường sức đề kháng.', '2025-06-15 15:12:03', '2025-06-15 15:12:03'),
(0xd4e5f6a7b8c901234567890abcdef123, 'IBUP004', 'Ibuprofen 400mg', 18000, 'Thuốc giảm đau, kháng viêm không steroid.', '2025-06-15 15:12:03', '2025-06-15 15:12:03'),
(0xe5f6a7b8c9d01234567890abcdef1234, 'OMEP005', 'Omeprazole 20mg', 30000.2, 'Thuốc điều trị loét dạ dày tá tràng.', '2025-06-15 15:12:03', '2025-06-15 15:12:03');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drug_inventory`
--
ALTER TABLE `drug_inventory`
  ADD PRIMARY KEY (`medicine_id`);

--
-- Indexes for table `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `drug_inventory`
--
ALTER TABLE `drug_inventory`
  ADD CONSTRAINT `drug_inventory_ibfk_1` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
