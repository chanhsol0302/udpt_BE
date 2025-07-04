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
-- Database: `user_services`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `email` varchar(191) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('PATIENT','STAFF','DOCTOR','PHARMACIST','    ANALYSIS_STAFF') NOT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `password_hash`, `role`, `created_at`, `updated_at`) VALUES
(0x2b7a14d23ffc43deb43c268e8f8a780c, 'sammy112102003@gmail.com', '$2a$10$YFWwBItHLN.Nka4a.UwWS.TkJ0bxI9o4MH1oftitJFRcH8WFDXB/O', 'PATIENT', '2025-07-03 21:08:38', '2025-07-03 21:08:38'),
(0x4a2c1b7e9d0f4e5c8b3a6f1d2e3c4b5a, 'staff001@example.com', '$2b$12$5M4H/Qy0VvHu.zMcTHH2He/hAZ11IcH8eW/lBDKlL1Ej4LCm4R/su', 'STAFF', '2025-06-17 19:20:37', NULL),
(0x550e8400e29b41d4a716446655440003, 'doctor000@gmail.com', '$2b$12$lXsCjf0hK1y5s3nBbRtuHOl4m5RUUc6gA9HGzAJniZcIGP9twHqxK', 'DOCTOR', '2025-06-12 14:15:07', NULL),
(0x7f8a9b0c1d2e4f3a8b9c0d1e2f3a4b5c, 'staff002@example.com', '$2b$12$SnyGNLkk1FLMpzghWB4n0.NC.Y1pVr.sPjHZe3tZYJmguaJGU/rYG', 'STAFF', '2025-06-17 19:20:37', NULL),
(0x8152c57b49be47cb8a4212ca5f0533a1, 'hau@gmail.com', '$2a$10$7406iufAeyTeUW9HoJyFKeWC6JXkcWRkU89VX1npLR8LX8O5GHGE2', 'STAFF', '2025-06-04 05:06:24', '2025-06-04 05:06:24'),
(0xa6078c266eec44c2a59a54dbbb220aba, 'chanhsol0302@gmail.com', '$2a$10$RAJvGcdmkrFSqmcSIt8.eetNA.fWFMEXoA15f8f3kSfbflyqDRleu', 'DOCTOR', '2025-06-04 03:02:09', '2025-06-04 03:02:09'),
(0xc3d2e1f0a9b84c5d7e6f1a2b3c4d5e6f, 'pharmacist001@example.com', '$2b$12$8i0rKglN8q6saBTWbkARme99JXSfWGkt3qu6l.KsdkHaHtBt/SOHO', 'PHARMACIST', '2025-06-17 19:22:24', NULL),
(0xe8f7d6c5b4a34d2e1f0c9b8a7c6d5e4f, 'pharmacist002@example.com', '$2b$12$u9T1rbImy1PdaoM9Kznb1u3bnnAFIvKcxivFpoFxcvDAK0lGlAtKi', 'PHARMACIST', '2025-06-17 19:22:24', NULL),
(0xf8ae5ce61e8f48608a0737132a3238ed, 'analyst_staff@gmail.com', '$2b$12$.wie/Iyanu8N/QlLGb3t5O7.OWPyftOVcC8S0nQ/ioaM7RWebEjJu', '    ANALYSIS_STAFF', '2025-06-08 03:45:14', '2025-06-08 03:45:14');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
