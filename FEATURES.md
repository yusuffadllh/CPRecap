# FEATURES.md

# SmartFinance Feature Specification

Dokumen ini menjelaskan seluruh fitur utama SmartFinance beserta flow, requirement, dan aturan implementasi.

---

# Core Features

## Authentication

### Login

Provider:

- Google Sign-In
- Email & Password.
- register

Backend:

- Firebase Authentication

Flow:

Open App
↓
Google Sign In
↓
Firebase Auth
↓
Dashboard

Rules

- Session otomatis dipulihkan jika masih valid.
- Logout menghapus session Firebase.

---

# Dashboard

Purpose

Memberikan ringkasan kondisi keuangan pengguna.

Menampilkan:

- Total Balance
- Income Bulan Ini
- Expense Bulan Ini
- Budget Progress
- Analytics Ringkas
- Recent Transaction

Quick Action:

- Add Income
- Add Expense
- Budget
- Analytics

---

# Transaction

Jenis

- Income
- Expense

Fitur

- Tambah
- Edit
- Hapus
- Cari
- Filter
- Sort

Field

- Amount
- Category
- Date
- Name
- Note

Rules

- Amount wajib > 0
- Category wajib dipilih
- Date wajib

---

# Category

Default Category

Income

- Salary
- Bonus
- Investment
- Gift
- Other

Expense

- Food
- Transport
- Shopping
- Entertainment
- Health
- Education
- Bills
- Travel
- Subscription
- Others

User dapat:

- Menambah kategori
- Edit kategori
- Hapus kategori custom

Kategori bawaan tidak boleh dihapus.

---

# Budget

Budget dibuat per kategori.

Contoh

Food

Budget

Rp1.000.000

Spent

Rp650.000

Remaining

Rp350.000

Progress dihitung otomatis.

Jika melebihi budget tampilkan warning.

---

# Analytics

Menampilkan

Income vs Expense

Category Spending

Monthly Trend

Weekly Trend

Budget Usage

Summary

Filter

- Minggu
- Bulan
- Tahun

---

# Notification Reader

## Purpose

Membuat draft transaksi otomatis dari notifikasi.

Permission

Notification Access

Flow

Notification

↓

Parser

↓

Extract Data

↓

AI Category

↓

Draft Transaction

↓

User Confirmation

↓

Save

Supported Apps

- BRImo
- Livin'
- BCA Mobile
- myBCA
- BNI Mobile
- SeaBank
- Jenius
- DANA
- OVO
- GoPay
- ShopeePay
- LinkAja

Extracted Data

- Merchant
- Amount
- Date
- Reference
- Transaction Type

Rules

Tidak langsung menyimpan transaksi.

Selalu membuat draft terlebih dahulu.

---

# Gmail Integration

Purpose

Membaca email transaksi.

Permission

gmail.readonly

Flow

Email

↓

Parser

↓

Merchant

↓

Amount

↓

AI Category

↓

Draft

↓

Confirmation

↓

Save

Email yang diproses

- Receipt
- Invoice
- Payment Confirmation
- E-Commerce Order

Rules

Tidak membaca email pribadi yang tidak berkaitan dengan transaksi.

Tidak menghapus email.

Tidak mengirim email.

---

# AI Assistant

AI digunakan sebagai asisten.

Bukan pengambil keputusan.

Fitur AI

- Auto Category
- Merchant Detection
- Smart Summary
- Spending Insight
- Duplicate Detection
- Budget Recommendation

Rules

Confidence

>=90%

Auto pilih kategori.

Confidence

<90%

Tampilkan pilihan kepada user.

AI tidak boleh:

- Menghapus transaksi
- Mengubah nominal
- Mengubah tanggal

tanpa konfirmasi pengguna.

---

# Search

User dapat mencari berdasarkan

- Merchant
- Nama
- Kategori
- Catatan

Search dilakukan secara lokal.

---

# Filter

Filter berdasarkan

- Income
- Expense
- Category
- Date
- Month
- Year

---

# Sort

Ascending

Descending

Date

Amount

Name

---

# Backup

Cloud Backup

Backup

Restore

Rules

Backup bersifat manual.

Restore membutuhkan konfirmasi.

---

# Theme

Support

- System
- Light
- Dark

Accent Color (Future)

---

# Security

Support

- Biometric
- App Lock (Future)
- Device Session

---

# Notification

Reminder

- Budget hampir habis
- Monthly Summary
- Daily Reminder

---

# Offline First

Semua transaksi disimpan ke Room Database.

Internet hanya digunakan untuk

- Login
- AI
- Gmail
- Backup

---

# Future Features

- Export PDF
- Subscription Tracker

---

# Feature Priority

P1

- Login
- Dashboard
- Transaction

P2

- Budget
- Analytics
- Category

P3

- Notification Reader
- Gmail
- AI

P4

- Backup
- Theme
- Security

P5

- Export

---

# Definition of Done

Sebuah fitur dianggap selesai apabila:

- UI sesuai DESIGN.md
- Menggunakan Architecture yang benar
- Mendukung Loading, Empty, Error, Success
- Memiliki validasi
- Tidak crash
- Mengikuti AGENTS.md
