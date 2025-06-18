-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Jun 2025 pada 07.04
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
-- Database: `service_gadget`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_aksesoris`
--

CREATE TABLE `tb_aksesoris` (
  `id_aksesoris` varchar(10) NOT NULL,
  `nama_aksesoris` varchar(30) NOT NULL,
  `harga_beliAk` int(25) NOT NULL,
  `harga_jualAk` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_aksesoris`
--

INSERT INTO `tb_aksesoris` (`id_aksesoris`, `nama_aksesoris`, `harga_beliAk`, `harga_jualAk`) VALUES
('A001', 'Casing Realme 16', 10000, 15000),
('A002', 'Earphone Sony', 100000, 150000),
('A003', 'Power Bank ROBOT 5000 mAh', 75000, 120000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_login`
--

CREATE TABLE `tb_login` (
  `id_teknisi` char(15) NOT NULL,
  `sandi` char(15) NOT NULL,
  `nama` char(25) NOT NULL,
  `jenkel` char(15) NOT NULL,
  `hp` char(15) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_login`
--

INSERT INTO `tb_login` (`id_teknisi`, `sandi`, `nama`, `jenkel`, `hp`, `alamat`) VALUES
('T01', '123', 'Ahmad Nur Latif Prayoga', 'Laki-Laki', '081949452687', 'Jl Jatipadang Raya, Pasar Minggu, Jakarta Selatan'),
('T02', '123', 'Martin Aron Samuel', 'Laki-Laki', '081212341234', 'Jl.Suka-suka'),
('T03', '123', 'Elon Musk', 'Laki-Laki', '081911111111', 'Jl.Starling,Depok,Indonesia'),
('T04', '123', 'Amelia', 'Perempuan', '123412341234', 'jakarta'),
('T05', '123', 'Naafira', 'Perempuan', '123412341234', 'Depok');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_nota`
--

CREATE TABLE `tb_nota` (
  `id_nota` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `id_teknisi` varchar(15) NOT NULL,
  `id_pelanggan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_nota`
--

INSERT INTO `tb_nota` (`id_nota`, `tanggal`, `id_teknisi`, `id_pelanggan`) VALUES
('IN0001', '2025-06-04', 'T01', 'P1'),
('IN0002', '2025-06-05', 'T01', 'P1'),
('IN0003', '2025-06-05', 'T01', 'P3'),
('IN0004', '2025-06-08', 'T01', 'P1'),
('IN0005', '2025-06-08', 'T02', 'P4'),
('IN0006', '2025-06-08', 'T01', 'P4');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_nota_detail`
--

CREATE TABLE `tb_nota_detail` (
  `id_nota` varchar(10) NOT NULL,
  `id_item` varchar(10) NOT NULL,
  `nama_item` varchar(50) NOT NULL,
  `harga_beli` int(100) NOT NULL,
  `harga_jual` int(100) NOT NULL,
  `kuantitas` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_nota_detail`
--

INSERT INTO `tb_nota_detail` (`id_nota`, `id_item`, `nama_item`, `harga_beli`, `harga_jual`, `kuantitas`) VALUES
('IN0001', 'S01', 'Ganti Baterai Laptop', 0, 100000, 1),
('IN0001', 'SP01', 'Baterai Laptop Vivobook 14 OLED', 500000, 1000000, 1),
('IN0001', 'A001', 'Casing Realme 16', 10000, 15000, 4),
('IN0002', 'S01', 'Ganti Baterai Laptop', 0, 100000, 1),
('IN0002', 'SP01', 'Baterai Laptop Vivobook 14 OLED', 500000, 1000000, 1),
('IN0002', 'A001', 'Casing Realme 16', 10000, 15000, 2),
('IN0003', 'S01', 'Ganti Baterai Laptop', 0, 100000, 1),
('IN0003', 'SP01', 'Baterai Laptop Vivobook 14 OLED', 500000, 1000000, 1),
('IN0003', 'A001', 'Casing Realme 16', 10000, 15000, 1),
('IN0004', 'A003', 'Power Bank ROBOT 5000 mAh', 75000, 120000, 3),
('IN0004', 'S01', 'Ganti Baterai Laptop', 0, 100000, 2),
('IN0004', 'SP01', 'Baterai Laptop Vivobook 14 OLED', 500000, 1000000, 2),
('IN0005', 'S03', 'Ganti LCD Handphone', 0, 100000, 2),
('IN0005', 'SP03', 'LCD Handphone Realme 6', 200000, 350000, 2),
('IN0005', 'A003', 'Power Bank ROBOT 5000 mAh', 75000, 120000, 1),
('IN0006', 'A003', 'Power Bank ROBOT 5000 mAh', 75000, 120000, 1),
('IN0006', 'S02', 'Ganti LCD Laptop', 0, 100000, 1),
('IN0006', 'SP02', 'LCD Laptop Vivobook 14 OLED', 500000, 1000000, 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_pelanggan`
--

CREATE TABLE `tb_pelanggan` (
  `id_pelanggan` char(15) NOT NULL,
  `nama_pelanggan` char(25) NOT NULL,
  `hp_pelanggan` char(15) NOT NULL,
  `jk_pelanggan` char(25) NOT NULL,
  `almt_pelanggan` text NOT NULL,
  `tanggal_pelanggan` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_pelanggan`
--

INSERT INTO `tb_pelanggan` (`id_pelanggan`, `nama_pelanggan`, `hp_pelanggan`, `jk_pelanggan`, `almt_pelanggan`, `tanggal_pelanggan`) VALUES
('P1', 'Dimas', '081212341234', 'Laki-Laki', 'Jakarta Selatan', '2025-04-19'),
('P2', 'Krisna Murti', '081912341234', 'Laki-Laki', 'Depok, Jawa Barat', '2025-04-21'),
('P3', 'Andini', '081212341234', 'Perempuan', 'Jakarta Timur', '2025-04-21'),
('P4', 'Herlambang', '081212341234', 'Laki-Laki', 'Cilandak, Jakarta Selatan', '2025-04-30');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_service`
--

CREATE TABLE `tb_service` (
  `id_service` varchar(10) NOT NULL,
  `jenis_service` varchar(200) NOT NULL,
  `biaya_service` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_service`
--

INSERT INTO `tb_service` (`id_service`, `jenis_service`, `biaya_service`) VALUES
('S01', 'Ganti Baterai Laptop', 100000),
('S02', 'Ganti LCD Laptop', 100000),
('S03', 'Ganti LCD Handphone', 100000),
('S04', 'Ganti Baterai Handphone', 50000),
('S05', 'Service Motherboard Laptop', 150000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_sparepart`
--

CREATE TABLE `tb_sparepart` (
  `kd_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(200) NOT NULL,
  `harga_beli` int(100) NOT NULL,
  `harga_jual` int(100) NOT NULL,
  `jenis_barang` varchar(20) NOT NULL,
  `merk_barang` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tb_sparepart`
--

INSERT INTO `tb_sparepart` (`kd_barang`, `nama_barang`, `harga_beli`, `harga_jual`, `jenis_barang`, `merk_barang`) VALUES
('SP01', 'Baterai Laptop Vivobook 14 OLED', 500000, 1000000, 'Baterai', 'Asus'),
('SP02', 'LCD Laptop Vivobook 14 OLED', 500000, 1000000, 'LCD', 'Asus'),
('SP03', 'LCD Handphone Realme 6', 200000, 350000, 'LCD', 'Realme'),
('SP04', 'Baterai Handphone Realme 6', 150000, 250000, 'Baterai', 'Realme');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_aksesoris`
--
ALTER TABLE `tb_aksesoris`
  ADD PRIMARY KEY (`id_aksesoris`);

--
-- Indeks untuk tabel `tb_login`
--
ALTER TABLE `tb_login`
  ADD PRIMARY KEY (`id_teknisi`);

--
-- Indeks untuk tabel `tb_nota`
--
ALTER TABLE `tb_nota`
  ADD PRIMARY KEY (`id_nota`);

--
-- Indeks untuk tabel `tb_pelanggan`
--
ALTER TABLE `tb_pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indeks untuk tabel `tb_service`
--
ALTER TABLE `tb_service`
  ADD PRIMARY KEY (`id_service`);

--
-- Indeks untuk tabel `tb_sparepart`
--
ALTER TABLE `tb_sparepart`
  ADD PRIMARY KEY (`kd_barang`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
