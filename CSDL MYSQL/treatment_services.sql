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
-- Database: `treatment_services`
--

-- --------------------------------------------------------

--
-- Table structure for table `treatments`
--

CREATE TABLE `treatments` (
  `id` binary(16) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `treatments`
--

INSERT INTO `treatments` (`id`, `code`, `name`, `price`, `description`, `created_at`, `updated_at`) VALUES
(0xe7cb118f49fa11f0bf4efdf6d95e4b5d, 'PT001', 'Vật lý trị liệu toàn thân', 1500000, 'Gói vật lý trị liệu phục hồi chức năng toàn diện cho cơ thể.', '2025-06-15 15:10:39', '2025-06-15 15:10:39'),
(0xe7d0208b49fa11f0bf4efdf6d95e4b5d, 'TP002', 'Trị liệu tâm lý chuyên sâu', 2500000, 'Phiên trị liệu tâm lý 1:1, giúp giải quyết các vấn đề tâm lý phức tạp.', '2025-06-15 15:10:39', '2025-06-15 15:10:39'),
(0xe7d5bafb49fa11f0bf4efdf6d95e4b5d, 'SURG003', 'Phẫu thuật nội soi', 15000000, 'Thực hiện phẫu thuật bằng phương pháp nội soi, giảm thiểu xâm lấn.', '2025-06-15 15:10:39', '2025-06-15 15:10:39'),
(0xe7db1ee449fa11f0bf4efdf6d95e4b5d, 'ACU004', 'Châm cứu truyền thống', 300000, 'Liệu pháp châm cứu cổ truyền giúp giảm đau và cân bằng năng lượng cơ thể.', '2025-06-15 15:10:39', '2025-06-15 15:10:39'),
(0xe7e1659a49fa11f0bf4efdf6d95e4b5d, 'NUTRI005', 'Tư vấn dinh dưỡng cá nhân', 800000, 'Chương trình tư vấn dinh dưỡng được thiết kế riêng theo nhu cầu và tình trạng sức khỏe.', '2025-06-15 15:10:39', '2025-06-15 15:10:39');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `treatments`
--
ALTER TABLE `treatments`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
