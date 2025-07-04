-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 04, 2025 at 08:35 AM
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
-- Database: `doctor_services`
--

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) DEFAULT NULL,
  `name` varchar(191) NOT NULL,
  `specialty_id` binary(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `user_id`, `name`, `specialty_id`) VALUES
(0x880e8400e29b41d4a716446655440001, 0x550e8400e29b41d4a716446655440003, 'BS. Le Van Ai', 0x770e8400e29b41d4a716446655440001),
(0x880e8400e29b41d4a716446655440002, 0x550e8400e29b41d4a716446655440004, 'BS. Pham Thi My Ngoc', 0x770e8400e29b41d4a716446655440002),
(0x880e8400e29b41d4a716446655440003, 0x550e8400e29b41d4a716446655440009, 'BS. Nguyen Thi Tuyet Minh', 0x770e8400e29b41d4a716446655440003);

-- --------------------------------------------------------

--
-- Table structure for table `pharmacists`
--

CREATE TABLE `pharmacists` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `name` varchar(191) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pharmacists`
--

INSERT INTO `pharmacists` (`id`, `user_id`, `name`) VALUES
(0x7a6b5c4d3e2f4a1b9c8d0e1f2a3b4c5d, 0xc3d2e1f0a9b84c5d7e6f1a2b3c4d5e6f, 'Pharmacist User 1'),
(0x9d8c7b6a5f4e4d3c2b1a0f9e8d7c6b5a, 0xe8f7d6c5b4a34d2e1f0c9b8a7c6d5e4f, 'Pharmacist User 2');

-- --------------------------------------------------------

--
-- Table structure for table `specialties`
--

CREATE TABLE `specialties` (
  `id` binary(16) NOT NULL,
  `name` varchar(191) NOT NULL,
  `description` varchar(191) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `specialties`
--

INSERT INTO `specialties` (`id`, `name`, `description`) VALUES
(0x770e8400e29b41d4a716446655440001, 'Cardiology', 'Chuyên khoa tim mạch, điều trị các bệnh liên quan đến tim và mạch máu'),
(0x770e8400e29b41d4a716446655440002, 'Neurology', 'Chuyên khoa thần kinh, điều trị các bệnh liên quan đến hệ thần kinh'),
(0x770e8400e29b41d4a716446655440003, 'Orthopedics', 'Chuyên khoa xương khớp, điều trị các bệnh liên quan đến xương và khớp');

-- --------------------------------------------------------

--
-- Table structure for table `staffs`
--

CREATE TABLE `staffs` (
  `id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `name` varchar(191) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staffs`
--

INSERT INTO `staffs` (`id`, `user_id`, `name`) VALUES
(0x2a3b4c5d6e7f4a8b9c0d1e2f3a4b5c6d, 0x7f8a9b0c1d2e4f3a8b9c0d1e2f3a4b5c, 'Staff User 2'),
(0x5b7c8d9e1f2a4b3c8d4e6f5a7b8c9d0e, 0x4a2c1b7e9d0f4e5c8b3a6f1d2e3c4b5a, 'Staff User 1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `specialty_id` (`specialty_id`);

--
-- Indexes for table `pharmacists`
--
ALTER TABLE `pharmacists`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `specialties`
--
ALTER TABLE `specialties`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `staffs`
--
ALTER TABLE `staffs`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
