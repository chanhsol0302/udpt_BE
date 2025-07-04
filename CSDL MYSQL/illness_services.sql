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
-- Database: `illness_services`
--

-- --------------------------------------------------------

--
-- Table structure for table `illnesses`
--

CREATE TABLE `illnesses` (
  `id` binary(16) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `illnesses`
--

INSERT INTO `illnesses` (`id`, `code`, `name`, `description`, `created_at`, `updated_at`) VALUES
(0xce632f7849fa11f0bf4efdf6d95e4b5d, 'FLU001', 'Cảm cúm thông thường', 'Một bệnh nhiễm trùng đường hô hấp cấp tính do virus cúm gây ra, với các triệu chứng như sốt, ho, đau họng, đau nhức cơ thể.', '2025-06-15 15:09:56', '2025-06-15 15:09:56'),
(0xce67e7bc49fa11f0bf4efdf6d95e4b5d, 'GASTRO002', 'Đau dạ dày cấp', 'Tình trạng viêm niêm mạc dạ dày đột ngột, thường gây ra các triệu chứng như đau bụng trên, buồn nôn, nôn mửa và khó tiêu.', '2025-06-15 15:09:56', '2025-06-15 15:09:56'),
(0xce6d1b1349fa11f0bf4efdf6d95e4b5d, 'PHARYNX003', 'Viêm họng hạt', 'Tình trạng viêm nhiễm mãn tính ở họng, kèm theo sự xuất hiện của các hạt lympho lớn ở thành sau họng. Thường gây đau họng, ngứa họng và ho khan.', '2025-06-15 15:09:56', '2025-06-15 15:09:56'),
(0xce72520449fa11f0bf4efdf6d95e4b5d, 'ALLERGY004', 'Dị ứng phấn hoa', 'Phản ứng quá mẫn của hệ miễn dịch với phấn hoa, gây ra các triệu chứng như hắt hơi, sổ mũi, ngứa mắt và nghẹt mũi.', '2025-06-15 15:09:56', '2025-06-15 15:09:56');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `illnesses`
--
ALTER TABLE `illnesses`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
