-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 15 Okt 2025 pada 17.06
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `data_produk`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `product`
--

CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `tanggalMasuk` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `product`
--

INSERT INTO `product` (`id`, `nama`, `harga`, `kategori`, `tanggalMasuk`) VALUES
('P001', 'Laptop Asus', 8500000, 'Elektronik', '2025-10-22'),
('P002', 'Mouse Logitech', 350000, 'Elektronik', '2025-10-23'),
('P003', 'Keyboard Mechanical', 750000, 'Elektronik', '2025-10-24'),
('P004', 'Roti Tawar', 15000, 'Makanan', '2025-10-25'),
('P005', 'Susu UHT', 12000, 'Minuman', '2025-10-26'),
('P006', 'Kemeja Putih', 125000, 'Pakaian', '2025-10-27'),
('P007', 'Celana Jeans', 200000, 'Pakaian', '2025-10-28'),
('P008', 'Pensil 2B', 3000, 'Alat Tulis', '2025-10-29'),
('P009', 'Buku Tulis', 8000, 'Alat Tulis', '2025-10-30'),
('P010', 'Air Mineral', 5000, 'Minuman', '2025-11-01'),
('P011', 'Smartphone Samsung', 4500000, 'Elektronik', '2025-11-02'),
('P012', 'Kue Brownies', 25000, 'Makanan', '2025-11-03'),
('P013', 'Jaket Hoodie', 180000, 'Pakaian', '2025-11-04'),
('P014', 'Pulpen Gel', 5000, 'Alat Tulis', '2025-11-05'),
('P015', 'Teh Botol', 8000, 'Minuman', '2025-11-06');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
